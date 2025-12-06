import { FormEvent } from 'react';
import FromComponent from "../../components/plotForm/PlotForm"
import { useAppDispatch, useAppSelector } from "../../store/hooks/redux";

import { setRadius } from "../../store/slices/radiusSlice";
import { addPoint } from '../../store/slices/pointSlice';
import { checkReq } from './api/checkReq';
import useError from '../error/useError';

const PlotForm = () => {
    const dispatch = useAppDispatch();

    const { token } = useAppSelector((state) => state.token);
    const { showError } = useError();

    return (
        <FromComponent
            onSubmitHandler={
                (event: FormEvent<HTMLFormElement>) => {
                    event.preventDefault()
                    
                    const formData = new FormData(event.currentTarget);

                    const r = Number(formData.get('R'));
                    const x = Number(formData.get('X'));
                    const y = Number(formData.get('Y'));
                    
                    checkReq({
                        token: token,
                        x: x,
                        y: y,
                        r: r,

                        onSuccess: (isHitted, timestamp) => {
                            dispatch(addPoint({
                                x: x,
                                y: y,
                                r: r,
                                isHitted: isHitted,
                                timestamp: timestamp
                            }))
                        },
                        onError: (descr) => showError(descr)
                    });
                }
            }
            
            formRowConfig={[
                    {
                        parameterName: "X",
                        inputConfig: {
                            type: "radio",
                            options: [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2],
                            defaultValue: 1
                        }
                    },
                    {
                        parameterName: "Y",
                        inputConfig: {
                            type: "number",
                            from: -3,
                            to: 3,
                            defaultValue: 0
                        }
                    },
                    {
                        parameterName: "R",
                        inputConfig: {
                            type: "radio",
                            options: [0.5, 1, 1.5, 2],
                            defaultValue: 1,
                            onChangeHandler: (value) => dispatch(setRadius({currentR: value}))
                        }
                    }
                ]}
        />
    )
}

export default PlotForm;