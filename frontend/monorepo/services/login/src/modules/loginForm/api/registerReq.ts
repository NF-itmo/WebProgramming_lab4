export const registerReq = (
    login: string,
    password: string,

    onSuccess: () => void,
    onError: (descr: string) => void = (e) => console.log(e)
) => {
    fetch('https://localhost/api/auth/register', {
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
        .then(() => onSuccess())
        .catch(error => {
            onError(`Неожиданная ошибка: ${error.message}`);
        });
}