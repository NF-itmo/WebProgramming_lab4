import styles from "./RadioInput.module.css";

type props<T> = {
    inputGroupName: string,
    value: T,
    isChecked: boolean,
    onChangeHandler?: (value: T) => void
}


const RadioInput = (
    {
        inputGroupName,
        value,
        isChecked,
        onChangeHandler = (value) => {}
    }: props<number>
) => {
    const optionId: string = inputGroupName + String(value);

    return (
        <div className={styles['input-group']}>
            <input type="radio"
                id={optionId}
                name={inputGroupName}
                value={value}
                defaultChecked={isChecked}
                onChange={
                    (e) => onChangeHandler(
                        Number(e.target.value)
                    )
                }
            />
            <label htmlFor={optionId}>
                {value}
            </label>
        </div>
    );
}

export default RadioInput;