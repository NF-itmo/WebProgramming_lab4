import {soapRequest} from "../../helpers/soap"

type params = {
    token: string,
    x: number,
    y: number,
    r: number,

    onSuccess: (isHitted: boolean, timestamp: number) => void,
    onError?: (descr: string) => void
}

export const checkReq = ({
        token,
        x,
        y,
        r,
        onSuccess,
        onError  = (e) => console.log(e)
    }: params
) => {
    return soapRequest({
        url: "https://localhost/api/checker",
        method: "check",
        bodyParams: {
            namespace: "http://checker.services.web3.org/",
            data: {
                x: x,
                y: y,
                r: r
            }
        },
        headerParams: [
            {
                namespace: "http://bearer.services.web3.org/",
                data: {
                    Authorization: `Bearer ${token}`
                }
            }
        ],
        onSuccess: (result) => {
            const isHitted = result?.querySelector("result")?.textContent;
            const timestamp = result?.querySelector("timestamp")?.textContent;

            if (isHitted && timestamp) {
                return onSuccess(
                    isHitted === "true",
                    Number(timestamp)
                )
            } 
            return onError(`Сервер вернул некорректный ответ`)
        },
        onError: (status, result) => {
            const failMsg = result?.innerHTML;
            return onError(`Неожиданная ошибка - ${status}: ${failMsg ? failMsg : ''}`);
        }}
    )
}