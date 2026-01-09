import { useEffect, useState } from "react";

import { EmptyTableStub, Pagination } from "@packages/shared";
import { Header2 } from "@packages/shared";
import styles from "./PaginatedHistoryTable.module.css";

import TableRow from "./tableRow/TableRow";


type Props = {
    tableData?: {
        timestamp: number;
        x: number;
        y: number;
        r: number;
        isHitted: boolean;
    }[];
    currentPage?: number
    rowsPerPage?: number,
    onPageChange?: (pageNumber: number) => void,
    totalPagesCnt?: number
};

const HistoryTable = (
    {
        tableData = [],
        onPageChange = () => {},
        totalPagesCnt = 1,
        currentPage = 1
    }: Props) => {

    return (
        <div className={styles["history-section"]}>
            <Header2>История введенных данных</Header2>

            <Pagination
                totalPages={totalPagesCnt}
                currentPage={currentPage}
                onPageChange={onPageChange}
            >
                <table className={styles["history-table"]}>
                    <thead>
                        <tr>
                            <th>Дата и время</th>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Результат</th>
                        </tr>
                    </thead>

                    <tbody>
                        {totalPagesCnt === 0 ? (
                            <EmptyTableStub columnsCnt={6} />
                        ) : (
                            tableData.map((elem, idx) => (
                                <TableRow
                                    timestamp={elem.timestamp}
                                    x={elem.x}
                                    y={elem.y}
                                    r={elem.r}
                                    isHitted={elem.isHitted}
                                    key={idx}
                                />
                            ))
                        )}
                    </tbody>
                </table>
            </Pagination>
        </div>
    );
};

export default HistoryTable;
