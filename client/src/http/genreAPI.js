import { $authHost, $host } from './index';

export const fetchGenres = async () => {
    const { data } = await $host.get('api/genres')
    return data
}

export const fetchGenre = async (id) => {
    const { data } = await $host.get(`api/genres/${id}`)
    return data
}

export const createGenre = async (nameGenre) => {
    const { data } = await $authHost.put('api/genres/save', { nameGenre })
}

export const editGenre = async (id, nameGenre) => {
    const { data } = await $authHost.put(`api/genres/update/${id}`, { nameGenre })
}

export const deleteGenre = async (id) => {
    const { data } = await $authHost.delete(`api/genres/${id}`)
}