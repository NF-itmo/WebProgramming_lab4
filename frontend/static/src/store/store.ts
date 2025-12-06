import { configureStore } from '@reduxjs/toolkit';
import pointsReducer from "./slices/pointSlice";
import radiusReducer from './slices/radiusSlice';
import tockenReducer, { tokenLocalStorageMiddleware } from './slices/tokenSlice';

export const store = configureStore({
    reducer: {
        points: pointsReducer,
        radius: radiusReducer,
        token: tockenReducer
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware().concat(tokenLocalStorageMiddleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;