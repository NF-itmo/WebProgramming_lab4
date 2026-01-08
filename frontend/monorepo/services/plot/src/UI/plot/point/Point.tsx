import styles from "./Point.module.css";

type prpos = {
    x: number,
    y: number,
    r?: number
}

const Point = ({x, y, r=3}: prpos) => {
    return (
        <circle
            cx={x} cy={y} r={r}
            className={styles["point-on-plot"]}
        ></circle>
    )
}

export default Point;