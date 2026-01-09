import { Routes, Route, useNavigate } from 'react-router-dom';
import { Loader, useAppSelector } from "@packages/shared";
import React from 'react';
import "./themes/default.css"
import "./App.css"
import { validateReq } from './api/vaiidateReq';

const LoginApp = React.lazy(() =>
  import('login/LoginApp').catch(() => ({
    default: () => <div>Error loading Login app</div>,
  }))
);

const MainApp = React.lazy(() =>
  import('main/MainApp').catch(() => ({
    default: () => <div>Error loading Main app</div>,
  }))
);

const App = () =>  {
  const navigate = useNavigate();

  React.useEffect(() => {
    validateReq({
      onUnauthorized: () => navigate('/login')
    })
  }, []);

  return (
    <Routes>
      <Route
        path="/login"
        element={
          <React.Suspense fallback={<Loader text={"Loading Login..."}/>}>
            <LoginApp />
          </React.Suspense>
        }
      />
      <Route
        path="/*"
        element={
          <React.Suspense fallback={<Loader text={"Loading Main App..."}/>}>
            <MainApp />
          </React.Suspense>
        }
      />
    </Routes>
  );
}

export default App;