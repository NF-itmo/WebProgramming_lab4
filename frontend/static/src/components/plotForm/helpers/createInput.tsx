import { ReactNode } from "react";

import NumberInput from "../../../UI/inputs/numberInput/NumberInput";
import RadioInput from "../../../UI/inputs/radioInput/RadioInput";
import TextInput from "../../../UI/inputs/textInput/TextInput";

import { NumericInputConfig, RadioInputConfig, TextInputConfig } from "../types";

const createInput = (
    groupName: string,
    inputConfig: RadioInputConfig | NumericInputConfig | TextInputConfig,
): ReactNode | ReactNode[] => {
    if (inputConfig.type === "radio") {
        return inputConfig.options.map((value, idx) => (
            <RadioInput
                key={idx}
                inputGroupName={groupName}
                value={value}
                isChecked={value === inputConfig.defaultValue}
                onChangeHandler={inputConfig.onChangeHandler}
            />
        ));
    } else if (inputConfig.type === "number") {
        return (
            <NumberInput
                inputGroupName={groupName}
                from={inputConfig.from}
                to={inputConfig.to}
                onChangeHandler={inputConfig.onChangeHandler}
                defaultValue={inputConfig.defaultValue}
            />
        );
    } else if (inputConfig.type === "text") {
        return (
            <TextInput
                inputGroupName={groupName}
                onChangeHandler={inputConfig.onChangeHandler}
                defaultValue={inputConfig.defaultValue}
                welcomeText={groupName}
            />
        );
    }

    return null;
};

export { createInput };