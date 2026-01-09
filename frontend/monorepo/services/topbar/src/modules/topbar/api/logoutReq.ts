type params = {
    onSuccess?: () => void,
    onError?: (descr: string) => void
}

export const logoutReq = ({
        onSuccess = () => {},
        onError = (e) => console.log(e)
    }: params
) => {
    fetch('https://localhost/api/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error(`Server error: ${response.status}`);
            return response.json();
        })
        .then(() => onSuccess())
        .catch((error) => onError(`Неожиданная ошибка: ${error.message}`));
}