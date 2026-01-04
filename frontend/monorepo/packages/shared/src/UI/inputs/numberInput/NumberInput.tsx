import styles from "./NumberInput.module.css"

type props = {
    inputGroupName: string,
    from: number,
    to: number,
    defaultValue?: number,
    onChangeHandler?: (value: number) => void
}


const NumberInputOption = (
    {
        inputGroupName,
        from,
        to,
        defaultValue = 0,
        onChangeHandler = (value) => {}
    }: props
) => {
    return (
        <input
            type="number"
            id={inputGroupName}
            name={inputGroupName}
            min={from}
            max={to} 
            defaultValue={defaultValue} 
            step="any" 
            placeholder={`Введите ${inputGroupName}`}
            onChange={
                (e) => onChangeHandler(
                    Number(e.target.value)
                )
            }
            className={styles['numeric-input']}
            required
        >
        </input>
    );
}

export default NumberInputOption;
