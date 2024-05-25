import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminAuthorItem from './AdminAuthorItem';
import '../App.css'
import '../Media.css'

const AdminListAuthor = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.authors.map((author, i) =>
                <AdminAuthorItem key={author.id} author={author} i={i}>

                </AdminAuthorItem>
            )}
        </>
    );
});

export default AdminListAuthor;