export const createGroup = (
    name: string,
    token: string,

    onSuccess: (groupId: number) => void,
    onError: (descr: string) => void = (e) => console.log(e)
) => {
    fetch('https://localhost/api/history/groups/create', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ groupName: name })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server error: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data && data.groupId) {
                onSuccess(data.groupId);
            } else {
                onError('Сервер вернул некорректный ответ');
            }
        })
        .catch(error => {
            onError(`Неожиданная ошибка: ${error.message}`);
        });
}