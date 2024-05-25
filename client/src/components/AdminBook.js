import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, Form, ListGroup, Modal } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { createBook, fetchBooks } from '../http/bookAPI';
import RentalList from './RentalList';
import AdminListBook from './AdminListBook';
import { sendFileImage } from '../http/fileImageAPI';
import '../App.css'
import '../Media.css'

const AdminBook = observer(() => {
    
    const { book } = useContext(Context)
    const [showCreate, setShowCreate] = useState(false);
    const handleCloseCreate = () => setShowCreate(false);
    const handleShowCreate = () => setShowCreate(true);
    const [genreId, setGenreId] = useState('')
    const [authorId, setAuthorId] = useState('')
    const [publisherId, setPublisherId] = useState('')
    const [nameBook, setNameBook] = useState('')
    const [datePublication, setDatePublication] = useState('')
    const [countBook, setCountBook] = useState('')
    const [price, setPrice] = useState('')
    const [imageBook, setImageBook] = useState(null)
    const [showErrorCreate, setShowErrorCreate] = useState(false);
    const handleCloseErrorCreate = () => setShowErrorCreate(false);
    const handleShowErrorCreate = () => setShowErrorCreate(true);

    const createFun = async () => {
        try {
            if (!(nameBook === '' || imageBook === null || genreId === '' || authorId === '' || publisherId === '')) {
                
                await createBook(parseInt(genreId), parseInt(authorId), parseInt(publisherId), nameBook, datePublication, parseInt(countBook), parseFloat(price), nameBook+'.png');
                await sendFileImage(imageBook, nameBook + '.png')
                document.location.reload();
            }
            else {
                setGenreId('')
                setAuthorId('')
                setPublisherId('')
                setNameBook('')
                setDatePublication('')
                setCountBook('')
                setPrice('')
                setImageBook(null)
                handleCloseCreate()
                handleShowErrorCreate()
            }
        }
        catch (error) {
            setGenreId('')
            setAuthorId('')
            setPublisherId('')
            setNameBook('')
            setDatePublication('')
            setCountBook('')
            setPrice('')
            setImageBook(null)
            handleCloseCreate()
            handleShowErrorCreate()
            console.error('Create failed:', error.message);
        }
    }
    useEffect(() => {
        fetchBooks().then(data => book.setBooks(data))
    }, [])
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Таблица: Книги
                </h1>
            </Row>
            <Row className="admin-top">
                <Button
                    onClick={handleShowCreate}
                    className='admin-button-create'
                    variant={"outline-dark"}>
                    Создать
                </Button>
                <ListGroup>
                    <ListGroup.Item className="admin-scroll">
                        <div className="admin-item">
                            <div className="admin-item-book">
                                {'Номер'}
                            </div>
                            <div className="admin-item-book">
                                {'Название'}
                            </div>
                            <div className="admin-item-book">
                                {'Дата публикации'}
                            </div>
                            <div className="admin-item-book">
                                {'Кол-во'}
                            </div>
                            <div className="admin-item-book">
                                {'Цена'}
                            </div>
                            <div className="admin-item-book">
                                {'Изображение'}
                            </div>
                            <div className="admin-item-book">
                                {'Изменение'}
                            </div>
                            <div className="admin-item-book">
                                {'Удаление'}
                            </div>
                            <div className="admin-item-book">
                                {'Просмотр'}
                            </div>
                        </div>

                    </ListGroup.Item>
                    <AdminListBook />
                </ListGroup>
            </Row>
            <Modal show={showCreate} onHide={handleCloseCreate}>
                <Modal.Header closeButton>
                    <Modal.Title>Создание книги</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель создания</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Название
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите название*'
                        value={nameBook}
                        onChange={e => setNameBook(e.target.value)}
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
                    <Form.Label>
                        Номер жанра
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
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
                        required
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
                        required
                        placeholder='Введите номер издателя*'
                        type='number'
                        value={publisherId}
                        onChange={e => setPublisherId(e.target.value)}
                    />
                    <Button variant="secondary" onClick={handleCloseCreate}>
                        Закрыть
                    </Button>
                    <Button variant="primary" onClick={createFun}>
                        Создать
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showErrorCreate} onHide={handleCloseErrorCreate}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы неправильно ввели данные</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseErrorCreate}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
});

export default AdminBook;