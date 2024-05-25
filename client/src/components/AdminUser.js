import React, { useContext, useEffect } from 'react';
import { Container, ListGroup } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { fetchUserLibraries } from '../http/userAPI';
import AdminListUser from './AdminListUser';
import '../App.css'
import '../Media.css'

const AdminUser = observer(() => {
    const { book } = useContext(Context)

    useEffect(() => {
        fetchUserLibraries().then(data => book.setUsers(data))
    }, [])
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Таблица: Пользователи
                </h1>
            </Row>
            <Row className="admin-top">
                <ListGroup>
                    <ListGroup.Item className="admin-scroll">
                        <div className="admin-item">
                            <div className="admin-item-user">
                                {'Номер'}
                            </div>
                            <div className="admin-item-user">
                                {'Имя'}
                            </div>
                            <div className="admin-item-user">
                                {'Фамилия'}
                            </div>
                            <div className="admin-item-user">
                                {'Отчество'}
                            </div>
                            <div className="admin-item-user">
                                {'Телефон'}
                            </div>
                            <div className="admin-item-user">
                                {'Почта'}
                            </div>
                            <div className="admin-item-user">
                                {'Логин'}
                            </div>
                            <div className="admin-item-user">
                                {'Роль'}
                            </div>
                            <div className="admin-item-user">
                                {'Изменение'}
                            </div>
                            <div className="admin-item-user">
                                {'Удаление'}
                            </div>
                            <div className="admin-item-user">
                                {'Просмотр'}
                            </div>
                        </div>

                    </ListGroup.Item>
                    <AdminListUser />
                </ListGroup>
            </Row>
        </Container>
    );
});

export default AdminUser;