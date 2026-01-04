import styles from "./ButtonSecondary.module.css"

type props = {
    text: string;
    type?: "button" | "submit" | "reset";
    onClick?: () => void;
}

const ButtonSecondary = ({
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

export default ButtonSecondary;