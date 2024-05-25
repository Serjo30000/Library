import React, { useContext, useState } from 'react';
import { Button, Container, Form, Modal } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import { NavLink } from 'react-router-dom';
import { LIBRARY_ROUTE, REGISTRATION_ROUTE } from '../utils/consts';
import { logIn, registration } from '../http/userAPI';
import { observer } from "mobx-react-lite"
import { Context } from '..';
import { useNavigate } from 'react-router-dom';
import '../App.css'
import '../Media.css'

const Auth = observer(() => {
    const {user} = useContext(Context)
    const [login, setLoginUser] = useState('')
    const [password, setPassword] = useState('')
    const [showError, setShowError] = useState(false);
    const handleCloseError = () => setShowError(false);
    const handleShowError = () => setShowError(true);
    const navigate = useNavigate()
    const signIn = async () => {
        try{
            let data = await logIn(login, password);
            user.setUser(data)
            user.setIsAuth(true)
            navigate(LIBRARY_ROUTE)
            window.location.reload();
        }
        catch(error){
            handleShowError()
            console.error('Auth failed:', error.message);
        }
    }

    return (
        <Container className='auth-login'>
            <Card className="auth-login-card">
                <h2 className="auth-login-h">
                    Вход
                </h2>
                <Form className="auth-login-form">
                    <Form.Control
                        className='auth-login-form-control'
                        placeholder='Введите ваш логин'
                        value={login}
                        onChange={e => setLoginUser(e.target.value)}
                    />
                    <Form.Control
                        className='auth-login-form-control'
                        placeholder='Введите ваш пароль'
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        type="password"
                    />
                    <Row className='auth-login-form-row'>
                        <div className='auth-login-form-row-link'>
                            Нет аккаунта? <NavLink to={REGISTRATION_ROUTE}>Зарегистрируйся</NavLink>
                        </div>
                        <Button
                            className='auth-login-form-row-button'
                            variant={"outline-dark"}
                            onClick={signIn}>
                            Войти
                        </Button>
                    </Row>
                </Form>
            </Card>
            <Modal show={showError} onHide={handleCloseError}>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>Вы неправильно ввели логин или пароль</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseError}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
});

export default Auth;