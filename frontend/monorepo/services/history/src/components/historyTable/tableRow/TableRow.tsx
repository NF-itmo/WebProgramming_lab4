import { TableCell } from "@packages/shared";
import { TableRow as TableRowUI } from "@packages/shared";

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