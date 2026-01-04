import styles from "./CenterLayout.module.css"

import { ReactNode } from "react";

type props = {
  children: ReactNode | ReactNode[]
}

const CenterLayout = ({children}: props) => {
    return (
        <div className={styles.containerWrapper}>
            <div className={styles.container}>
                {children}
            </div>
        </div>
    );
}

export default CenterLayout;