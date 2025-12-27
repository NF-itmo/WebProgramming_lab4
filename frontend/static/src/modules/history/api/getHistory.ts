type Params = {
    start: number;
    length: number;
    token: string;
    groupId?: number;
    onSuccess: (data: Result[]) => void;
    onError?: (descr: string) => void;
};

type Result = {
    x: number;
    y: number;
    r: number;
    timestamp: number;
    isHitted: boolean;
};

export const getHistory = ({
    start,
    length,
    token,
    groupId,
    onSuccess,
    onError = (e) => console.error(e)
}: Params) => {
    let url = `https://localhost/api/history?start=${start}&length=${length}&groupId=${groupId}`;

    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            const points: Result[] = Array.isArray(data) ? data : (data.points || []);
            onSuccess(points);
        })
        .catch((error) => {
            onError(error.message || "Failed to fetch history");
        });
};
