import { MouseEvent, ReactNode } from "react"

type props = {
    width: number,
    height: number,
    clickHandler?: (event: MouseEvent<SVGSVGElement>) => void

    children: ReactNode | ReactNode[]
}

const SvgPlane = (
    {
        children,
        width,
        height,
        clickHandler
    }: props
) => {
    const plotPlaneStyles = {
        height: `${height}px`,
        width: `${width}px`
    }

    return (
        <svg
            xmlns="http://www.w3.org/2000/svg" xmlnsXlink="http://www.w3.org/1999/xlink"
            style={plotPlaneStyles}
            onClick={clickHandler}
        >
            {children}
        </svg>
    )
}

export default SvgPlane;