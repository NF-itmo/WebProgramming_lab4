type groupItem = {
    groupId: number,
    groupName: string
}

type params = {
    start?: number,
    length?: number,
    token: string,

    onSuccess: (response: groupItem[]) => void,
    onError?: (descr: string) => void
}

export const getGroups = (
    {
        token,
        start = 0,
        length = 10,
        onSuccess,
        onError = (e) => console.log(e),
    }: params
) => {
    fetch(`https://localhost/api/history/groups?start=${start}&length=${length}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server error: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data) {
                onSuccess(data);
            } else {
                onError('Сервер вернул некорректный ответ');
            }
        })
        .catch(error => {
            onError(`Неожиданная ошибка: ${error.message}`);
        });
}