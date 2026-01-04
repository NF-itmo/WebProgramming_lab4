import styles from "./ColumnLayout.module.css"

import { ReactNode } from "react";

type props = {
  children: ReactNode | ReactNode[]
}

const ColumnLayout = ({children}: props) => {
    return (
        <div className={styles.container}>
            {children}
        </div>
    );
}

export default ColumnLayout;