import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, ListGroup, Modal } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { fetchRentals } from '../http/rentalAPI';
import AdminListRental from './AdminListRental';
import '../App.css'
import '../Media.css'

const AdminRental = observer(() => {
    const { book } = useContext(Context)

    useEffect(() => {
        fetchRentals().then(data => book.setRentals(data))
    }, [])
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Таблица: Заказы
                </h1>
            </Row>
            <Row className="admin-top">
                <ListGroup>
                    <ListGroup.Item className="admin-scroll">
                        <div className="admin-item">
                            <div className="admin-item-rental">
                                {'Номер'}
                            </div>
                            <div className="admin-item-rental">
                                {'Кол-во'}
                            </div>
                            <div className="admin-item-rental">
                                {'Дата покупки'}
                            </div>
                            <div className="admin-item-rental">
                                {'Дата завершения покупки'}
                            </div>
                            <div className="admin-item-rental">
                                {'Номер книги'}
                            </div>
                            <div className="admin-item-rental">
                                {'Логин'}
                            </div>
                            <div className="admin-item-rental">
                                {'Удаление'}
                            </div>
                            <div className="admin-item-rental">
                                {'Просмотр'}
                            </div>
                        </div>

                    </ListGroup.Item>
                    <AdminListRental />
                </ListGroup>
            </Row>
        </Container>
    );
});

export default AdminRental;