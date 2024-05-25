import React, { useContext, useEffect, useState } from 'react';
import {observer} from "mobx-react-lite"
import { Context } from "../index";
import ListGroup from 'react-bootstrap/ListGroup';
import { fetchBooks, fetchBooksByGenre } from '../http/bookAPI';
import '../App.css'
import '../Media.css'

const GenreBar = observer(() => {
    const {book} = useContext(Context)
    const [activeItem, setActiveItem] = useState(true);
    useEffect(() => {
        if (book.selectedGenre.id !== undefined){
            setActiveItem(false)
        }
    }, []);
    return (
        <ListGroup>
            <ListGroup.Item
                className='genre-bar-item'
                onClick={() => {
                    book.setSelectedGenre({})
                    fetchBooks().then(data => book.setBooks(data))
                    setActiveItem(true)
                }}
                active={activeItem === true}
            >
                Все
            </ListGroup.Item>
            {book.genres.map(genre =>
                <ListGroup.Item 
                    className='genre-bar-item'
                    active={genre.id === book.selectedGenre.id}
                    onClick={() => {
                        book.setSelectedGenre(genre)
                        fetchBooksByGenre(genre.id).then(data => book.setBooks(data))
                        setActiveItem(false)
                    }}
                    key={genre.id}
                >
                    {genre.nameGenre}
                </ListGroup.Item>
            )}
        </ListGroup>
    );
});

export default GenreBar;