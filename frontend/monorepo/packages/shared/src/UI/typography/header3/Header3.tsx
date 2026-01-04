import { ReactNode } from "react";
import styles from "./Header3.module.css"

type props = {
    children: ReactNode | ReactNode[]
}

const Header3 = (
    {
        children
    }: props
) => {
    return (
        <h3 className={styles.header2}>
            {children}
        </h3>
    )
}

export default Header3;