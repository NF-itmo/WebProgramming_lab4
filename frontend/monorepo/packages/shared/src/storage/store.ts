import { configureStore } from '@reduxjs/toolkit';
import pointsReducer from './slices/pointSlice';
import radiusReducer from './slices/radiusSlice';
import groupReducer from "./slices/groupSlice";

export const store = configureStore({
  reducer: {
    points: pointsReducer,
    radius: radiusReducer,
    group: groupReducer,
  }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
