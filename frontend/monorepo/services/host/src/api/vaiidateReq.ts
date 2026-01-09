type params = {
    onSuccess?: () => void,
    onUnauthorized?: () => void,
    onError?: (descr: string) => void
}

export const validateReq = ({
        onSuccess = () => {},
        onUnauthorized = () => {},
        onError = (e) => console.log(e),
    }: params
) => {
    fetch('https://localhost/api/auth/validate', {
        method: 'GET'
    })
        .then(response => {
            if (response.status === 401) {
                onUnauthorized();
            }
            else if (!response.ok) {
                throw new Error(`Server error: ${response.status}`);
            }
            return response.json();
        })
        .then(() => {
            onSuccess()
        })
        .catch(error => {
            onError(`Неожиданная ошибка: ${error.message}`);
        });
}