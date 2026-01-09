import { useState } from "react";
import DropdownOption, { optionSettings } from "./dropdownOption/DropdownOption";
import styles from "./DropdownMenu.module.css";

type Props = {
  name: string;
  options?: optionSettings[];
};

const DropdownMenu = ({
  name,
  options = []
}: Props) => {
  const [open, setOpen] = useState(false);

  return (
    <div
      className={styles.dropdown}
      onMouseLeave={() => setOpen(false)}
    >
      <button
        className={`${styles.dropbtn} ${open ? styles.active : ""}`}
        onMouseEnter={() => setOpen(!open)}
        onClick={() => setOpen(!open)}
      >
        {name}
      </button>

      {open && (
        <div className={styles.menu}>
          {
            options.map((option, i) => <DropdownOption name={option.name} onClick={option.onClick} key={i}/>)
          }
        </div>
      )}
    </div>
  );
};

export default DropdownMenu;
