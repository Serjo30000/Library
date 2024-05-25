import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminUserItem from './AdminUserItem';
import '../App.css'
import '../Media.css'

const AdminListUser = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.users.map((userLibrary, i) =>
                <AdminUserItem key={userLibrary.id} userLibrary={userLibrary} i={i}>

                </AdminUserItem>
            )}
        </>
    );
});

export default AdminListUser;