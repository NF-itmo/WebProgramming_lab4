import { useEffect, useState } from "react";
import styles from "./Error.module.css";

type Props = {
    text: string;
    onClose?: () => void;
    duration?: number;
};

const Error = (
    {
        text,
        onClose,
        duration = 5000
    }: Props
) => {
    const [visible, setVisible] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => {
            setVisible(false);
            onClose?.();
        }, duration);
        
        return () => clearTimeout(timer);
    }, [duration, onClose]);

    if (!visible) return null;

    return <div className={styles["error-message"]}>{text}</div>;
};

export default Error;
