import { ActionCreatorWithPayload, Middleware } from '@reduxjs/toolkit';

export const createSessionStorageMiddleware = <T>(
  actionCreator: ActionCreatorWithPayload<T>,
  itemName: string
): Middleware => {
  const sessionStorageMiddleware: Middleware = store => next => action => {
    const result = next(action);
    
    if (actionCreator.match(action)) {
      const value =
        typeof action.payload === 'string'
          ? action.payload
          : JSON.stringify(action.payload);

      sessionStorage.setItem(itemName, value);
    }

    return result;
  };

  return sessionStorageMiddleware;
};
