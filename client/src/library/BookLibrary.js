import { makeAutoObservable } from "mobx"

export default class BookLibrary {
    constructor() {
        this._publishers = [

        ]
        this._grades = [

        ]
        this._genres = [

        ]
        this._books = [
            
        ]
        this._authors = [

        ]
        this._rentals = [

        ]
        this._users = [

        ]
        this._selectedGenre = {}
        makeAutoObservable(this)
    }

    setIsPublishers(publishers) {
        this._publishers = publishers
    }

    setGrades(grades) {
        this._grades = grades
    }

    setGenres(genres) {
        this._genres = genres
    }

    setBooks(books) {
        this._books = books
    }

    setAuthors(authors) {
        this._authors = authors
    }

    setRentals(rentals) {
        this._rentals = rentals
    }

    setUsers(users) {
        this._users = users
    }

    setSelectedGenre(genre) {
        this._selectedGenre = genre
    }

    get publishers() {
        return this._publishers
    }

    get grades() {
        return this._grades
    }

    get genres() {
        return this._genres
    }

    get books() {
        return this._books
    }

    get authors() {
        return this._authors
    }

    get rentals() {
        return this._rentals
    }

    get users() {
        return this._users
    }

    get selectedGenre() {
        return this._selectedGenre
    }
}