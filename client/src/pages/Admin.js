import React, { useContext, useEffect } from 'react';
import { Button, Container, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { ADMIN_AUTHOR, ADMIN_BOOK, ADMIN_GENRE, ADMIN_GRADE, ADMIN_PUBLISHER, ADMIN_RENTAL, ADMIN_STATISTICS, ADMIN_USER } from '../utils/consts';
import '../App.css'
import '../Media.css'

const Admin = () => {
    const navigate = useNavigate()
    return (
        <Container className='admin-panel'>
            <Row className='admin-panel-row'>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_AUTHOR)}>Авторы</Button>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_BOOK)}>Книги</Button>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_GENRE)}>Жанры</Button>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_GRADE)}>Оценки</Button>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_PUBLISHER)}>Издатели</Button>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_RENTAL)}>Заказы</Button>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_USER)}>Пользователи</Button>
                <Button className='admin-panel-row-button' variant={"outline-dark"} onClick={() => navigate(ADMIN_STATISTICS)}>Статистика</Button>
            </Row>
            <Row className='admin-panel-row-down'>

            </Row>
        </Container>
    );
};

export default Admin;