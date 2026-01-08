export type axisNameConfig = {
    shiftX: number;
    shiftY: number;
    name: string;
};
export type arrowConfig = {
    length: number,
    width: number
};
export type ticksConfig =  {
    size: number,
    step: number,
    captionShiftX: number,
    captionShiftY: number,
    captionsGenerator: (n: number) => string
};

export const defaultAxisNameConfig = {
    shiftX: -15,
    shiftY: 15,
    name: "X"
}
export const defaultTicksConfig = {
    size: 5,
    step: 40,
    captionShiftX: 0,
    captionShiftY: 0,
    captionsGenerator: (n: number) => `R ${n}`
}
export const defaultArrowConfig = {
    length: 10,
    width: 4
}