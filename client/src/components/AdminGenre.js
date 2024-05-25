import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, Form, ListGroup, Modal } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { createGenre, fetchGenres } from '../http/genreAPI';
import AdminListGenre from './AdminListGenre';
import '../App.css'
import '../Media.css'

const AdminGenre = observer(() => {
    const { book } = useContext(Context)
    const [showCreate, setShowCreate] = useState(false);
    const handleCloseCreate = () => setShowCreate(false);
    const handleShowCreate = () => setShowCreate(true);
    const [showErrorCreate, setShowErrorCreate] = useState(false);
    const handleCloseErrorCreate = () => setShowErrorCreate(false);
    const handleShowErrorCreate = () => setShowErrorCreate(true);

    useEffect(() => {
        fetchGenres().then(data => book.setGenres(data))
    }, [])
    const [nameGenre, setNameGenre] = useState('')
    const createFun = async () => {
        try {
            if (!(nameGenre === '')) {
                await createGenre(nameGenre);
                document.location.reload();
            }
            else {
                setNameGenre('')
                handleCloseCreate()
                handleShowErrorCreate()
            }
        }
        catch (error) {
            setNameGenre('')
            handleCloseCreate()
            handleShowErrorCreate()
            console.error('Create failed:', error.message);
        }
    }
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Таблица: Жанры
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
                            <div className="admin-item-genre">
                                {'Номер'}
                            </div>
                            <div className="admin-item-genre">
                                {'Название'}
                            </div>
                            <div className="admin-item-genre">
                                {'Изменить'}
                            </div>
                            <div className="admin-item-genre">
                                {'Удалить'}
                            </div>
                            <div className="admin-item-genre">
                                {'Посмотреть'}
                            </div>
                        </div>

                    </ListGroup.Item>
                    <AdminListGenre/>
                </ListGroup>
            </Row>
            <Modal show={showCreate} onHide={handleCloseCreate}>
                <Modal.Header closeButton>
                    <Modal.Title>Создание жанра</Modal.Title>
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
                        value={nameGenre}
                        onChange={e => setNameGenre(e.target.value)}
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

export default AdminGenre;