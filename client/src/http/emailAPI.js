import { $authHost, $host } from './index';

export const sendBuyBookEmail = async (to, price) => {
    const { data } = await $host.post(`api/email/sendBuyBook?to=${to}&price=${price}`)
}

export const sendDeleteBookEmail = async (to, price) => {
    const { data } = await $host.post(`api/email/sendDeleteBook?to=${to}&price=${price}`)
}