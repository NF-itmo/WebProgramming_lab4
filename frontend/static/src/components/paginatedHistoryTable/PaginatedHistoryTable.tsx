import { useEffect, useState } from "react";

import EmptyTableStub from "../../UI/table/emptyTableStub/EmptyTableStub";
import Header2 from "../../UI/typography/header2/Header2";
import styles from "./PaginatedHistoryTable.module.css";

import TableRow from "./tableRow/TableRow";
import Pagination from "../pagination/Pagination";

type Props = {
    tableData?: {
        timestamp: number;
        x: number;
        y: number;
        r: number;
        isHitted: boolean;
    }[];
    rowsPerPage?: number,
    onPageChange?: (pageNumber: number) => void,
    totalPagesCnt?: number
};

const HistoryTable = (
    {
        tableData = [],
        onPageChange = () => {},
        totalPagesCnt = 1
    }: Props) => {
    const [page, setPage] = useState(1);
    useEffect(() => {
        onPageChange(page)
    }, [page])

    return (
        <div className={styles["history-section"]}>
            <Header2>История введенных данных</Header2>

            <Pagination
                totalPages={totalPagesCnt}
                currentPage={page}
                onPageChange={setPage}
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
