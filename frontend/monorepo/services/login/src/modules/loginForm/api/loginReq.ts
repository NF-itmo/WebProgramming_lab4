export const loginReq = (
    login: string,
    password: string,

    onSuccess: (token: string) => void,
    onError: (descr: string) => void = (e) => console.log(e)
) => {
    fetch('https://localhost/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ login, password })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server error: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data && data.token) {
                onSuccess(data.token);
            } else {
                onError('Сервер вернул некорректный ответ');
            }
        })
        .catch(error => {
            onError(`Неожиданная ошибка: ${error.message}`);
        });
}