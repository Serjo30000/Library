import React, { useContext, useEffect, useState } from 'react';
import { Button, Col, Container, Form, Image, ListGroup, Modal, Row } from 'react-bootstrap';
import star from '../assets/static/star.png'
import { BASKET_ROUTE, LIBRARY_ROUTE, PAYMENT_ROUTE } from '../utils/consts';
import { useLocation, useNavigate } from 'react-router-dom';
import { fetchBook } from '../http/bookAPI';
import { observer } from 'mobx-react-lite';
import { fetchGradesByBook, addGrade } from '../http/gradeAPI';
import { Context } from '..';
import '../App.css'
import '../Media.css'

const Book = observer(() => {
    const navigate = useNavigate()
    const { user } = useContext(Context)
    const [countBook, setCountBook] = useState('')
    const [commentBook, setCommentBook] = useState('')
    const [selectedRating, setSelectedRating] = useState('5');
    const [showError, setShowError] = useState(false);
    const handleCloseError = () => setShowError(false);
    const handleShowError = () => setShowError(true);

    const handleSelectRatingChange = (event) => {
        setSelectedRating(event.target.value);
    };
    const { state } = useLocation();
    const { bookId } = state;
    const [book, setBook] = useState({
        data: {
            "id": 0,
            "nameBook": "string",
            "datePublication": new Date(),
            "countBook": 0,
            "price": 0,
            "imageBook": "default.png",
            "genre": {
                "id": 0,
                "nameGenre": "string"
            },
            "author": {
                "id": 0,
                "name": "string",
                "surname": "string",
                "patronymic": "string",
                "dateBirth": new Date()
            },
            "publisher": {
                "id": 0,
                "namePublisher": "string",
                "address": "string"
            }
    }});
    const [grades, setGrades] = useState([])
    useEffect(() => {
        fetchBook(bookId).then(data => {
            setBook({ data });
        });
        fetchGradesByBook(bookId).then(data => {
            setGrades(data)
        })
    }, []);
    const paymentFun = async () => {
        try {
            if (parseInt(countBook) > 0 && book.data.countBook>0){
                let fl = false
                const bookRentals = JSON.parse(localStorage.getItem('BookRentals'))
                if (bookRentals.length > 0) {
                    bookRentals.forEach((element, i) => {
                        if (element.id === bookId && element.login === user.user.login && book.data.countBook >= (bookRentals[i].countBook + parseInt(countBook))) {
                            fl = true
                            bookRentals[i].countBook = bookRentals[i].countBook + parseInt(countBook)
                        }
                        else if (element.id === bookId && element.login === user.user.login && book.data.countBook < (bookRentals[i].countBook + parseInt(countBook))) {
                            fl = true
                            bookRentals[i].countBook = book.data.countBook
                        }
                    });
                }
                if (!fl && book.data.countBook >= parseInt(countBook)) {
                    bookRentals.push({ 'id': bookId, 'countBook': parseInt(countBook), 'login': user.user.login })
                }
                else if (!fl && book.data.countBook < parseInt(countBook)){
                    bookRentals.push({ 'id': bookId, 'countBook': book.data.countBook, 'login': user.user.login })
                }
                localStorage.setItem('BookRentals', JSON.stringify(bookRentals))
                navigate(BASKET_ROUTE)
            }
            else{
                setCountBook('')
                handleShowError()
            }
        }
        catch (error) {
            handleShowError()
            console.error('Payment failed:', error.message);
        }
    }

    const sendCommentBook = async () => {
        try{
            if (commentBook!==''){
                await addGrade(bookId, user.user.login, selectedRating, commentBook)
                setCommentBook('')
                setSelectedRating('5')
                document.location.reload();
            }
            else{
                setCommentBook('')
                setSelectedRating('5')
                handleShowError()
            }
        }
        catch(error){
            setCommentBook('')
            setSelectedRating('5')
            handleShowError()
            console.error('Payment failed:', error.message);
        }
    }

    console.log(JSON.parse(localStorage.getItem('BookRentals')))
    return (
        <Container className="book-container">
            <Row className="book-container-row">
                <Col md={5}>
                    <Image className="book-container-row-image" src={require(`/src/assets/${book.data.imageBook}`)} />
                </Col>
                <Col md={3} className="book-container-row-option">
                    <Row>
                        <h2 className="book-container-row-option-h">{book.data.nameBook}</h2>
                        <div className="book-container-row-option-grade">
                            <div className="book-container-row-option-grade-number">{'Рейтинг: ' + ((grades.map(grade => grade.rating).reduce(function (currentSum, currentNumber) {
                                return currentSum + currentNumber
                            }, 0) / grades.length) || 0).toFixed(1) }</div>
                            <Image className="book-container-row-option-grade-image" src={star} />
                            <div className="book-container-row-option-grade-number">
                                {'(' + grades.length+')'}
                            </div>
                        </div>
                        <div className="book-container-row-option-text">
                            {'Цена: ' + book.data.price}
                        </div>
                        <div className="book-container-row-option-text">
                            {'Количество: ' + book.data.countBook}
                        </div>
                        <div className="book-container-row-option-text">
                            {'Жанр: ' + book.data.genre.nameGenre}
                        </div>
                        <div className="book-container-row-option-text">
                            {'Издатель: ' + book.data.publisher.namePublisher}
                        </div>
                        <div className="book-container-row-option-text">
                            {'Дата публикации: ' + book.data.datePublication}
                        </div>
                    </Row>
                </Col>
                <Col md={4} className="book-container-row-active">
                    <Form className="book-container-row-active-form">
                        <Form.Control
                            required
                            className='book-container-row-active-form-control'
                            placeholder='Введите количество'
                            type='number'
                            min={0}
                            max={book.data.countBook}
                            value={countBook}
                            onChange={e => setCountBook(e.target.value)}
                        />
                        <Button className='book-container-row-active-form-button' variant={"outline-dark"} onClick={paymentFun}>
                            Добавить в корзину
                        </Button>
                    </Form>
                </Col>
            </Row>
            <div className="book-container-block">
                <Button
                    variant={"outline-dark"}
                    onClick={() => navigate(LIBRARY_ROUTE)}
                    className="book-container-block-button">
                    Вернуться
                </Button>
            </div>
            <Row className="book-container-row">
                <Col md={12} className="book-container-row-col">
                    <Form className="book-container-row-col-form">
                        <select className="book-container-row-col-form-select" value={selectedRating} onChange={handleSelectRatingChange}>
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                        <Form.Control
                            required
                            className="book-container-row-col-form-control"
                            placeholder='Напишите комментарий'
                            as="textarea"
                            value={commentBook}
                            onChange={e => setCommentBook(e.target.value)}
                        />
                        <Button variant={"outline-dark"} className="book-container-row-col-form-button" onClick={sendCommentBook}>
                            Отправить комментарий
                        </Button>
                    </Form>
                </Col>
            </Row>
            <Row className="book-container-row-comment">
                <ListGroup className="book-container-row-comment-list-group">
                    <ListGroup.Item>
                        <h3 className="book-container-row-comment-list-group-h">
                            Комментарии:
                        </h3>
                        
                    </ListGroup.Item>
                    {grades.map(grade =>
                        <ListGroup.Item
                            key={grade.id}
                        >
                            <div className="book-container-row-comment-list-group-head">
                                <div className="book-container-row-comment-list-group-head-text">
                                    {'Логин: ' + grade.userLibrary.login}
                                </div>
                                <div className="book-container-row-comment-list-group-head-text">
                                    {'Оценка: ' + grade.rating}
                                </div>
                            </div>
                            <div className="book-container-row-comment-list-group-text">
                                {grade.comment}
                            </div>
                        </ListGroup.Item>
                    )}
                </ListGroup>
            </Row>
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
        </Container>
    );
});

export default Book;