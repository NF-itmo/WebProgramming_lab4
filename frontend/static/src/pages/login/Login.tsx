import LoginForm from "../../modules/loginForm/LoginForm";
import CenterLayout from "../../UI/layouts/centerLayout/CenterLayout";
import LoginPageLayout from "../../UI/layouts/loginPageLayout/lgoinPageLayout";

const Login = () => {
    return (
        <LoginPageLayout>
            <LoginForm/>
        </LoginPageLayout>
    );
}

export default Login;