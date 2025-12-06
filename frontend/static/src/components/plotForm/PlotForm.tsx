import { FormEventHandler } from "react";

import styles from "./PlotForm.module.css"

import FormRow from "./internal/formRow/FormRow";
import Button from "../../UI/buttons/buttonPrimary/ButtonPrimary";

import { NumericInputConfig, RadioInputConfig, TextInputConfig } from "./types";
import { createInput } from "./helpers/createInput";

type props = {
    onSubmitHandler: FormEventHandler<HTMLFormElement>,
    formRowConfig: {
        parameterName: string;
        inputConfig: RadioInputConfig | NumericInputConfig | TextInputConfig;
    }[];
};

const Form = (
    {
        onSubmitHandler,
        formRowConfig
    }: props
) => {
    return (
        <form id={styles["graph-test-form"]} onSubmit={onSubmitHandler}>
            <div className={styles["form-grid"]}>
                {formRowConfig.map((rowConfig, idx) => (
                    <FormRow
                        key={idx}
                        parameterName={rowConfig.parameterName}
                    >
                        {
                            createInput(
                                rowConfig.parameterName,
                                rowConfig.inputConfig
                            )
                        }
                    </FormRow>
                ))}
            </div>
            <div className={styles['submit-container']}>
                <Button text={"Отправить форму"}/>
            </div>
        </form>
    );
};

export default Form;
