import { ReactNode } from "react";
import styles from "./LoginPageLayout.module.css"

type props = {
    children: ReactNode | ReactNode[]
}

const LoginPageLayout = (
    {
        children
    }: props
) => {
    return (
        <div className={styles.containerWrapper}>
            <div className={styles.container}>
                {children}
            </div>
        </div>
    );
}

export default LoginPageLayout;