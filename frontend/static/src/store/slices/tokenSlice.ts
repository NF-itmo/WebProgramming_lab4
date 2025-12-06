import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { createSessionStorageMiddleware } from '../middleware/sessionStorageMiddleware';

type tokenState = {
    token: string;
}

const initialState: tokenState = (() => {
    try {
        const raw = sessionStorage.getItem('token');
        const parsed = raw ? JSON.parse(raw) : null;
        return parsed && typeof parsed.token === "string"
            ? parsed
            : { token: "" };
    } catch {
        return { token: "" };
    }
})();

export const tokenSlice = createSlice({
    name: 'token',
    initialState,
    reducers: {
        setToken: (state, action: PayloadAction<tokenState>) => {
            state.token = action.payload.token;
        }
    },
});

export const { setToken } = tokenSlice.actions;
export default tokenSlice.reducer;
export const tokenLocalStorageMiddleware = createSessionStorageMiddleware(setToken, 'token');