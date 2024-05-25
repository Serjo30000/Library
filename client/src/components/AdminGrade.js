import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, ListGroup, Modal } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { fetchGrades } from '../http/gradeAPI';
import AdminListGrade from './AdminListGrade';
import '../App.css'
import '../Media.css'

const AdminGrade = observer(() => {
    const { book } = useContext(Context)

    useEffect(() => {
        fetchGrades().then(data => book.setGrades(data))
    }, [])
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Таблица: Оценки
                </h1>
            </Row>
            <Row className="admin-top">
                <ListGroup>
                    <ListGroup.Item className="admin-scroll">
                        <div className="admin-item">
                            <div className="admin-item-grade">
                                {'Номер'}
                            </div>
                            <div className="admin-item-grade">
                                {'Рейтинг'}
                            </div>
                            <div className="admin-item-grade">
                                {'Номер книги'}
                            </div>
                            <div className="admin-item-grade">
                                {'Логин'}
                            </div>
                            <div className="admin-item-grade">
                                {'Удалить'}
                            </div>
                            <div className="admin-item-grade">
                                {'Посмотреть'}
                            </div>
                        </div>

                    </ListGroup.Item>
                    <AdminListGrade />
                </ListGroup>
            </Row>
        </Container>
    );
});

export default AdminGrade;