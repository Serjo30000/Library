import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListGroup from 'react-bootstrap/ListGroup';
import { Context } from '..';
import { deleteRental } from '../http/rentalAPI';
import '../App.css'
import '../Media.css'

const AdminRentalItem = ({ rental, i }) => {
    const navigate = useNavigate()
    const { book } = useContext(Context)
    const [showDelete, setShowDelete] = useState(false);
    const handleCloseDelete = () => setShowDelete(false);
    const handleShowDelete = () => setShowDelete(true);
    const [showView, setShowView] = useState(false);
    const handleCloseView = () => setShowView(false);
    const handleShowView = () => setShowView(true);
    const [countBook, setCountBook] = useState(rental.countBook)
    const [dateStart, setDateStart] = useState(rental.dateStart)
    const [dateEnd, setDateEnd] = useState(rental.dateEnd)
    const [nameBook, setNameBook] = useState(rental.book.nameBook)
    const [userName, setUserName] = useState(rental.userLibrary.login)
    const deleteFun = async () => {
        try {
            await deleteRental(rental.id);
            document.location.reload();
        }
        catch (error) {
            console.error('Delete failed:', error.message);
        }
    }
    return (
        <ListGroup.Item className="admin-scroll">
            <Form className="admin-item">
                <div className="admin-item-rental">
                    {rental.id}
                </div>
                <div className="admin-item-rental">
                    {rental.countBook}
                </div>
                <div className="admin-item-rental">
                    {rental.dateStart}
                </div>
                <div className="admin-item-rental">
                    {rental.dateEnd}
                </div>
                <div className="admin-item-rental">
                    {rental.book.id}
                </div>
                <div className="admin-item-rental">
                    {rental.userLibrary.login}
                </div>
                <Button
                    onClick={handleShowDelete}
                    className="admin-item-rental"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={handleShowView}
                    className="admin-item-rental"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={showDelete} onHide={handleCloseDelete}>
                <Modal.Header closeButton>
                    <Modal.Title>Удаление заказа</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить заказ?</Modal.Body>
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
                    <Modal.Title>Просмотр заказа</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель просмотра</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Кол-во
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите кол-во*'
                        value={countBook}
                        onChange={e => setCountBook(e.target.value)}
                    />
                    <Form.Label>
                        Дата покупки
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите дату покупки*'
                        value={dateStart}
                        onChange={e => setDateStart(e.target.value)}
                    />
                    <Form.Label>
                        Дата завершения покупки
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите дату завершения покупки*'
                        value={dateEnd}
                        onChange={e => setDateEnd(e.target.value)}
                    />
                    <Form.Label>
                        Название
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите название*'
                        value={nameBook}
                        onChange={e => setNameBook(e.target.value)}
                    />
                    <Form.Label>
                        Логин
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите логин*'
                        value={userName}
                        onChange={e => setUserName(e.target.value)}
                    />
                    <Button variant="secondary" onClick={handleCloseView}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </ListGroup.Item>

    );
};

export default AdminRentalItem;