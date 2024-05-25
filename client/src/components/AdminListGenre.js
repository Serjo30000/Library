import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminGenreItem from './AdminGenreItem';
import '../App.css'
import '../Media.css'

const AdminListGenre = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.genres.map((genre, i) =>
                <AdminGenreItem key={genre.id} genre={genre} i={i}>

                </AdminGenreItem>
            )}
        </>
    );
});

export default AdminListGenre;