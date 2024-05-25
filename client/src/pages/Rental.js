import React, { useEffect, useState } from 'react';
import { Button, Card, Col, Container, Form, Image, Row } from 'react-bootstrap';
import star from '../assets/static/star.png'
import { ACCOUNT_ROUTE } from '../utils/consts';
import { useLocation, useNavigate } from 'react-router-dom';
import { fetchRental, fetchRentals } from '../http/rentalAPI';
import { fetchGradesByBook, fetchGradesByRental } from '../http/gradeAPI';
import '../App.css'
import '../Media.css'

const Rental = () => {
    const navigate = useNavigate()
    const { state } = useLocation();
    const { rentalId, bookId } = state;
    const [rental, setRental] = useState({
        data: {
            "id": 0,
            "countBook": 0,
            "dateStart": "2023-12-16T00:40:17.154Z",
            "dateEnd": "2023-12-16T00:40:17.154Z",
            "book": {
                "id": 0,
                "nameBook": "string",
                "datePublication": "2023-12-16T00:40:17.154Z",
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
                    "dateBirth": "2023-12-16T00:40:17.154Z"
                },
                "publisher": {
                    "id": 0,
                    "namePublisher": "string",
                    "address": "string"
                }
            },
            "userLibrary": {
                "id": 0,
                "name": "string",
                "surname": "string",
                "patronymic": "string",
                "phone": "string",
                "email": "string",
                "login": "string",
                "password": "string",
                "role": {
                    "id": 0,
                    "nameRole": "string"
                }
            }
        }
    });
    const [grades, setGrades] = useState([])
    useEffect(() => {
        fetchRental(rentalId).then(data => {
            setRental({ data });
        });
        fetchGradesByBook(bookId).then(data => {
            setGrades(data)
        })
    }, []);
    return (
        <Container className="rental-container">
            <Row className="rental-container-row">
                <Col md={6}>
                    <Image className="rental-container-row-image" src={require(`/src/assets/${rental.data.book.imageBook}`)}/>
                </Col>
                <Col md={6} className="rental-container-row-option">
                    <Row>
                        <h2 className="rental-container-row-option-h">{rental.data.book.nameBook}</h2>
                        <div className="rental-container-row-option-grade">
                            <div className="rental-container-row-option-grade-number">{'Рейтинг: ' + ((grades.map(grade => grade.rating).reduce(function (currentSum, currentNumber) {
                                return currentSum + currentNumber
                            }, 0) / grades.length) || 0).toFixed(1) }</div>
                            <Image className="rental-container-row-option-grade-image" src={star} />
                            <div className="rental-container-row-option-grade-number">
                                {'(' + grades.length + ')'}
                            </div>
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Цена: ' + rental.data.book.price}
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Общее кол-во: ' + rental.data.book.countBook}
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Ваше кол-во: ' + rental.data.countBook}
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Жанр: ' + rental.data.book.genre.nameGenre}
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Издатель: ' + rental.data.book.publisher.namePublisher}
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Дата публикации: ' + rental.data.book.datePublication}
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Дата начала: ' + rental.data.dateStart}
                        </div>
                        <div className="rental-container-row-option-text">
                            {'Дата конца: ' + rental.data.dateEnd}
                        </div>
                    </Row>
                </Col>
            </Row>
            <Button
                variant={"outline-dark"}
                onClick={() => navigate(ACCOUNT_ROUTE)}
                className="rental-container-button">
                Вернуться
            </Button>
        </Container>
    );
};

export default Rental;