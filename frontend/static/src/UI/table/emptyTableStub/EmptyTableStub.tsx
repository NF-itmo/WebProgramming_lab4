import styles from "./EmptyTableStub.module.css"

type props = {
    columnsCnt: number,
    text?: string
}

const EmptyTableStub = (
    {
        columnsCnt,
        text = "История пуста. Заполните форму, чтобы добавить данные."
    }: props
) => {
    return (
        <tr>
            <td id="emptyHistory" className={styles["empty-history"]} colSpan={columnsCnt}>
                {text}
            </td>
        </tr> 
    )
}

export default EmptyTableStub;