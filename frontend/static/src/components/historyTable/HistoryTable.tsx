import EmptyTableStub from "../../UI/table/emptyTableStub/EmptyTableStub";
import Header2 from "../../UI/typography/header2/Header2";
import styles from "./Table.module.css"

import TableRow from "./tableRow/TableRow";

type props = {
    tableData?: {
        timestamp: number,
        x: number,
        y: number,
        r: number,
        isHitted: boolean
    }[];
}

const HistoryTable = (
    {
        tableData = []
    }: props
) => {
    return (
        <div className={styles["history-section"]}>
            
            <Header2>История введенных данных</Header2>

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
                    {
                        tableData.length === 0 ?
                            <EmptyTableStub columnsCnt={6}/>
                            :
                            tableData.map((elem, idx) => 
                                <TableRow
                                    timestamp={elem.timestamp}
                                    x={elem.x}
                                    y={elem.y}
                                    r={elem.r}
                                    isHitted={elem.isHitted}
                                    key={idx}
                                />
                            )
                    }
                </tbody>
            </table>
        </div>
        
    )
}

export default HistoryTable;