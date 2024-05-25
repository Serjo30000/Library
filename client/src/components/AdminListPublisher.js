import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminPublisherItem from './AdminPublisherItem';
import '../App.css'
import '../Media.css'

const AdminListPublisher = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.publishers.map((publisher, i) =>
                <AdminPublisherItem key={publisher.id} publisher={publisher} i={i}>

                </AdminPublisherItem>
            )}
        </>
    );
});

export default AdminListPublisher;