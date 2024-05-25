import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListGroup from 'react-bootstrap/ListGroup';
import { Context } from '..';
import { deletePublisher, editPublisher } from '../http/publisherAPI';
import '../App.css'
import '../Media.css'

const AdminPublisherItem = ({ publisher, i }) => {
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
    const [namePublisher, setNamePublisher] = useState(publisher.namePublisher)
    const [address, setAddress] = useState(publisher.address)
    const [showErrorEdit, setShowErrorEdit] = useState(false);
    const handleCloseErrorEdit = () => setShowErrorEdit(false);
    const handleShowErrorEdit = () => setShowErrorEdit(true);

    const editFun = async () => {
        try {
            if (!(namePublisher === '' || address === '')) {
                await editPublisher(publisher.id, namePublisher, address);
                document.location.reload();
            }
            else {
                setNamePublisher(publisher.namePublisher)
                setAddress(publisher.address)
                handleCloseEdit()
                handleShowErrorEdit()
            }
        }
        catch (error) {
            setNamePublisher(publisher.namePublisher)
            setAddress(publisher.address)
            handleCloseEdit()
            handleShowErrorEdit()
            console.error('Edit failed:', error.message);
        }
    }
    const deleteFun = async () => {
        try {
            await deletePublisher(publisher.id);
            document.location.reload();
        }
        catch (error) {
            console.error('Delete failed:', error.message);
        }
    }
    return (
        <ListGroup.Item className="admin-scroll">
            <Form className="admin-item">
                <div className="admin-item-publisher">
                    {publisher.id}
                </div>
                <div className="admin-item-publisher">
                    {publisher.namePublisher}
                </div>
                <div className="admin-item-publisher">
                    {publisher.address}
                </div>
                <Button
                    onClick={handleShowEdit}
                    className="admin-item-publisher"
                    variant={"outline-dark"}>
                    Изменить
                </Button>
                <Button
                    onClick={handleShowDelete}
                    className="admin-item-publisher"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={handleShowView}
                    className="admin-item-publisher"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={showEdit} onHide={handleCloseEdit}>
                <Modal.Header closeButton>
                    <Modal.Title>Изменение издателя</Modal.Title>
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
                    <Modal.Title>Удаление издателя</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить издателя?</Modal.Body>
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
                    <Modal.Title>Просмотр издателя</Modal.Title>
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
                        value={namePublisher}
                        onChange={e => setNamePublisher(e.target.value)}
                    />
                    <Form.Label>
                        Адрес
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите адрес*'
                        value={address}
                        onChange={e => setAddress(e.target.value)}
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

export default AdminPublisherItem;