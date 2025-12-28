import { group } from "console";
import GroupSelector from "../../components/groupSelector/GroupSelector";
import { useAppDispatch, useAppSelector } from "../../store/hooks/redux";
import { addGroup, clearGroups, setCurrentGroupId, setGroups,  } from "../../store/slices/groupSlice";
import useError from "../error/useError";
import { createGroup } from "./api/createGroup";
import { getGroups } from "./api/getGroups";
import { useEffect } from "react";

const Groups = () => {
    const dispatch = useAppDispatch();
    const { token } = useAppSelector((state) => state.token);
    const { currentGroupId, groups } = useAppSelector((state) => state.group);
    const { showError } = useError();

    useEffect(
        () => {
            getGroups({
                token: token,
                onSuccess: (data: {groupId: number, groupName: string}[]) =>  {
                    dispatch(clearGroups())
                    dispatch(setGroups(data))

                    if (data.length !== 0){
                        dispatch(setCurrentGroupId({ id: data[0].groupId}));
                    }
                }
            })
        }, [token]
    )
    
    const handleCreateGroup = (groupName: string) => {
        if (!groupName.trim()) {
            showError('Group name cannot be empty');
            return;
        }

        createGroup(
            groupName.trim(),
            token,
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