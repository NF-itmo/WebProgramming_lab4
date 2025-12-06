import styles from "./figure.module.css"
import { degrees_to_radians } from "../../../utils/math";

type props = {
    x: number,
    y: number,
    r: number,
    start_degrees?: number,
    finish_degrees?: number
}

const Circle = (
    {
        x,
        y,
        r,
        start_degrees = 0,
        finish_degrees = 360
    }: props
) => {
    const isLargeArcFlag = (finish_degrees - start_degrees) > 180 ? 1 : 0;
    const [start_rads, finish_rads] = [degrees_to_radians(start_degrees), degrees_to_radians(finish_degrees)]

    // See: https://developer.mozilla.org/en-US/docs/Web/SVG/Tutorials/SVG_from_scratch/Paths#arcs
    const path = `M ${x} ${y}
        L ${x+r*Math.cos(start_rads)} ${y+r*Math.sin(start_rads)}
        A ${r} ${r} ${start_degrees} ${isLargeArcFlag} 1 ${x+r*Math.cos(finish_rads)} ${y+r*Math.sin(finish_rads)}
        ${finish_degrees - start_degrees === 360 ? "Z" : ""}`
    
    return (
        <path className={styles['svg-fill-area']} d={path}></path>
    );
}

export default Circle;