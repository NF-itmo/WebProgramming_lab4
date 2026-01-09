type params = {
    x: number,
    y: number,
    r: number,
    groupId?: number,

    onSuccess: (isHitted: boolean, timestamp: number) => void,
    onError?: (descr: string) => void
}

export const checkReq = ({
        x,
        y,
        r,
        groupId,
        onSuccess,
        onError = (e) => console.error(e)
    }: params
) => {
    if (groupId === undefined) return onError('Сначала нужно выбрать группу');
    
    fetch(`https://localhost/api/geometry/check`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({x: x, y: y, r: r, groupId: groupId})
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            const isHitted = typeof data.result === 'boolean' ? data.result : data.isHitted;
            const timestamp = typeof data.timestamp === 'number' ? data.timestamp : 0;
            onSuccess(isHitted, timestamp);
        })
        .catch((error) => {
            onError(error.message || "Failed to check point");
        });
};