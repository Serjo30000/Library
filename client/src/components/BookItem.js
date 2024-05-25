import React, { useContext, useEffect, useState } from 'react';
import { Card } from 'react-bootstrap';
import Col from 'react-bootstrap/Col';
import Image from 'react-bootstrap/Image';
import star from '../assets/static/star.png';
import { useNavigate } from 'react-router-dom';
import { BOOK_ROUTE } from '../utils/consts';
import { fetchGradesByBook } from '../http/gradeAPI';
import { Context } from '..';
import '../App.css'
import '../Media.css'

const BookItem = ({book}) => {
    const navigate = useNavigate()
    const [grades, setGrades] = useState([])
    useEffect(() => {
        fetchGradesByBook(book.id).then(data => {
            setGrades(data)
        })
    }, []);
    return (
        <Col md={3} className="book-item-col" onClick={() => navigate(BOOK_ROUTE + `/${book.id}`, { state: { bookId: book.id } })}>
            <Card className="book-item-col-card">
                <Image className="book-item-col-card-image" src={require(`/src/assets/${book.imageBook}`)}/>
                <div className="book-item-col-card-block">
                    <div className="book-item-col-card-block-fio">{book.author.surname + ' ' + book.author.name[0] + '. ' + book.author.patronymic[0] + '.'}</div>
                    <div className="book-item-col-card-block-name-book">{book.nameBook}</div>
                    <div className="book-item-col-card-block-grade">
                        <div className="book-item-col-card-block-grade-number">{((grades.map(grade => grade.rating).reduce(function (currentSum, currentNumber) {
                            return currentSum + currentNumber
                        }, 0) / grades.length) || 0).toFixed(1) }</div>
                        <Image className="book-item-col-card-block-grade-image" src={star}/>
                        <div className="book-item-col-card-block-grade-number">
                            {'(' + grades.length + ')'}
                        </div>
                    </div>
                </div>
            </Card>
        </Col>
    );
};

export default BookItem;