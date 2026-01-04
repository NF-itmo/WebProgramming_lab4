import styles from "./MultiInput.module.css"

type props = {
    inputGroupName: string,
    inputConfig: TextInput | NumberInput
}

type TextInput = {
    type: "text",
    welcomeText?: string,
    defaultValue?: string,
    minLength?: number,
    onChangeHandler: (value: string) => void
}
type NumberInput = {
    type: "number",
    from: number,
    to: number,
    defaultValue?: number,
    onChangeHandler: (value: number) => void
}

const MultiInput = (
    {
        inputGroupName,
        inputConfig
    }: props
) => {
    if (inputConfig.type === "text") {
        return (
            <input
                id={inputGroupName}
                name={inputGroupName}
                defaultValue={inputConfig.defaultValue} 
                placeholder={inputConfig.welcomeText}
                onChange={
                    (e) => inputConfig.onChangeHandler(e.target.value)
                }
                minLength={inputConfig.minLength}
                className={styles['input']}
                required
            >
            </input>
        )
    } else if (inputConfig.type === "number"){
        return (
            <input
                type="number"
                id={inputGroupName}
                name={inputGroupName}
                min={inputConfig.from}
                max={inputConfig.to} 
                defaultValue={inputConfig.defaultValue} 
                step="any" 
                placeholder={`Введите ${inputGroupName}`}
                onChange={
                    (e) => inputConfig.onChangeHandler(
                        Number(e.target.value)
                    )
                }
                className={styles['numeric-input']}
                required
            >
            </input>
        );  
    }
    throw new Error("No such input type")
    
}

export default MultiInput;