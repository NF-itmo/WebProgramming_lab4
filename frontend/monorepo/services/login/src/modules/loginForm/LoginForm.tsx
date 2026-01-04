import { registerReq } from "./api/registerReq";
import { loginReq } from "./api/loginReq";
import LoginFormComponent from "../../components/loginForm/LoginForm"
import { useNavigate } from "react-router-dom";
import { setToken } from "@packages/shared";
import { useAppDispatch } from "@packages/shared";
import { useError } from "@packages/shared";

const LoginForm = () => {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const { showError } = useError();
    
    return (
        <LoginFormComponent
            onRegisterHandler={
                (login, password) => {
                    if (login === "" || password === "") return showError("Login and password must be non-empty")
                    if (password.length < 4) return showError("Password should contain at least 4 symbols")
                    registerReq(
                        login,
                        password,
                        (token) => {
                            dispatch(setToken({token: token}))
                            navigate("/")
                        },
                        (descr) => showError(descr)
                    )
                    
                }
            }
            onLoginHandler={
                (login, password) => {
                    if (login === "" || password === "") return showError("Login and password must be non-empty")
                    loginReq(
                        login,
                        password,
                        (token) => {
                            dispatch(setToken({token: token}))
                            navigate("/")
                        },
                        (descr) => showError(descr)
                    )
                }
            }
        />
    )
}

export default LoginForm;