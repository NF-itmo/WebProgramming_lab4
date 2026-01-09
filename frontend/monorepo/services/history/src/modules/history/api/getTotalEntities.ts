type Params = {
    groupId?: number;
    onSuccess: (count: number) => void;
    onError?: (descr: string) => void;
};

export const getTotalEntities = ({
    groupId,
    onSuccess,
    onError = (e) => console.error(e)
}: Params) => {
    let url = `https://localhost/api/history/count`;
    if (groupId) {
        url += `?groupId=${groupId}`;
    }

    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            const count = typeof data.count === 'number' ? data.count : 0;
            onSuccess(count);
        })
        .catch((error) => {
            onError(error.message || "Failed to fetch count");
        });
};
