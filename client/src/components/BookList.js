import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import { Row } from 'react-bootstrap';
import BookItem from "./BookItem";
import '../App.css'
import '../Media.css'

const BookList = observer(() => {
    const { book } = useContext(Context)
    return (
        <Row className='book-list-row'>
            {book.books.map(book => 
                <BookItem key={book.id} book={book} />
            )}
        </Row>
    );
});

export default BookList;