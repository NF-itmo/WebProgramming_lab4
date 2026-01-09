const getCookieData = (cookieName: string) => {
  return document.cookie
    .split('; ')
    .find(c => c.startsWith(`${cookieName}=`))
    ?.split('=')[1];
}

export const getCsrfToken = () => {
  const token = getCookieData("csrf_token");
  return token ? token : ''
}