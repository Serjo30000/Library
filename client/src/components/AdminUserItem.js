import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListGroup from 'react-bootstrap/ListGroup';
import { Context } from '..';
import { deleteUserLibrary, editUserLibrary } from '../http/userAPI';
import '../App.css'
import '../Media.css'

const AdminUserItem = ({ userLibrary, i }) => {
    const navigate = useNavigate()
    const { book } = useContext(Context)
    const [showEdit, setShowEdit] = useState(false);
    const handleCloseEdit = () => setShowEdit(false);
    const handleShowEdit = () => setShowEdit(true);
    const [showDelete, setShowDelete] = useState(false);
    const handleCloseDelete = () => setShowDelete(false);
    const handleShowDelete = () => setShowDelete(true);
    const [showView, setShowView] = useState(false);
    const handleCloseView = () => setShowView(false);
    const handleShowView = () => setShowView(true);
    const [name, setName] = useState(userLibrary.name)
    const [surname, setSurname] = useState(userLibrary.surname)
    const [patronymic, setPatronymic] = useState(userLibrary.patronymic)
    const [phone, setPhone] = useState(userLibrary.phone)
    const [email, setEmail] = useState(userLibrary.email)
    const [login, setLogin] = useState(userLibrary.login)
    const [role, setRole] = useState(userLibrary.role)
    const editFun = async () => {
        try {
            await editUserLibrary(userLibrary.login);
            document.location.reload();
        }
        catch (error) {
            console.error('Edit failed:', error.message);
        }
    }
    const deleteFun = async () => {
        try {
            await deleteUserLibrary(userLibrary.login);
            document.location.reload();
        }
        catch (error) {
            console.error('Delete failed:', error.message);
        }
    }
    return (
        <ListGroup.Item className="admin-scroll">
            <Form className="admin-item">
                <div className="admin-item-user">
                    {i + 1}
                </div>
                <div className="admin-item-user">
                    {userLibrary.name}
                </div>
                <div className="admin-item-user">
                    {userLibrary.surname}
                </div>
                <div className="admin-item-user">
                    {userLibrary.patronymic}
                </div>
                <div className="admin-item-user">
                    {userLibrary.phone}
                </div>
                <div className="admin-item-user">
                    {userLibrary.email}
                </div>
                <div className="admin-item-user">
                    {userLibrary.login}
                </div>
                <div className="admin-item-user">
                    {userLibrary.role}
                </div>
                <Button
                    onClick={handleShowEdit}
                    className="admin-item-user-button"
                    variant={"outline-dark"}>
                    Изменить
                </Button>
                <Button
                    onClick={handleShowDelete}
                    className="admin-item-user"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={handleShowView}
                    className="admin-item-user"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={showEdit} onHide={handleCloseEdit}>
                <Modal.Header closeButton>
                    <Modal.Title>Изменение роли у пользователя</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите сделать пользователя админом?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseEdit}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={editFun}>
                        Изменить
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showDelete} onHide={handleCloseDelete}>
                <Modal.Header closeButton>
                    <Modal.Title>Удаление пользователя</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить пользователя</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseDelete}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={deleteFun}>
                        Удалить
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showView} onHide={handleCloseView}>
                <Modal.Header closeButton>
                    <Modal.Title>Просмотр пользователя</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель просмотра</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Имя
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите имя*'
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                    <Form.Label>
                        Фамилия
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите фамилию*'
                        value={surname}
                        onChange={e => setSurname(e.target.value)}
                    />
                    <Form.Label>
                        Отчество
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите отчество'
                        value={patronymic}
                        onChange={e => setPatronymic(e.target.value)}
                    />
                    <Form.Label>
                        Телефон
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите телефон*'
                        value={phone}
                        onChange={e => setPhone(e.target.value)}
                    />
                    <Form.Label>
                        Почту
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите почту*'
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                    <Form.Label>
                        Логин
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите логин*'
                        value={login}
                        onChange={e => setLogin(e.target.value)}
                    />
                    <Form.Label>
                        Роль
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите роль*'
                        value={role}
                        onChange={e => setRole(e.target.value)}
                    />
                    <Button variant="secondary" onClick={handleCloseView}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </ListGroup.Item>

    );
};

export default AdminUserItem;