import { Header1 } from "@packages/shared";
import { Header3 } from "@packages/shared";
import styles from "./Header.module.css"

const Header = () => {
    return (
        <div className={styles.header}>
            <Header1>Решетников Сергей Евгеньевич</Header1>
            <Header3>Группа: P3208</Header3>
            <Header3>Вариант: 467233</Header3>
            <Header3>Ист. хран. дан. 1</Header3>
        </div>
    );
}

export default Header;