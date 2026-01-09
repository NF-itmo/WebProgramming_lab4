import { getCsrfToken } from "@packages/shared";

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
        onError = (e) => console.log(e)
    }: params
) => {
    if (groupId === undefined) return onError('Сначала нужно выбрать группу');

    fetch('https://localhost/api/geometry/check', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-Token': getCsrfToken()
        },
        body: JSON.stringify({ x: x, y: y, r: r, groupId: groupId })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server error: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data && typeof data.isHitted === 'boolean' && typeof data.timestamp === 'number') {
                onSuccess(data.isHitted, data.timestamp);
            } else if (data && typeof data.result === 'boolean' && typeof data.timestamp === 'number') {
                onSuccess(data.result, data.timestamp);
            } else {
                onError('Сервер вернул некорректный ответ');
            }
        })
        .catch(error => {
            onError(`Неожиданная ошибка: ${error.message}`);
        });
}