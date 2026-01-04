import React from 'react';
import ReactDOM from 'react-dom/client';
import App from "./App"
import './index.css';
import { ErrorProvider, store } from '@packages/shared';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <ErrorProvider>
      <Provider store={store}>
        <BrowserRouter>
          <App/>
        </BrowserRouter>
      </Provider>
    </ErrorProvider>
  </React.StrictMode>
);
