type params = {
    url: string,
    method: string,
    bodyParams: dataItem,
    headerParams?: dataItem[],

    onSuccess: (result: Element | null) => void,
    onError?: (status: number, result: Element | null) => void
}

type dataItem = {
    namespace: string,
    data: object
}

export const soapRequest = async ({
    url,
    method,
    bodyParams,
    headerParams = [],

    onSuccess = () => {},
    onError = () => {}
}: params) => {
    
    const [nsArray, dataArr] = parseParamsArray(headerParams, "headerNamespace")

    const body = `<?xml version="1.0" encoding="utf-8"?>
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            ${nsArray.map((elem) => elem)}
            xmlns:ws="${bodyParams.namespace}"
        >
            <soapenv:Header>
                ${dataArr.map((elem) => elem)}
            </soapenv:Header>
            <soapenv:Body>
                <ws:${method}>
                    ${Object.entries(bodyParams.data).map(([k,v])=>`<${k}>${v}</${k}>`).join('')}
                </ws:${method}>
            </soapenv:Body>
        </soapenv:Envelope>
    `;

    return fetch(
        url,
        {
            method: 'POST',
            headers: {'Content-Type':'text/xml'},
            body
        }
    ).then(
        async (result) => {
            if (result.ok) {
                return onSuccess(
                    parseSOAPResponse(
                        await result.text()
                    )
                )
            }
            return onError(
                result.status,
                parseSOAPResponse(
                    await result.text(),
                    "faultstring"
                )
            )
        }
    );
}

const parseSOAPResponse = (text: string, tag: string = "return") => {
    const parser = new DOMParser();
    const xml = parser.parseFromString(text, 'text/xml');
    return xml.querySelector(tag);
}


const parseParamsArray = (
    paramsArr: dataItem[],
    namespacePrefix: string
): string[][] => {
    const nsArray: string[] = [];
    const dataArray: string[] = [];

    let nsIndex = 0;
    paramsArr.forEach((elem) => {
        const nsName = namespacePrefix.concat(nsIndex.toString());
        nsArray.push(
            `xmlns:${nsName}="${elem.namespace}"`
        )
        dataArray.push(
            `${Object.entries(elem.data).map(([k,v])=>`<${nsName}:${k}>${v}</${nsName}:${k}>`).join('')}`
        )
    })

    return [
        nsArray,
        dataArray
    ]
}