import { ReactNode } from "react"
import styles from "./TopbarSkeleton.module.css"

type props = {
    children: ReactNode | ReactNode[]
}

const TopbarSkeleton = ({
    children
}: props ) => {
    return (
        <div className={styles.topbar}>
            {children}
        </div>
    )
}

export default TopbarSkeleton;