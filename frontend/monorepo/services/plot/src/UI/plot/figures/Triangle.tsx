import styles from "./figure.module.css"

type props = {
    point1: point,
    point2: point,
    point3: point
}

type point = {
    x: number,
    y: number
}

const Triangle = (
    {point1, point2, point3}: props
) => {
    const points = `${point1.x}, ${point1.y}
        ${point2.x}, ${point2.y}
        ${point3.x}, ${point3.y}`
    
    return (
        <polygon  className={styles['svg-fill-area']} points={points}></polygon>
    );
}

export default Triangle;