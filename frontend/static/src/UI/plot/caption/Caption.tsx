import styles from "./Caption.module.css";

type props = {
    x: number,
    y: number,
    text: string
}

const Caption = (
    {
        x,
        y,
        text
    }: props
) => {
    return (
        <text
            className={styles["svg-caption"]}
            x={x}
            y={y}
        >
            {text}
        </text>
    )
}

export default Caption;