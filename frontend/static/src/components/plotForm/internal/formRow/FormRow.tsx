import styles from "./FormRow.module.css";

import { ReactNode } from "react";

type props = {
   parameterName: string,
   children: ReactNode | ReactNode[]
}

const FormRow = (
    {parameterName, children}: props
) => {
    return (
        <div className={styles['form-row']}>
            <div className={styles['row-label']}>
                <p>Выберете {parameterName}:</p>
            </div>
            <div className={styles['row-input']}>
                {children}
            </div>
        </div>
    )
}

export default FormRow;