import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal, Image, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListGroup from 'react-bootstrap/ListGroup';
import { Context } from '..';
import { deleteBook, editBook } from '../http/bookAPI';
import { dropFileImage, editFileImage } from '../http/fileImageAPI';
import '../App.css'
import '../Media.css'

const AdminBookItem = ({ bookEl, i }) => {
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
    const [genreId, setGenreId] = useState(bookEl.genre.id)
    const [authorId, setAuthorId] = useState(bookEl.author.id)
    const [publisherId, setPublisherId] = useState(bookEl.publisher.id)
    const [nameBook, setNameBook] = useState(bookEl.nameBook)
    const [datePublication, setDatePublication] = useState(bookEl.datePublication)
    const [countBook, setCountBook] = useState(bookEl.countBook)
    const [price, setPrice] = useState(bookEl.price)
    const [imageBook, setImageBook] = useState(null)
    const [nameImageBook, setNameImageBook] = useState(bookEl.nameBook)
    const [showErrorEdit, setShowErrorEdit] = useState(false);
    const handleCloseErrorEdit = () => setShowErrorEdit(false);
    const handleShowErrorEdit = () => setShowErrorEdit(true);

    const editFun = async () => {
        try {
            if (!(nameBook === '' || imageBook === '')) {
                await editBook(bookEl.id, nameImageBook, datePublication, parseInt(countBook), parseFloat(price), nameBook + '.png');
                await editFileImage(imageBook, bookEl.imageBook, nameImageBook + '.png')
                document.location.reload();
            }
            else {
                setNameBook(bookEl.nameBook)
                setDatePublication(bookEl.datePublication)
                setCountBook(bookEl.countBook)
                setPrice(bookEl.price)
                setImageBook(null)
                setNameImageBook(bookEl.nameBook)
                handleCloseEdit()
                handleShowErrorEdit()
            }
        }
        catch (error) {
            setNameBook(bookEl.nameBook)
            setDatePublication(bookEl.datePublication)
            setCountBook(bookEl.countBook)
            setPrice(bookEl.price)
            setImageBook(null)
            setNameImageBook(bookEl.nameBook)
            handleCloseEdit()
            handleShowErrorEdit()
            console.error('Edit failed:', error.message);
        }
    }
    const deleteFun = async () => {
        try {
            await deleteBook(bookEl.id);
            await dropFileImage(bookEl.nameBook+'.png')
            document.location.reload();
        }
        catch (error) {
            console.error('Delete failed:', error.message);
        }
    }
    return (
        <ListGroup.Item className="admin-scroll">
            <Form className="admin-item">
                <div className="admin-item-book">
                    {bookEl.id}
                </div>
                <div className="admin-item-book">
                    {bookEl.nameBook}
                </div>
                <div className="admin-item-book">
                    {bookEl.datePublication}
                </div>
                <div className="admin-item-book">
                    {bookEl.countBook}
                </div>
                <div className="admin-item-book">
                    {bookEl.price}
                </div>
                <div className="admin-item-book">
                    {bookEl.imageBook}
                </div>
                <Button
                    onClick={handleShowEdit}
                    className="admin-item-book-button"
                    variant={"outline-dark"}>
                    Изменить
                </Button>
                <Button
                    onClick={handleShowDelete}
                    className="admin-item-book-button"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={handleShowView}
                    className="admin-item-book-button"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={showEdit} onHide={handleCloseEdit}>
                <Modal.Header closeButton>
                    <Modal.Title>Изменение книги</Modal.Title>
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
                        value={nameImageBook}
                        onChange={e => setNameImageBook(e.target.value)}
                    />
                    <Form.Label>
                        Дата публикации
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите дату публикации*'
                        type='date'
                        value={datePublication}
                        onChange={e => setDatePublication(e.target.value)}
                    />
                    <Form.Label>
                        Кол-во
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите кол-во*'
                        type='number'
                        value={countBook}
                        onChange={e => setCountBook(e.target.value)}
                    />
                    <Form.Label>
                        Цена
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите цену*'
                        type='number'
                        value={price}
                        onChange={e => setPrice(e.target.value)}
                    />
                    <Form.Label>
                        Картинка
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Вставте картинку*'
                        type='file'
                        onChange={e => setImageBook(e.target.files[0])}
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
                    <Modal.Title>Удаление книги</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить книгу?</Modal.Body>
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
                    <Modal.Title>Просмотр книги</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель просмотра</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Картинка
                    </Form.Label>
                    <Form.Label className="d-flex align-items-center justify-content-center">
                        <Image width={'80%'} height={500} src={require(`/src/assets/${nameBook+'.png'}`)} />
                    </Form.Label>
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
                        Дата публикации
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите дату публикации*'
                        type='date'
                        value={datePublication}
                        onChange={e => setDatePublication(e.target.value)}
                    />
                    <Form.Label>
                        Кол-во
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите кол-во*'
                        type='number'
                        value={countBook}
                        onChange={e => setCountBook(e.target.value)}
                    />
                    <Form.Label>
                        Цена
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите цену*'
                        type='number'
                        value={price}
                        onChange={e => setPrice(e.target.value)}
                    />
                    <Form.Label>
                        Номер жанра
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите номер жанра*'
                        type='number'
                        value={genreId}
                        onChange={e => setGenreId(e.target.value)}
                    />
                    <Form.Label>
                        Номер автора
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите номер автора*'
                        type='number'
                        value={authorId}
                        onChange={e => setAuthorId(e.target.value)}
                    />
                    <Form.Label>
                        Номер издателя
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите номер издателя*'
                        type='number'
                        value={publisherId}
                        onChange={e => setPublisherId(e.target.value)}
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

export default AdminBookItem;