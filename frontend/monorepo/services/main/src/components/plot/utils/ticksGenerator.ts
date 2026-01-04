function* ticks(start: number, step: number, max: number, min: number): Generator<number, void, undefined> {
    let isPositive = true;
    let index = 1;

    while (start + index*step <= max || start - index*step >= min) {
        if (isPositive) {
            let cord = start + index*step
            if (cord <= max) yield cord
        } else {
            let cord = start - index*step
            if (cord >= min) yield cord
        }
        index++;
    }
}

export default ticks;