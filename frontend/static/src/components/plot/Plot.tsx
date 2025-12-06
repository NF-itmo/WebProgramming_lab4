import styles from "./Plot.module.css"

import { MouseEvent } from "react"

import PlotPoint from "../../UI/plot/point/Point"
import Circle from "../../UI/plot/figures/Circle";
import Rectangle from "../../UI/plot/figures/Rectangle";
import Triangle from "../../UI/plot/figures/Triangle";
import SvgPlane from "../../UI/svgPlane/SvgPlane";
import Axis from "../../UI/plot/axis/Axis";

type props = {
    height?: number,
    width?: number,
    unit?: number,
    step?: number,
    points?: point[],
    clickHandler: (x: number, y: number) => void
}

type point = {
    x: number,
    y: number
}

const Plot = (
    {
        height = 200,
        width = 200,
        unit = 80,
        step = 0.5,
        points = [],
        clickHandler
    }: props
) => {
    const [center_x, center_y] = [height/2, width/2];

    const normalizedClickHandler = (event: MouseEvent<SVGSVGElement>) => {
        const [X, Y] = [event.clientX, event.clientY];
        const rect = event.currentTarget.getBoundingClientRect();

        clickHandler(
            (((X - rect.left) - center_x) / unit),
            ((center_y - (Y - rect.top)) / unit),
        )
    }
    const captionsGenerator = (n: number) => {
        //if (n*step%1 !== 0) return `${Math.sign(n)===-1?'-':""}R/${1/(Math.abs(n)*step)}`
        return `${n*step}R`
    }

    return (
        <div className={styles["plot-container"]}>
            <SvgPlane width={width} height={height} clickHandler={normalizedClickHandler}>

                <Circle x={center_x} y={center_y} r={unit} start_degrees={180} finish_degrees={270}/>
                <Rectangle x={center_x} y={center_y-unit} h={unit} w={unit/2}/>
                <Triangle
                    point1={{x: center_x, y: center_y}}
                    point2={{x: center_x+unit, y: center_y}}
                    point3={{x: center_x, y: center_y+unit/2}}
                />

                <Axis
                    x1={0}
                    x2={width}
                    y1={center_y}
                    y2={center_y}
                    axisNameConfig={{
                        name: "X",
                        shiftY: 17
                    }}
                    ticksConfig={{
                        step: unit*step,
                        captionShiftY: -5,
                        captionShiftX: -9,
                        captionsGenerator: captionsGenerator
                    }}
                />
                <Axis
                    x1={center_x}
                    x2={center_x}
                    y1={height}
                    y2={0}
                    axisNameConfig={{
                        name: "Y",
                        shiftY: 15
                    }}
                    ticksConfig={{
                        step: unit*step,
                        captionShiftY: 4,
                        captionShiftX: 6,
                        captionsGenerator: captionsGenerator
                    }}
                />


                {
                    points.map((elem, idx) => (
                        <PlotPoint
                            key={idx}
                            x={unit*elem.x + center_x}
                            y={-unit*elem.y + center_y}
                        />
                    ))
                }
            </SvgPlane>
        </div>
    )
}

export default Plot;