import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminRentalItem from './AdminRentalItem';
import '../App.css'
import '../Media.css'

const AdminListRental = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.rentals.map((rental, i) =>
                <AdminRentalItem key={rental.id} rental={rental} i={i}>

                </AdminRentalItem>
            )}
        </>
    );
});

export default AdminListRental;