import React, { useContext, useState } from 'react';
import { Button, Container, Form, Modal } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import { NavLink, useNavigate } from 'react-router-dom';
import { ACCOUNT_ROUTE, BASKET_ROUTE, LOGIN_ROUTE } from '../utils/consts';
import { updateCountBook } from '../http/bookAPI';
import { sendBuyBookEmail } from '../http/emailAPI';
import { Context } from '..';
import '../App.css'
import '../Media.css'

const Payment = () => {
    const { book } = useContext(Context)
    const [email, setEmail] = useState('')
    const [numberCard, setNumberCard] = useState('')
    const [passwordCard, setPasswordCard] = useState('')
    const [showError, setShowError] = useState(false);
    const handleCloseError = () => setShowError(false);
    const handleShowError = () => setShowError(true);
    const navigate = useNavigate()

    const buyBook = async () => {
        try{
            if (!(email === '' || numberCard === '' || passwordCard === '')){
                const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                if (regex.test(email)) {
                    const bookRentals = JSON.parse(localStorage.getItem('BookRentals'))
                    for (let i = 0; i < bookRentals.length; i++) {
                        await updateCountBook(bookRentals[i].id, bookRentals[i].countBook, bookRentals[i].login)
                    }

                    let allSumPrice = 0
                    if (bookRentals.length !== 0) {
                        for (let i = 0; i < bookRentals.length; i++) {
                            const bk = book.books.find(b => b.id === bookRentals[i].id)
                            if (bk != null && bk !== undefined) {
                                allSumPrice += bk.price * bookRentals[i].countBook
                            }
                        }
                    }

                    await sendBuyBookEmail(email, allSumPrice)
                    localStorage.setItem('BookRentals', JSON.stringify([]))
                    navigate(ACCOUNT_ROUTE)
                }
            }
        }
        catch(error){
            handleShowError()
            console.error('Buy failed:', error.message);
        }
    }

    return (
        <Container className='payment-container'>
            <Card className="payment-container-card">
                <h2 className="payment-container-card-h">
                    Оформление заказа
                </h2>
                <Form className="payment-container-card-form">
                    <Form.Control
                        className='payment-container-card-form-control'
                        required
                        placeholder='Введите почту для реквизита*'
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                    <Form.Control
                        className='payment-container-card-form-control'
                        required
                        placeholder='Введите ваш номер карты*'
                        type='number'
                        value={numberCard}
                        onChange={e => setNumberCard(e.target.value)}
                    />
                    <Form.Control
                        className='payment-container-card-form-control'
                        placeholder='Введите ваш пароль карты*'
                        type='password'
                        value={passwordCard}
                        onChange={e => setPasswordCard(e.target.value)}
                    />
                    <Row className='payment-container-card-row'>
                        <div className='payment-container-card-row-link'>
                            <NavLink to={BASKET_ROUTE}>Вернуться в корзину</NavLink>
                        </div>
                        <Button
                            className='payment-container-card-row-button'
                            variant={"outline-dark"}
                            onClick={buyBook}>
                            Купить
                        </Button>
                    </Row>
                </Form>
            </Card>
            <Modal show={showError} onHide={handleCloseError}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы неправильно ввели данные для оплаты</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseError}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default Payment;