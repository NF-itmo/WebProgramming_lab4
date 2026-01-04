import styles from "./figure.module.css"

type props = {
    x: number,
    y: number,
    h: number,
    w: number
}

const Rectangle = (
    {x, y, h, w}: props
) => {
    return (
        <rect x={x} y={y} width={w} height={h} className={styles['svg-fill-area']}></rect> 
    );
}

export default Rectangle;