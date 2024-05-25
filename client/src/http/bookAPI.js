import { $authHost, $host } from './index';

export const fetchBooks = async () => {
    const { data } = await $host.get('api/books')
    return data
}

export const fetchBooksByGenre = async (genre) => {
    const { data } = await $host.get(`api/books/genre/${genre}`)
    return data
}

export const fetchBooksByAuthor = async (author) => {
    const { data } = await $host.get(`api/books/author/${author}`)
    return data
}

export const fetchBooksByPublisher = async (publisher) => {
    const { data } = await $host.get(`api/books/publisher/${publisher}`)
    return data
}

export const fetchBook = async (id) => {
    const { data } = await $host.get(`api/books/${id}`)
    return data
}

export const createBook = async (genreId, authorId, publisherId, nameBook, datePublication, countBook, price, imageBook) => {
    const { data } = await $authHost.put(`api/books/save?genreId=${genreId}&authorId=${authorId}&publisherId=${publisherId }`, { nameBook, datePublication, countBook, price, imageBook })
}

export const updateCountBook = async (id, countBook, login) => {
    const { data } = await $authHost.put(`api/books/updateCount/${id}?countBook=${countBook}&login=${login}`)
}

export const editBook = async (id, nameBook, datePublication, countBook, price, imageBook) => {
    const { data } = await $authHost.put(`api/books/update/${id}`, { nameBook, datePublication, countBook, price, imageBook })
}

export const deleteBook = async (id) => {
    const { data } = await $authHost.delete(`api/books/${id}`)
}