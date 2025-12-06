const findNearestNumber = (
    target: number,
    elemsArray: number[]
): number => {
    let closest = elemsArray[0];
    let minDiff = Infinity;
    
    elemsArray.forEach(elem => {
        const diff = Math.abs(target - elem);
        if (diff < minDiff) {
            minDiff = diff;
            closest = elem;
        }
    });

    return closest;
}

export { findNearestNumber };