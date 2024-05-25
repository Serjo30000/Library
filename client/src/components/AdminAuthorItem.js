import React, { useContext, useState } from 'react';
import { Card, Button, Container, Form, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListGroup from 'react-bootstrap/ListGroup';
import { Context } from '..';
import { deleteAuthor, editAuthor } from '../http/authorAPI';
import '../App.css'
import '../Media.css'

const AdminAuthorItem = ({ author, i }) => {
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
    const [name, setName] = useState(author.name)
    const [surname, setSurname] = useState(author.surname)
    const [patronymic, setPatronymic] = useState(author.patronymic)
    const [dateBirth, setDateBirth] = useState(author.dateBirth)
    const [showErrorEdit, setShowErrorEdit] = useState(false);
    const handleCloseErrorEdit = () => setShowErrorEdit(false);
    const handleShowErrorEdit = () => setShowErrorEdit(true);

    const editFun = async () => {
        try {
            if (!(name === '' || surname === '')) {
                await editAuthor(author.id, name, surname, patronymic, dateBirth);
                document.location.reload();
            }
            else {
                setName(author.name)
                setSurname(author.surname)
                setPatronymic(author.patronymic)
                setDateBirth(author.dateBirth)
                handleCloseEdit()
                handleShowErrorEdit()
            }
        }
        catch (error) {
            setName(author.name)
            setSurname(author.surname)
            setPatronymic(author.patronymic)
            setDateBirth(author.dateBirth)
            handleCloseEdit()
            handleShowErrorEdit()
            console.error('Edit failed:', error.message);
        }
    }
    const deleteFun = async () => {
        try {
            await deleteAuthor(author.id);
            document.location.reload();
        }
        catch (error) {
            console.error('Delete failed:', error.message);
        }
    }
    return (
        <ListGroup.Item className="admin-scroll">
            <Form className="admin-item">
                <div className="admin-item-author">
                    {author.id}
                </div>
                <div className="admin-item-author">
                    {author.name}
                </div>
                <div className="admin-item-author">
                    {author.surname}
                </div>
                <div className="admin-item-author">
                    {author.patronymic}
                </div>
                <div className="admin-item-author">
                    {author.dateBirth}
                </div>
                <Button
                    onClick={handleShowEdit}
                    className="admin-item-author"
                    variant={"outline-dark"}
                    >
                    Изменить
                </Button>
                <Button
                    onClick={handleShowDelete}
                    className="admin-item-author"
                    variant={"outline-dark"}>
                    Удалить
                </Button>
                <Button
                    onClick={handleShowView}
                    className="admin-item-author"
                    variant={"outline-dark"}>
                    Посмотреть
                </Button>
            </Form>
            <Modal show={showEdit} onHide={handleCloseEdit}>
                <Modal.Header closeButton>
                    <Modal.Title>Изменение автора</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель изменения</Modal.Body>
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
                    <Modal.Title>Удаление автора</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы уверены, что хотите удалить автора?</Modal.Body>
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
                    <Modal.Title>Просмотр автора</Modal.Title>
                </Modal.Header>
                <Modal.Body>Панель просмотра</Modal.Body>
                <Modal.Footer>
                    <Form.Label>
                        Имя
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите имя*'
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                    <Form.Label>
                        Фамилия
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите фамилию*'
                        value={surname}
                        onChange={e => setSurname(e.target.value)}
                    />
                    <Form.Label>
                        Отчество
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите отчество'
                        value={patronymic}
                        onChange={e => setPatronymic(e.target.value)}
                    />
                    <Form.Label>
                        Дата рождения
                    </Form.Label>
                    <Form.Control
                        className='mt-3'
                        readOnly
                        placeholder='Введите дату рождения*'
                        type='date'
                        value={dateBirth}
                        onChange={e => setDateBirth(e.target.value)}
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

export default AdminAuthorItem;