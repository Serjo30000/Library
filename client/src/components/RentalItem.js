import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { BOOK_ROUTE, RENTAL_ROUTE } from '../utils/consts';
import ListGroup from 'react-bootstrap/ListGroup';
import { deleteRental } from '../http/rentalAPI';
import { Context } from '..';
import { sendDeleteBookEmail } from '../http/emailAPI';
import '../App.css'
import '../Media.css'

const RentalItem = ({ rental, i }) => {
    const navigate = useNavigate()
    const { book } = useContext(Context)
    const [email, setEmail] = useState('')
    const [numberCard, setNumberCard] = useState('')
    const [passwordCard, setPasswordCard] = useState('')
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [showError, setShowError] = useState(false);
    const handleCloseError = () => setShowError(false);
    const handleShowError = () => setShowError(true);

    const deleteRentalFun = async () => {
        try {
            if (!(email === '' || numberCard === '' || passwordCard === '')) {
                const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                if (regex.test(email)){
                    await sendDeleteBookEmail(email, (rental.book.price * rental.countBook))
                    await deleteRental(rental.id)
                    handleClose()
                    setEmail('')
                    setNumberCard('')
                    setPasswordCard('')
                    document.location.reload();
                }
                else{
                    handleClose()
                    setEmail('')
                    setNumberCard('')
                    setPasswordCard('')
                    handleShowError()
                }
            }
            else{
                handleClose()
                setEmail('')
                setNumberCard('')
                setPasswordCard('')
                handleShowError()
            }
        }
        catch (error) {
            console.error('Delete failed:', error.message);
            handleClose()
            setEmail('')
            setNumberCard('')
            setPasswordCard('')
            handleShowError()
        }
    }
    
    return (
        <ListGroup.Item className="account-scroll">
            <Form className="rental-item-form">
                <div className="rental-item-form-text-index" onClick={() => navigate(BOOK_ROUTE + `/${rental.id}`, { state: { bookId: rental.book.id } })}>
                    {i + 1}
                </div>
                <div className="rental-item-form-text">
                    {rental.book.nameBook}
                </div>
                <div className="rental-item-form-text">
                    {rental.book.datePublication}
                </div>
                <div className="rental-item-form-text">
                    {rental.book.countBook}
                </div>
                <div className="rental-item-form-text">
                    {rental.book.price}
                </div>
                <div className="rental-item-form-text">
                    {rental.book.genre.nameGenre}
                </div>
                <div className="rental-item-form-text">
                    {rental.book.publisher.namePublisher}
                </div>
                <div className="rental-item-form-text">
                    {rental.book.author.surname + ' ' + rental.book.author.name[0] + '. ' + rental.book.author.patronymic[0] + '. '}
                </div>
                <div className="rental-item-form-text">
                    {rental.countBook}
                </div>
                <div className="rental-item-form-text">
                    {rental.dateStart}
                </div>
                <div className="rental-item-form-text">
                    {rental.dateEnd}
                </div>
                <Button 
                    onClick={handleShow}
                    className="rental-item-form-button"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={() => navigate(RENTAL_ROUTE + `/${rental.id}`, { state: { rentalId: rental.id, bookId: rental.book.id } })}
                    className="rental-item-form-button"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Удаление купленной книги</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить книгу?</Modal.Body>
                <Modal.Footer>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите почту для реквизита*'
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите ваш номер карты*'
                        type='number'
                        value={numberCard}
                        onChange={e => setNumberCard(e.target.value)}
                    />
                    <Form.Control
                        className='mt-3'
                        placeholder='Введите ваш пароль карты*'
                        type='password'
                        value={passwordCard}
                        onChange={e => setPasswordCard(e.target.value)}
                    />
                    <Button variant="secondary" onClick={handleClose}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={deleteRentalFun}>
                        Удалить
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showError} onHide={handleCloseError}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы неправильно ввели данные</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseError}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </ListGroup.Item>

    );
};

export default RentalItem;