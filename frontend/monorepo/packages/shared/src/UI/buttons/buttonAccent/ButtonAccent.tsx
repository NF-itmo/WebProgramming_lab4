import styles from "./ButtonAccent.module.css"

type props = {
    text: string;
    type?: "button" | "submit" | "reset";
    onClick?: () => void;
}

const ButtonAccent = ({
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

export default ButtonAccent;