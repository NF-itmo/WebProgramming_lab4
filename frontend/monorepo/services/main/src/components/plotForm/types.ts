export type RadioInputConfig = {
    type: "radio";
    options: number[];
    defaultValue: number,
    onChangeHandler?: (value: number) => void;
};

export type NumericInputConfig = {
    type: "number";
    from: number;
    to: number;
    defaultValue: number,
    onChangeHandler?: (value: number) => void;
};

export type TextInputConfig = {
    type: "text";
    defaultValue?: string,
    onChangeHandler?: (value: string) => void;
};