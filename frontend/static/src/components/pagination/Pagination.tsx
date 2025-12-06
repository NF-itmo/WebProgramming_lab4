import React, { ReactNode } from "react";
import styles from "./Pagination.module.css";
import ButtonAccent from "../../UI/buttons/buttonAccent/ButtonAccent";


type PaginationProps = {
    children: ReactNode;
    totalPages: number;
    currentPage: number;
    onPageChange: (page: number) => void;
};

const Pagination = ({
    children,
    totalPages,
    currentPage,
    onPageChange,
}: PaginationProps) => {
  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <>
        {children}

        <div className={styles.paginationNav}>
            <ButtonAccent
                text="Prev"
                type="button"
                onClick={
                    () => {
                        if (currentPage !== 1) onPageChange(currentPage - 1)
                    }
                }/>
            {currentPage} / {totalPages}
            <ButtonAccent text="Next" type="button" onClick={
                () => {
                    if (currentPage !== totalPages) onPageChange(currentPage + 1)
                }
            }/>
        </div>
    </>
  );
};

export default Pagination;
