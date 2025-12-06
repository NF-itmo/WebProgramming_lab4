import { soapRequest } from "../../helpers/soap";

type Params = {
    start: number;
    length: number;
    token: string;
    onSuccess: (data: Result[]) => void;
    onError?: (descr: string) => void;
};

type Result = {
    x: number;
    y: number;
    r: number;
    timestamp: number;
    isHitted: boolean;
};

export const getHistory = ({
    start,
    length,
    token,
    onSuccess,
    onError = (e) => console.error(e)
}: Params) => {
    return soapRequest({
        url: "https://localhost/api/history",
        method: "get",
        bodyParams: {
            namespace: "http://history.services.web3.org/",
            data: { start, length }
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

                const resultNodes = xmlDoc?.querySelectorAll("point");

                const data: Result[] = Array.from(resultNodes).map((node) => {
                    const getValue = (tag: string) => node.querySelector(tag)?.textContent;

                    const x = Number(getValue("x"));
                    const y = Number(getValue("y"));
                    const r = Number(getValue("r"));
                    const timestamp = Number(getValue("timestamp"));
                    const isHitted = getValue("hitted") === "true";

                    if ([x, y, r, timestamp].some(isNaN)) {
                        throw new Error("Некорректный формат числовых данных в ответе сервера");
                    }

                    return { x, y, r, timestamp, isHitted };
                });

                return onSuccess(data);
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
