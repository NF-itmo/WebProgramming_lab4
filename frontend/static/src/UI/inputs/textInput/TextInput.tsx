import styles from "./TextInput.module.css"

type props = {
    inputGroupName: string,
    welcomeText?: string,
    defaultValue?: string,
    minLength?: number,
    onChangeHandler?: (value: string) => void
}


const TextInput = (
    {
        inputGroupName,
        welcomeText = "",
        defaultValue = "",
        minLength = 1,
        onChangeHandler = (value: string) => {}
    }: props
) => {
    return (
        <input
            id={inputGroupName}
            name={inputGroupName}
            defaultValue={defaultValue} 
            placeholder={welcomeText}
            onChange={
                (e) => onChangeHandler(e.target.value)
            }
            minLength={minLength}
            className={styles['text-input']}
            required
        >
        </input>
    )
}

export default TextInput;