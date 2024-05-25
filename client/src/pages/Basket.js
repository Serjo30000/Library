import React, { useContext, useEffect, useState } from 'react';
import { observer } from "mobx-react-lite"
import ListGroup from 'react-bootstrap/ListGroup';
import { Button, Container, Form, Modal } from 'react-bootstrap';
import LocalBookItem from '../components/LocalBookItem';
import { fetchBook, fetchBooks } from '../http/bookAPI';
import { Context } from '..';
import BasketList from '../components/BasketList';
import { useNavigate } from 'react-router-dom';
import { PAYMENT_ROUTE } from '../utils/consts';
import '../App.css'
import '../Media.css'

const Basket = observer(() => {
    const { book } = useContext(Context)
    const navigate = useNavigate()
    const [showError, setShowError] = useState(false);
    const handleCloseError = () => setShowError(false);
    const handleShowError = () => setShowError(true);
    useEffect(() => {
        fetchBooks().then(data => book.setBooks(data))
    }, [])

    const navigateFun = async () => {
        try{
            const bookRentals = JSON.parse(localStorage.getItem('BookRentals'))
            if (bookRentals.length !== 0) {
                navigate(PAYMENT_ROUTE)
            }
        }
        catch(error){
            handleShowError()
            console.error('Navigate failed:', error.message);
        }
    }

    const dropBasket = async () => {
        try {
            localStorage.setItem('BookRentals', JSON.stringify([]))
            document.location.reload();
        }
        catch (error) {
            handleShowError()
            console.error('Drop failed:', error.message);
        }
    }

    const sumPrice = () => {
        try{
            const bookRentals = JSON.parse(localStorage.getItem('BookRentals'))
            let allSumPrice = 0
            console.log(bookRentals)
            if (bookRentals.length !== 0){
                for (let i = 0; i < bookRentals.length;i++){
                    const bk = book.books.find(b => b.id === bookRentals[i].id)
                    if (bk!=null && bk!==undefined){
                        allSumPrice += bk.price * bookRentals[i].countBook
                    }
                }
                return allSumPrice
            }
            else{
                return allSumPrice
            }
        }
        catch(error){
            handleShowError()
            console.error('Sum failed:', error.message);
        }
    }
    return (
        <Container>
            <ListGroup>
                <ListGroup.Item className="basket-scroll">
                    <div className="basket-item">
                        <div className="basket-item-text">
                            {'Номер'}
                        </div>
                        <div className="basket-item-text">
                            {'Название'}
                        </div>
                        <div className="basket-item-text">
                            {'Дата публикации'}
                        </div>
                        <div className="basket-item-text">
                            {'Кол-во'}
                        </div>
                        <div className="basket-item-text">
                            {'Цена'}
                        </div>
                        <div className="basket-item-text">
                            {'Жанр'}
                        </div>
                        <div className="basket-item-text">
                            {'Издатель'}
                        </div>
                        <div className="basket-item-text">
                            {'Автор'}
                        </div>
                        <div className="basket-item-text">
                            {'Выбранно'}
                        </div>
                        <div className="basket-item-text">
                            {'Изменить'}
                        </div>
                    </div>
                </ListGroup.Item>
                <BasketList/>
            </ListGroup>
            <Form className="basket-form">
                <div className='basket-form-sum-price'>
                    {'Общая сумма: ' + sumPrice() +' ₽'}
                </div>
                <Button
                    className='basket-form-button'
                    variant={"outline-dark"}
                    onClick={() => dropBasket()}>
                    Очистить заказ
                </Button>
                <Button
                    className='basket-form-button'
                    variant={"outline-dark"}
                    onClick={() => navigateFun()}>
                    Оформить заказ
                </Button>
            </Form>
            <Modal show={showError} onHide={handleCloseError}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Произошла ошибка при выполнении заказа</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseError}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
});

export default Basket;