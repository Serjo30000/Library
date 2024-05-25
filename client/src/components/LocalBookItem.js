import React, { useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { BOOK_ROUTE } from '../utils/consts';
import ListGroup from 'react-bootstrap/ListGroup';
import { observer } from 'mobx-react-lite';
import '../App.css'
import '../Media.css'

const LocalBookItem = observer(({ book,i }) => {
    const navigate = useNavigate()
    const [localBook, setLocalBook] = useState(JSON.parse(localStorage.getItem('BookRentals')).find(r => r.id === book.id))
    const [recalculateCount, setRecalculateCount] = useState('')
    const [showError, setShowError] = useState(false);
    const handleCloseError = () => setShowError(false);
    const handleShowError = () => setShowError(true);
    const recalculate = async () => {
        try {
            let recalculateBookRentals
            const bookRentals = JSON.parse(localStorage.getItem('BookRentals'))

            if (book.countBook >= recalculateCount && parseInt(recalculateCount)>0){
                localBook.countBook = parseInt(recalculateCount)
                recalculateBookRentals = bookRentals.map(b => b.id !== localBook.id ? b : localBook);
                setRecalculateCount('')
                setLocalBook(localBook)
                localStorage.setItem('BookRentals', JSON.stringify(recalculateBookRentals))
                document.location.reload();
            }
            else if (book.countBook >= recalculateCount && parseInt(recalculateCount) === 0){
                recalculateBookRentals = bookRentals.filter(function (b) {
                    if (b.id !== localBook.id) {
                        return b
                    }
                });
                localStorage.setItem('BookRentals', JSON.stringify(recalculateBookRentals))
                document.location.reload();
            }
        }
        catch (error) {
            handleShowError()
            console.error('Recalculate failed:', error.message);
        }
    }

    return (
        <ListGroup.Item className="basket-scroll">
            <Form className="local-book-form">
                <div className="local-book-form-element-index" onClick={() => navigate(BOOK_ROUTE + `/${localBook.id}`, { state: { bookId: book.id } })}>
                    {i + 1}
                </div>
                <div className="local-book-form-element">
                    {book.nameBook}
                </div>
                <div className="local-book-form-element">
                    {book.datePublication}
                </div>
                <div className="local-book-form-element">
                    {localBook.countBook}
                </div>
                <div className="local-book-form-element">
                    {book.price}
                </div>
                <div className="local-book-form-element">
                    {book.genre.nameGenre}
                </div>
                <div className="local-book-form-element">
                    {book.publisher.namePublisher}
                </div>
                <div className="local-book-form-element">
                    {book.author.surname + ' ' + book.author.name[0] + '. ' + book.author.patronymic[0] + '.'}
                </div>
                <Form.Control
                    required
                    className="local-book-form-element-control"
                    placeholder='Количество'
                    value={recalculateCount}
                    onChange={e => setRecalculateCount(e.target.value)}
                />
                <Button
                    className="local-book-form-element-control"
                    variant={"outline-success"}
                    onClick={() => recalculate()}>
                    Пересчитать
                </Button>
            </Form>
            <Modal show={showError} onHide={handleCloseError}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Произошла ошибка при пересчёте</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseError}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </ListGroup.Item>
        
    );
});

export default LocalBookItem;