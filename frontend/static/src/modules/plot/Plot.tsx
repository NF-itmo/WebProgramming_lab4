import PlotComponent from "../../components/plot/Plot"

import { useAppDispatch, useAppSelector } from "../../store/hooks/redux";
import { addPoint } from "../../store/slices/pointSlice";
import useError from "../error/useError";

import { checkReq } from "./api/checkReq";
import { findNearestNumber } from "./helpers/findNearestNumber";

const Plot = () => {
    const dispatch = useAppDispatch();

    const { points } = useAppSelector((state) => state.points);
    const { currentR } = useAppSelector((state) => state.radius);
    const { token } = useAppSelector((state) => state.token);
    const { showError } = useError();
    
    return (
        <PlotComponent
            points = {
                points.map(
                    (point) => ({
                        x: point.x / point.r,
                        y: point.y / point.r
                    })
                )
            }
            clickHandler = {
                (x,y) => {
                    const nearestX = findNearestNumber(
                        x*currentR,
                        [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]
                    );
                    checkReq({
                        token: token,
                        x: nearestX,
                        y: y * currentR,
                        r: currentR,

                        onSuccess: (isHitted, timestamp) => {
                            dispatch(addPoint({
                                x: nearestX,
                                y: y * currentR,
                                r: currentR,
                                isHitted: isHitted,
                                timestamp: timestamp
                            }))
                        },
                        onError: (descr) => showError(descr)
                    });
                    
                    
                }
            }
        />
    )
}

export default Plot;