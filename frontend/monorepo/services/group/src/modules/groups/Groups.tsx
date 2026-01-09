import GroupSelector from "../../components/groupSelector/GroupSelector";
import { useAppDispatch, useAppSelector } from "@packages/shared";
import { addGroup, clearGroups, setCurrentGroupId, setGroups } from "@packages/shared";
import { createGroup } from "./api/createGroup";
import { getGroups } from "./api/getGroups";
import { useEffect } from "react";
import { useError } from "@packages/shared";

const Groups = () => {
    const dispatch = useAppDispatch();
    const { currentGroupId, groups } = useAppSelector((state) => state.group);
    const { showError } = useError();

    useEffect(
        () => {
            getGroups({
                onSuccess: (data: {groupId: number, groupName: string}[]) =>  {
                    dispatch(clearGroups())
                    dispatch(setGroups(data))

                    if (data.length !== 0){
                        dispatch(setCurrentGroupId({ id: data[0].groupId}));
                    }
                }
            })
        }, []
    )
    
    const handleCreateGroup = (groupName: string) => {
        if (!groupName.trim()) {
            showError('Group name cannot be empty');
            return;
        }

        createGroup(
            groupName.trim(),
            (groupId) => {
                dispatch(addGroup({groupId: groupId, groupName: groupName.trim()}))
                dispatch(setCurrentGroupId({ id: groupId}));
            },
            (error) => {
                showError(`Failed to create group: ${error}`);
            }
        );
    };

    const handleSelectGroup = (groupId: number) => {
        if (currentGroupId === groupId) {
            return;
        }
        dispatch(setCurrentGroupId({id: groupId}));
    };


    return (
        <GroupSelector
            onGroupCreateHandler={(groupName) => handleCreateGroup(groupName)}
            onGroupSelectionHandler={(groupId) => handleSelectGroup(groupId)}
            currentGroupId = {currentGroupId}
            groups={groups}
        />
    )
}

export default Groups;