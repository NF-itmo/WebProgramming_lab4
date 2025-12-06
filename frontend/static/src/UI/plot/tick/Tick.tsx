import styles from "./Tick.module.css";

import { rotate } from "../../../utils/math";

type props = {
    x: number,
    y: number,
    size?: number,
    angle?: number
}

const Tick = (
    {
        x,
        y,
        angle = 0,
        size = 4
    }: props
) => {
    const rotatedPoint1 = rotate(0, +size, angle);
    const rotatedPoint2 = rotate(0, -size, angle);

    return (
        <g>
            <line
                className={styles["svg-cord-line"]}
                x1={x+rotatedPoint1.x}
                x2={x+rotatedPoint2.x}
                y1={y+rotatedPoint1.y}
                y2={y+rotatedPoint2.y}
                stroke="black"
            ></line>
        </g>
    )
}

export default Tick;