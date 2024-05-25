import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import { Row } from 'react-bootstrap';
import BookItem from "./BookItem";
import LocalBookItem from './LocalBookItem';
import '../App.css'
import '../Media.css'

const BasketList = observer(() => {
    const localBooks = JSON.parse(localStorage.getItem('BookRentals'))
    const { book } = useContext(Context)
    return (
        <>
            {/* {localBooks.map((localBook, i) =>
                <LocalBookItem key={localBook.id} localBook={localBook} book={book.books.find(b => b.id === localBook.id)} i={i} />
            )} */}
            {book.books.filter((function (b) {
                for (let j=0; j < localBooks.length;j++){
                    if (localBooks[j].id === b.id){
                        return b
                    }
                }
            })).map((book, i) =>
                <LocalBookItem key={book.id} book={book} i={i} />
            )}
        </>
    );
});

export default BasketList;