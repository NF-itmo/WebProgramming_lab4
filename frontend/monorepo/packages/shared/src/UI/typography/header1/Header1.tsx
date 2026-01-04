import { ReactNode } from "react";
import styles from "./Header1.module.css"

type props = {
    children: ReactNode | ReactNode[]
}

const Header1 = (
    {
        children
    }: props
) => {
    return (
        <h1 className={styles.header1}>
            {children}
        </h1>
    )
}

export default Header1;