import { useEffect, useState } from "react";
import Table from "../../components/paginatedHistoryTable/PaginatedHistoryTable";
import { useAppDispatch, useAppSelector } from "../../store/hooks/redux";
import { fromArray, appendPointsArray } from "../../store/slices/pointSlice";
import { getHistory } from "./api/getHistory";
import { getTotalEntities } from "./api/getTotalEntities";
import useError from "../error/useError";

type props = {
    rowsPerPage?: number
}

type HistoryPoint = {
    timestamp: number,
    x: number,
    y: number,
    r: number,
    isHitted: boolean
}

const History = ({
    rowsPerPage = 10
}: props) => {
    const dispatch = useAppDispatch();
    const { points } = useAppSelector((state) => state.points);
    const { token } = useAppSelector((state) => state.token);
    const { showError } = useError();
    const [getPagesCnt, setPagesCnt] = useState<number>(1);
    const [getDisplayedPointsStartIdx, setDisplayedPointsStartIdx] = useState<number>(0); 
    const [getCurrentPagePoints, setCurrentPagePoints] = useState<HistoryPoint[]>([]);
    const [totalEntities, setTotalEntities] = useState<number>(0);

    useEffect(() => {
        getTotalEntities({
            token: token,
            onSuccess: (count) => {
                setTotalEntities(count);
                const _pagesCnt = Math.ceil(count / rowsPerPage);
                setPagesCnt(_pagesCnt > 0 ? _pagesCnt : 1);
            },
            onError: (descr) => showError(descr)
        });
    }, []);


    useEffect(() => {
        const startIdx = getDisplayedPointsStartIdx;
        const endIdx = startIdx + rowsPerPage;
        
        if (endIdx > points.length) {
            const missingPointsCount = endIdx - points.length;
            getHistory({
                start: points.length,
                length: missingPointsCount,
                token: token,
                onSuccess: (data) => {
                    dispatch(appendPointsArray(data));
                },
                onError: (descr) => showError(descr)
            });
        }

        const pagePoints = points.slice(startIdx, endIdx)
            .map((elem) => ({
                timestamp: elem.timestamp * 1000,
                x: elem.x,
                y: elem.y,
                r: elem.r,
                isHitted: elem.isHitted
            }));
        
        setCurrentPagePoints(pagePoints);
    }, [getDisplayedPointsStartIdx, points]);


    useEffect(() => {
        const _pagesCnt = Math.ceil(totalEntities/ rowsPerPage);
        setPagesCnt(_pagesCnt > 0 ? _pagesCnt : 1);
    }, [totalEntities]);

    const handlePageChange = (pageNumber: number) => {
        const newStartIdx = (pageNumber - 1) * rowsPerPage;
        setDisplayedPointsStartIdx(newStartIdx);
    };

    return (
        <Table
            tableData={getCurrentPagePoints}
            totalPagesCnt={getPagesCnt}
            onPageChange={handlePageChange}
        />
    );
};

export default History;