import { ReactNode } from "react"
import styles from "./TableCell.module.css"

type props = {
    children: ReactNode | ReactNode[]
}

const TableCell = (
    {
       children
    }: props
) => {
    return (
        <td className={styles["table-cell"]}>
            {children}
        </td>
    )
}

export default TableCell;