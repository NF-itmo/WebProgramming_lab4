import { useEffect, useMemo, useState } from "react";
import Table from "../../components/paginatedHistoryTable/PaginatedHistoryTable";
import { useAppDispatch, useAppSelector } from "../../store/hooks/redux";
import { fromArray, appendPointsArray, setTotalPointsCount, clearPoints } from "../../store/slices/pointSlice";
import { getHistory } from "./api/getHistory";
import { getTotalEntities } from "./api/getTotalEntities";
import useError from "../error/useError";

type Props = {
    rowsPerPage?: number;
};

type HistoryPoint = {
    timestamp: number;
    x: number;
    y: number;
    r: number;
    isHitted: boolean;
};

const History = ({ rowsPerPage = 10 }: Props) => {
    const dispatch = useAppDispatch();
    const { points, totalPoints } = useAppSelector((state) => state.points);
    const { token } = useAppSelector((state) => state.token);
    const { currentGroupId } = useAppSelector((state) => state.group);
    const { showError } = useError();

    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    // get entities and their total count on group change
    useEffect(() => {
        dispatch(clearPoints());
        setCurrentPage(1);
        dispatch(setTotalPointsCount(0))

        if (currentGroupId === undefined) return;

        getTotalEntities({
            token,
            groupId: currentGroupId,
            onSuccess: (count) => {
                dispatch(setTotalPointsCount(count))
            },
            onError: showError
        });
        getHistory({
            start: 0,
            length: rowsPerPage,
            token,
            groupId: currentGroupId,
            onSuccess: (data) => dispatch(fromArray(data)),
            onError: showError
        });
    }, [currentGroupId, token]);

    // recalc total pages on totalEntities change
    useEffect(() => {
        setTotalPages(Math.max(1, Math.ceil(totalPoints / rowsPerPage)))
    }, [Math.ceil(totalPoints/rowsPerPage)])

    const tableData: HistoryPoint[] = useMemo(() => {
        const startIdx = (currentPage - 1) * rowsPerPage;
        const endIdx = startIdx + rowsPerPage;

        return points.slice(startIdx, endIdx).map((p) => ({
            timestamp: p.timestamp * 1000,
            x: p.x,
            y: p.y,
            r: p.r,
            isHitted: p.isHitted
        }));
    }, [points, currentPage, rowsPerPage]);

    const handlePageChange = (page: number) => {
        if (currentGroupId === undefined) return;

        const startIdx = (page - 1) * rowsPerPage;
        const endIdx = startIdx + rowsPerPage;

        if (points.length >= endIdx) return;

        getHistory({
            start: points.length,
            length: endIdx - points.length,
            token,
            groupId: currentGroupId,
            onSuccess: (data) => dispatch(appendPointsArray(data)),
            onError: showError
        });

        setCurrentPage(page);
    };

    return (
        <Table
            tableData={tableData}
            totalPagesCnt={totalPages}
            onPageChange={handlePageChange}
            currentPage={currentPage}
        />
    );
};

export default History;
