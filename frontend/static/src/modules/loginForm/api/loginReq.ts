import {soapRequest} from "../../helpers/soap"

export const loginReq = (
    login: string,
    password: string,

    onSuccess: (token: string) => void,
    onError: (descr: string) => void = (e) => console.log(e)
) => {
    return soapRequest({
        url: "https://localhost/api/auth",
        method: "login",
        bodyParams: {
            namespace: "http://auth.services.web3.org/",
            data: {
                login: login,
                password: password
            }
        },
        onSuccess: (result) => {
            const token = result?.querySelector("token")?.textContent;

            if (token) return onSuccess(token)
            return onError(`Сервер вернул некорректный ответ`)
        },
        onError: (status, result) => {
            const failMsg = result?.innerHTML;
            return onError(`Неожиданная ошибка - ${status}: ${failMsg ? failMsg : ''}`);
        }}
    )
}