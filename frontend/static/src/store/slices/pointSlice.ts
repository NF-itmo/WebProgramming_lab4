import { createSlice, PayloadAction } from '@reduxjs/toolkit';


export type Point = {
    x: number;
    y: number;
    r: number;
    timestamp: number;
    isHitted: boolean;
}

type PointsState = {
    points: Point[];
}

const initialState: PointsState = {
    points: []
};

export const pointsSlice = createSlice({
    name: 'points',
    initialState,
    reducers: {
        addPoint: (state, action: PayloadAction<Point>) => {
            const newPoint: Point = {
                ...action.payload
            };
            state.points.unshift(newPoint);
        },
        appendPointsArray: (state, action: PayloadAction<Point[]>) => {
            state.points = state.points.concat(action.payload);
        },
        clearPoints: (state) => {
            state.points = [];
        },
        fromArray: (state, action: PayloadAction<Point[]>) => {
            state.points = action.payload;
        }
    },
});

export const { addPoint, clearPoints, fromArray, appendPointsArray } = pointsSlice.actions;
export default pointsSlice.reducer;
