import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListGroup from 'react-bootstrap/ListGroup';
import { Context } from '..';
import { deleteGrade } from '../http/gradeAPI';
import '../App.css'
import '../Media.css'

const AdminGradeItem = ({ grade, i }) => {
    const navigate = useNavigate()
    const { book } = useContext(Context)
    const [showEdit, setShowEdit] = useState(false);
    const [showDelete, setShowDelete] = useState(false);
    const handleCloseDelete = () => setShowDelete(false);
    const handleShowDelete = () => setShowDelete(true);
    const [showView, setShowView] = useState(false);
    const handleCloseView = () => setShowView(false);
    const handleShowView = () => setShowView(true);
    const [rating, setRating] = useState(grade.rating)
    const [nameBook, setNameBook] = useState(grade.book.nameBook)
    const [userName, setUserName] = useState(grade.userLibrary.login)
    const [comment, setComment] = useState(grade.comment)
    const deleteFun = async () => {
        try {
            await deleteGrade(grade.id);
            document.location.reload();
        }
        catch (error) {
            console.error('Delete failed:', error.message);
        }
    }
    return (
        <ListGroup.Item className="admin-scroll">
            <Form className="admin-item">
                <div className="admin-item-grade">
                    {grade.id}
                </div>
                <div className="admin-item-grade">
                    {grade.rating}
                </div>
                <div className="admin-item-grade">
                    {grade.book.id}
                </div>
                <div className="admin-item-grade">
                    {grade.userLibrary.login}
                </div>
                <Button
                    onClick={handleShowDelete}
                    className="admin-item-grade-button"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={handleShowView}
                    className="admin-item-grade-button"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={showDelete} onHide={handleCloseDelete}>
                <Modal.Header closeButton>
                    <Modal.Title>Удаление оценки</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить оценку?</Modal.Body>
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
                    <Modal.Title>Просмотр оценки</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель просмотра</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Оценка
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите оценку*'
                        value={rating}
                        onChange={e => setRating(e.target.value)}
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
                    <Form.Label>
                        Комментарий
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        as="textarea"
                        placeholder='Введите комментарий*'
                        value={comment}
                        onChange={e => setComment(e.target.value)}
                    />
                    <Button variant="secondary" onClick={handleCloseView}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </ListGroup.Item>

    );
};

export default AdminGradeItem;