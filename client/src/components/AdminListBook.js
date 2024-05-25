import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminBookItem from './AdminBookItem';
import '../App.css'
import '../Media.css'

const AdminListBook = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.books.map((book, i) =>
                <AdminBookItem key={book.id} bookEl={book} i={i}>

                </AdminBookItem>
            )}
        </>
    );
});

export default AdminListBook;