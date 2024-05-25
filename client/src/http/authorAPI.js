import { $authHost, $host } from './index';

export const fetchAuthors = async () => {
    const { data } = await $host.get('api/authors')
    return data
}

export const fetchAuthor = async (id) => {
    const { data } = await $host.get(`api/authors/${id}`)
    return data
}

export const createAuthor = async (name, surname, patronymic, dateBirth) => {
    const { data } = await $authHost.put('api/authors/save', { name, surname, patronymic, dateBirth })
}

export const editAuthor = async (id, name, surname, patronymic, dateBirth) => {
    const { data } = await $authHost.put(`api/authors/update/${id}`, { name, surname, patronymic, dateBirth })
}

export const deleteAuthor = async (id) => {
    const { data } = await $authHost.delete(`api/authors/${id}`)
}