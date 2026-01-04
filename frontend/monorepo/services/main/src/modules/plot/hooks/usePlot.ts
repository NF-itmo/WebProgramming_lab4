import { useState, useCallback } from 'react';

interface Point {
    x: number;
    y: number;
}

const usePlot = () => {
    const [points, setPoints] = useState<Point[]>([]);

    const handlePlotClick = useCallback((event: React.MouseEvent<SVGSVGElement>) => {
        const svg = event.currentTarget;
        const rect = svg.getBoundingClientRect();
        
        const clickPoint: Point = {
            x: event.clientX - rect.left,
            y: event.clientY - rect.top,
        };
        
        setPoints(prev => [...prev, clickPoint]);
    }, []);

    const addPoint = useCallback((point: Point) => {
        setPoints(prev => [...prev, point]);
    }, []);

    const clearPoints = useCallback(() => {
        setPoints([]);
    }, []);

    return [
        points,
        handlePlotClick,
        addPoint,
        clearPoints,
    ];
};

export {usePlot};