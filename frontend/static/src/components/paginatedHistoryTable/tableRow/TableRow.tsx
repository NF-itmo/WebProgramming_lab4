import TableCell from "../../../UI/table/tableCell/TableCell";
import TableRowUI from "../../../UI/table/tableRow/TableRow";

type props = {
    timestamp: number,
    x: number,
    y: number,
    r: number,
    isHitted: boolean
}

const TableRow = (
    {
        timestamp,
        x,
        y,
        r,
        isHitted
    }: props
) => {
    return (
        <TableRowUI>
            <TableCell>{new Date(timestamp).toLocaleString('ru-RU')}</TableCell>
            <TableCell>{x.toFixed(2)}</TableCell>
            <TableCell>{y.toFixed(2)}</TableCell>
            <TableCell>{r}</TableCell>
            <TableCell>{ isHitted ? "Hit" : "Miss" }</TableCell>
        </TableRowUI>
    )
}

export default TableRow;