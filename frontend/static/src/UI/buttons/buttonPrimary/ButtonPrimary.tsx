import styles from "./ButtonPrimary.module.css"

type props = {
    text: string;
    type?: "button" | "submit" | "reset";
    onClick?: () => void;
}

const ButtonPrimary = ({
    text,
    type = "submit",
    onClick
}: props) => {
    return (
        <button type={type} className={styles["btn"]} onClick={onClick}>
            {text}
        </button>
    )
}

export default ButtonPrimary;