import { $authHost, $host } from './index';

export const fetchGradesByBook = async (book) => {
    const { data } = await $host.get(`api/grades/book/${book}`)
    return data
}

export const addGrade = async (bookId,login,rating,comment) => {
    const { data } = await $authHost.put(`api/grades/save?bookId=${bookId}&login=${login}`, { rating, comment })
}

export const fetchGrades = async () => {
    const { data } = await $host.get(`api/grades`)
    return data
}

export const deleteGrade = async (id) => {
    const { data } = await $authHost.delete(`api/grades/${id}`)
}

export const fetchGrade = async (id) => {
    const { data } = await $host.get(`api/grades/${id}`)
    return data
}