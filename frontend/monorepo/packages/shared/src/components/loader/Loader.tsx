import styles from "./Loader.module.css";

type props = {
    text?: string
}

const Loader = (
    {
        text = ''
    }: props
) => {
  return (
    <div className={styles['spinner-wrapper']}>
      <div className={styles['pulse-spinner']}>
        <div className={`${styles['pulse-dot']} ${styles['pulse-dot-1']}`}></div>
        <div className={`${styles['pulse-dot']} ${styles['pulse-dot-2']}`}></div>
        <div className={styles['pulse-dot']}></div>
      </div>
      <div className={styles['spinner-text']}>{ text }</div>
    </div>
  );
};

export default Loader;