import styles from "./TableRow.module.css"

import { ReactNode } from "react";

type props = {
    children: ReactNode | ReactNode[]
}

const TableRow = (
    {
        children
    }: props
) => {
    return (
        <tr className={styles["table-row"]}>
            {children}
        </tr>
    )
}

export default TableRow;