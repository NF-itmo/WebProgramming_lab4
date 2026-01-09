import styles from "./DropdownOption.module.css";

type Props = {
  name: string;
  onClick: () => void
};

const DropdownOption = ({
  name,
  onClick
}: Props) => {
  return (
    <div className={styles.option} onClick={() => onClick()}>
      {name}
    </div>
  );
};

export default DropdownOption;
export type {Props as optionSettings};