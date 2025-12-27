import { createSlice, PayloadAction } from "@reduxjs/toolkit";

type GroupState = {
    currentGroupId?: number;
    groups: Record<number, string>;
};

const initialState: GroupState = {
    currentGroupId: undefined,
    groups: {}
};

const groupSlice = createSlice({
    name: "group",
    initialState,
    reducers: {
        setCurrentGroupId: (state, action: PayloadAction<{ id: number;}>) => {
            state.currentGroupId = action.payload.id;
        },
        setGroups: (state, action: PayloadAction<Array<{ groupId: number; groupName: string;}>>) => {
            state.groups = {};
            action.payload.forEach(({ groupId, groupName }) => {
                state.groups[groupId] = groupName;
            });
        },
        clearGroups: (state) => {
            state.groups = {};
        },
        addGroup: (state, action: PayloadAction<{ groupId: number; groupName: string;}>) => {
            state.groups[action.payload.groupId] = action.payload.groupName;
        }
    }
});

export const { setCurrentGroupId, setGroups, clearGroups, addGroup } = groupSlice.actions;
export default groupSlice.reducer;