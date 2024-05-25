import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListGroup from 'react-bootstrap/ListGroup';
import { Context } from '..';
import { deleteGenre, editGenre } from '../http/genreAPI';
import '../App.css'
import '../Media.css'

const AdminGenreItem = ({ genre, i }) => {
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
    const [nameGenre, setNameGenre] = useState(genre.nameGenre)
    const [showErrorEdit, setShowErrorEdit] = useState(false);
    const handleCloseErrorEdit = () => setShowErrorEdit(false);
    const handleShowErrorEdit = () => setShowErrorEdit(true);

    const editFun = async () => {
        try {
            if (!(nameGenre === '')) {
                await editGenre(genre.id, nameGenre);
                document.location.reload();
            }
            else {
                setNameGenre(genre.nameGenre)
                handleCloseEdit()
                handleShowErrorEdit()
            }
        }
        catch (error) {
            setNameGenre(genre.nameGenre)
            handleCloseEdit()
            handleShowErrorEdit()
            console.error('Edit failed:', error.message);
        }
    }
    const deleteFun = async () => {
        try {
            await deleteGenre(genre.id);
            document.location.reload();
        }
        catch (error) {
            console.error('Delete failed:', error.message);
        }
    }
    return (
        <ListGroup.Item className="admin-scroll">
            <Form className="admin-item">
                <div className="admin-item-genre">
                    {genre.id}
                </div>
                <div className="admin-item-genre">
                    {genre.nameGenre}
                </div>
                <Button
                    onClick={handleShowEdit}
                    className="admin-item-genre-button"
                    variant={"outline-dark"}>
                    Изменить
                </Button>
                <Button
                    onClick={handleShowDelete}
                    className="admin-item-genre-button"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={handleShowView}
                    className="admin-item-genre-button"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={showEdit} onHide={handleCloseEdit}>
                <Modal.Header closeButton>
                    <Modal.Title>Изменение жанра</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель изменения</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Название
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите название*'
                        value={nameGenre}
                        onChange={e => setNameGenre(e.target.value)}
                    />
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
                    <Modal.Title>Удаление жанра</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить жанр?</Modal.Body>
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
                    <Modal.Title>Просмотр жанра</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель просмотра</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Название
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите название*'
                        value={nameGenre}
                        onChange={e => setNameGenre(e.target.value)}
                    />
                    <Button variant="secondary" onClick={handleCloseView}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showErrorEdit} onHide={handleCloseErrorEdit}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы неправильно ввели данные</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseErrorEdit}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </ListGroup.Item>
    
    );
};

export default AdminGenreItem;