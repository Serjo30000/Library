import { $authHost, $host } from './index';

export const fetchPublishers = async () => {
    const { data } = await $host.get('api/publishers')
    return data
}

export const fetchPublisher = async (id) => {
    const { data } = await $host.get(`api/publishers/${id}`)
    return data
}

export const createPublisher = async (namePublisher, address) => {
    const { data } = await $authHost.put('api/publishers/save', { namePublisher, address })
}

export const editPublisher = async (id, namePublisher, address) => {
    const { data } = await $authHost.put(`api/publishers/update/${id}`, { namePublisher, address })
}

export const deletePublisher = async (id) => {
    const { data } = await $authHost.delete(`api/publishers/${id}`)
}