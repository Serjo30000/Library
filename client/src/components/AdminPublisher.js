import React, { useContext, useEffect, useState } from 'react';
import { Button, Container, Form, ListGroup, Modal } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { createPublisher, fetchPublishers } from '../http/publisherAPI';
import AdminListPublisher from './AdminListPublisher';
import '../App.css'
import '../Media.css'

const AdminPublisher = observer(() => {
    const { book } = useContext(Context)
    const [showCreate, setShowCreate] = useState(false);
    const handleCloseCreate = () => setShowCreate(false);
    const handleShowCreate = () => setShowCreate(true);
    const [showErrorCreate, setShowErrorCreate] = useState(false);
    const handleCloseErrorCreate = () => setShowErrorCreate(false);
    const handleShowErrorCreate = () => setShowErrorCreate(true);

    useEffect(() => {
        fetchPublishers().then(data => book.setIsPublishers(data))
    }, [])
    const [namePublisher, setNamePublisher] = useState('')
    const [address, setAddress] = useState('')
    const createFun = async () => {
        try {
            if (!(namePublisher === '' || address === '')) {
                await createPublisher(namePublisher, address);
                document.location.reload();
            }
            else {
                setNamePublisher('')
                setAddress('')
                handleCloseCreate()
                handleShowErrorCreate()
            }
        }
        catch (error) {
            setNamePublisher('')
            setAddress('')
            handleCloseCreate()
            handleShowErrorCreate()
            console.error('Create failed:', error.message);
        }
    }
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Таблица: Издатели
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
                            <div className="admin-item-publisher">
                                {'Номер'}
                            </div>
                            <div className="admin-item-publisher">
                                {'Название'}
                            </div>
                            <div className="admin-item-publisher">
                                {'Адрес'}
                            </div>
                            <div className="admin-item-publisher">
                                {'Изменить'}
                            </div>
                            <div className="admin-item-publisher">
                                {'Удалить'}
                            </div>
                            <div className="admin-item-publisher">
                                {'Посмотреть'}
                            </div>
                        </div>

                    </ListGroup.Item>
                    <AdminListPublisher />
                </ListGroup>
            </Row>
            <Modal show={showCreate} onHide={handleCloseCreate}>
                <Modal.Header closeButton>
                    <Modal.Title>Создание издателя</Modal.Title>
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
                        value={namePublisher}
                        onChange={e => setNamePublisher(e.target.value)}
                    />
                    <Form.Label>
                        Адрес
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        required
                        placeholder='Введите адрес*'
                        value={address}
                        onChange={e => setAddress(e.target.value)}
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

export default AdminPublisher;