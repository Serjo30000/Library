import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import { Row } from 'react-bootstrap';
import BookItem from "./BookItem";
import RentalItem from './RentalItem';
import '../App.css'
import '../Media.css'

const RentalList = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.rentals.map((rental, i) =>
                <RentalItem key={rental.id} rental={rental} i={i}>

                </RentalItem>
            )}
        </>
    );
});

export default RentalList;