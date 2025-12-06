import { ReactNode } from "react";
import styles from "./Header2.module.css"

type props = {
    children: ReactNode | ReactNode[]
}

const Header2 = (
    {
        children
    }: props
) => {
    return (
        <h2 className={styles.header2}>
            {children}
        </h2>
    )
}

export default Header2;