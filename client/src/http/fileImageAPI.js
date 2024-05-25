import { $fileImageHost, $authHost, $host } from './index';

export const sendFileImage = async (image, nameFile) => {
    const formData = new FormData();
    formData.append('image', image);
    const { data } = await $fileImageHost.post(`api/images/sendFileImage?nameFile=${nameFile}`, formData)
    return data
}

export const dropFileImage = async (nameFile) => {
    const { data } = await $authHost.post(`api/images/dropFileImage?nameFile=${nameFile}`)
    return data
}

export const editFileImage = async (image, oldNameFile, newNameFile) => {
    const formData = new FormData();
    formData.append('image', image);
    const { data } = await $fileImageHost.post(`api/images/editFileImage?oldNameFile=${oldNameFile}&newNameFile=${newNameFile}`, formData)
    return data
}