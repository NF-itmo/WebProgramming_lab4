import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type radiusState = {
    currentR: number;
}

const initialState: radiusState = {
    currentR: 1
};

export const radiusSlice = createSlice({
    name: 'radius',
    initialState,
    reducers: {
        setRadius: (state, action: PayloadAction<radiusState>) => {
            state.currentR = action.payload.currentR;
        }
    },
});

export const { setRadius } = radiusSlice.actions;
export default radiusSlice.reducer;
