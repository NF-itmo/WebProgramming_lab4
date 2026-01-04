import styles from "./Axis.module.css";

import Tick from "../tick/Tick";
import { rotate } from "../../../utils/math";

import { arrowConfig, axisNameConfig, ticksConfig } from "./types"
import { defaultArrowConfig, defaultAxisNameConfig, defaultTicksConfig } from "./types"
import Caption from "../caption/Caption";


type props = {
    x1: number,
    x2: number,
    y1: number,
    y2: number,

    axisNameConfig?: Partial<axisNameConfig>,
    arrowConfig?: Partial<arrowConfig>,
    ticksConfig?: Partial<ticksConfig>
}

function* ticks(
    start: number,
    step: number,
    x1: number,
    y1: number,
    length: number,
    angle: number
): Generator<{ x: number; y: number }, void, undefined> {
    const pointAt = (dist: number) => ({
        x: x1 + Math.cos(angle) * dist,
        y: y1 + Math.sin(angle) * dist,
    });

    let index = 1;

    while (start + index * step <= length || start - index * step >= 0) {
        let d = start + index * step;
        if (d <= length) yield pointAt(d);

        d = start - index * step;
        if (d >= 0) yield pointAt(d);

        index++;
    }
}


const Axis = (
    {
        x1,
        x2,
        y1,
        y2,
        axisNameConfig,
        ticksConfig,
        arrowConfig
    }: props
) => {
    const _axisNameConfig = {...defaultAxisNameConfig, ...axisNameConfig}
    const _ticksConfig = {...defaultTicksConfig, ...ticksConfig}
    const _arrowConfig = {...defaultArrowConfig, ...arrowConfig}

    const angle = Math.atan2(y2 - y1, x2 - x1);
    const length = Math.hypot(x2 - x1, y2 - y1);
    const center = length/2;

    const rotatedArrowPoint1 = rotate(-_arrowConfig.length, +_arrowConfig.width, angle)
    const rotatedArrowPoint2 = rotate(-_arrowConfig.length, -_arrowConfig.width, angle)

    const arrowCurve = `${x2},${y2}
        ${x2+rotatedArrowPoint1.x},${y2+rotatedArrowPoint1.y}
        ${x2+rotatedArrowPoint2.x},${y2+rotatedArrowPoint2.y}`;

    const _captionsGenerator = (idx: number): string => {
        const isTickPositive = idx%2 === 0;
        const tickNumber = (isTickPositive ? idx/2+1 : -idx/2-0.5)
        return _ticksConfig.captionsGenerator(tickNumber)
    }

    return (
        <g>
            <line className={styles["svg-cord-line"]} x1={x1} x2={x2} y1={y1} y2={y2}></line>
            <polygon className={styles["svg-cord-arrow"]} points={arrowCurve}></polygon>

            <Caption
                x={x2 + _axisNameConfig.shiftX}
                y={y2 + _axisNameConfig.shiftY}
                text={_axisNameConfig.name}
            />

            <g>
                {[...ticks(center, _ticksConfig.step, x1, y1, length, angle)].map(
                    ({ x, y }, idx) => (
                        <>
                            <Tick
                                x={x}
                                y={y}
                                angle={angle}
                                size={4}
                                key={"tick".concat(String(idx))}
                            />
                            <Caption
                                x={x + _ticksConfig.captionShiftX}
                                y={y + _ticksConfig.captionShiftY}
                                text={_captionsGenerator(idx)}
                                key={"caption".concat(String(idx))}
                            />
                        </>
                    )
                )}
            </g>
        </g>
    )
}

export default Axis;