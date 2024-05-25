import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, Form, ListGroup, Modal } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { createAuthor, fetchAuthors } from '../http/authorAPI';
import AdminListAuthor from './AdminListAuthor';
import '../App.css'
import '../Media.css'

const AdminAuthor = observer(() => {
    const { book } = useContext(Context)
    const [showCreate, setShowCreate] = useState(false);
    const handleCloseCreate = () => setShowCreate(false);
    const handleShowCreate = () => setShowCreate(true);
    const [showErrorCreate, setShowErrorCreate] = useState(false);
    const handleCloseErrorCreate = () => setShowErrorCreate(false);
    const handleShowErrorCreate = () => setShowErrorCreate(true);
    useEffect(() => {
        fetchAuthors().then(data => book.setAuthors(data))
    }, [])
    const [name, setName] = useState('')
    const [surname, setSurname] = useState('')
    const [patronymic, setPatronymic] = useState('')
    const [dateBirth, setDateBirth] = useState('')
    const createFun = async () => {
        try {
            if (!(name === '' || surname === '')){
                await createAuthor(name, surname, patronymic, dateBirth);
                document.location.reload();
            }
            else{
                setName('')
                setSurname('')
                setPatronymic('')
                setDateBirth('')
                handleCloseCreate()
                handleShowErrorCreate()
            }
        }
        catch (error) {
            setName('')
            setSurname('')
            setPatronymic('')
            setDateBirth('')
            handleCloseCreate()
            handleShowErrorCreate()
            console.error('Create failed:', error.message);
        }
    }
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Таблица: Авторы
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
                            <div className="admin-item-author">
                                {'Номер'}
                            </div>
                            <div className="admin-item-author">
                                {'Имя'}
                            </div>
                            <div className="admin-item-author">
                                {'Фамилия'}
                            </div>
                            <div className="admin-item-author">
                                {'Отчество'}
                            </div>
                            <div className="admin-item-author">
                                {'Дата рождения'}
                            </div>
                            <div className="admin-item-author">
                                {'Изменить'}
                            </div>
                            <div className="admin-item-author">
                                {'Удалить'}
                            </div>
                            <div className="admin-item-author">
                                {'Посмотреть'}
                            </div>
                        </div>

                    </ListGroup.Item>
                    <AdminListAuthor />
                </ListGroup>
            </Row>
            <Modal show={showCreate} onHide={handleCloseCreate}>
                <Modal.Header closeButton>
                    <Modal.Title>Создание автора</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель создания</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Имя
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите имя*'
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                    <Form.Label>
                        Фамилия
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите фамилию*'
                        value={surname}
                        onChange={e => setSurname(e.target.value)}
                    />
                    <Form.Label>
                        Отчество
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите отчество'
                        value={patronymic}
                        onChange={e => setPatronymic(e.target.value)}
                    />
                    <Form.Label>
                        Дата рождения
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите дату рождения*'
                        type='date'
                        value={dateBirth}
                        onChange={e => setDateBirth(e.target.value)}
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

export default AdminAuthor;