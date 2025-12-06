import "./themes/default.css";
import "./App.css";

import Main from './pages/main/Main';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/login/Login";
import ErrorProvider from "./modules/error/ErrorProvider";

function App() {
    return (
        <ErrorProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Main/>} />
                    <Route path="/login" element={<Login/>} />
                </Routes>
            </BrowserRouter>
        </ErrorProvider>
    );
}

export default App;
