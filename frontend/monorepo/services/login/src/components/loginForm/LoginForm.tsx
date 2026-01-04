import { FormEventHandler, useState } from "react";
import styles from "./LoginForm.module.css"
import { MultiInput } from "@packages/shared"
import { ButtonPrimary } from "@packages/shared";
import { ButtonSecondary } from "@packages/shared";

type props = {
    onLoginHandler?: (login: string, password: string) => void,
    onRegisterHandler?: (login: string, password: string) => void,
};

const LoginForm = (
    {
        onLoginHandler = (login, password) => {},
        onRegisterHandler = (login, password) => {},
    }: props
) => {
    const [getLogin, setLogin] = useState<string>("");
    const [getPassword, setPassword] = useState<string>("");

    return (
        <div className={styles['form-container']}>
            <MultiInput
                inputGroupName="login"
                inputConfig = {
                    {
                        type: "text",
                        welcomeText: "Логин",
                        defaultValue: "",
                        onChangeHandler: (value: string) => setLogin(value)
                    }
                }
            />
            {/*<MultiInput
                inputGroupName="password"
                inputConfig = {
                    {
                        type: "number",
                        defaultValue: 1,
                        onChangeHandler: (value: number) => {}
                    }
                }
            />*/}
            <MultiInput
                inputGroupName="login"
                inputConfig = {
                    {
                        type: "text",
                        welcomeText: "Пароль",
                        defaultValue: "",
                        onChangeHandler: (value: string) => setPassword(value)
                    }
                }
            />
            <ButtonPrimary
                type="button"
                text="Войти"
                onClick={() => onLoginHandler(getLogin, getPassword)}
            />
            <ButtonSecondary
                type="button"
                text="Зарегистрироваться"
                onClick={() => onRegisterHandler(getLogin, getPassword)}
            />
        </div>
    )
}

export default LoginForm;