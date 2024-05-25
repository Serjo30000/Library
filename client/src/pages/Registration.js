import React, { useState } from 'react';
import { Button, Container, Form, Modal } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import { NavLink, useNavigate } from 'react-router-dom';
import { LIBRARY_ROUTE, LOGIN_ROUTE } from '../utils/consts';
import { registration } from '../http/userAPI';
import '../App.css'
import '../Media.css'

const Registration = () => {
    const [name, setName] = useState('')
    const [surname, setSurname] = useState('')
    const [patronymic, setPatronymic] = useState('')
    const [phone, setPhone] = useState('')
    const [email, setEmail] = useState('')
    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')
    const [passwordConfirm, setPasswordConfirm] = useState('')
    const [showError, setShowError] = useState(false);
    const handleCloseError = () => setShowError(false);
    const handleShowError = () => setShowError(true);
    const navigate = useNavigate()
    const regIn = async () => {
        try{
            if (password !== passwordConfirm || (password === '' && passwordConfirm === '')){
                handleShowError()
            }
            else{
                await registration(name, surname, patronymic, phone, email, login, password);
                navigate(LIBRARY_ROUTE)
            }
            
        }
        catch(error){
            handleShowError()
            console.error('Registration failed:', error.message);
        }
    }
    return (
        <Container className='registration-container'>
            <Card className="registration-container-card">
                <h2 className="registration-container-h">
                    Регистрация
                </h2>
                <Form className="registration-container-form">
                    <Form.Control
                        className='registration-container-form-control'
                        required
                        placeholder='Введите ваше имя*'
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                    <Form.Control
                        className='registration-container-form-control'
                        required
                        placeholder='Введите вашу фамилию*'
                        value={surname}
                        onChange={e => setSurname(e.target.value)}
                    />
                    <Form.Control
                        className='registration-container-form-control'
                        placeholder='Введите ваше отчество'
                        value={patronymic}
                        onChange={e => setPatronymic(e.target.value)}
                    />
                    <Form.Control
                        className='registration-container-form-control'
                        required
                        placeholder='Введите ваш телефон*'
                        value={phone}
                        onChange={e => setPhone(e.target.value)}
                    />
                    <Form.Control
                        className='registration-container-form-control'
                        required
                        placeholder='Введите вашу почту*'
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                    <Form.Control
                        className='registration-container-form-control'
                        required
                        placeholder='Введите ваш логин*'
                        value={login}
                        onChange={e => setLogin(e.target.value)}
                    />
                    <Form.Control
                        className='registration-container-form-control'
                        required
                        placeholder='Введите ваш пароль*'
                        type='password'
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                    <Form.Control
                        className='registration-container-form-control'
                        required
                        placeholder='Повторите ваш пароль*'
                        type='password'
                        value={passwordConfirm}
                        onChange={e => setPasswordConfirm(e.target.value)}
                    />
                    <Row className='registration-container-form-row'>
                        <div className='registration-container-form-row_link'>
                            Есть аккаунт? <NavLink to={LOGIN_ROUTE}>Войди</NavLink>
                        </div>
                        <Button
                            className='registration-container-form-row-button'
                            variant={"outline-dark"}
                            onClick={regIn}>
                            Зарегистрироваться
                        </Button>
                    </Row>
                </Form>
            </Card>
            <Modal show={showError} onHide={handleCloseError}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы неправильно ввели данные для регистрации</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseError}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default Registration;