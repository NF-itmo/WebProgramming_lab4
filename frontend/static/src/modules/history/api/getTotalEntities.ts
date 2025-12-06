import { soapRequest } from "../../helpers/soap";

type Params = {
    token: string;
    onSuccess: (count: number) => void;
    onError?: (descr: string) => void;
};

export const getTotalEntities = ({
    token,
    onSuccess,
    onError = (e) => console.error(e)
}: Params) => {
    return soapRequest({
        url: "https://localhost/api/history",
        method: "getTotal",
        bodyParams: {
            namespace: "http://history.services.web3.org/",
            data: {}
        },
        headerParams: [
            {
                namespace: "http://bearer.services.web3.org/",
                data: { Authorization: `Bearer ${token}` }
            }
        ],
        onSuccess: (xmlDoc) => {
            try {
                if (!xmlDoc) throw new Error("Сервер вернул пустой ответ");

                const count = Number(xmlDoc.querySelector("count")?.textContent);
                if (isNaN(count)) {
                    throw new Error("Некорректный формат числовых данных в ответе сервера");
                }
                return onSuccess(count)
            } catch (err: any) {
                return onError(err.message ?? "Ошибка при обработке ответа сервера");
            }
        },
        onError: (status, result) => {
            const failMsg = result?.innerHTML;
            return onError(`Неожиданная ошибка - ${status}: ${failMsg ? failMsg : ''}`);
        }
    });
};
