import React, {useContext} from 'react';
import { Context } from '../index';
import Nav from "react-bootstrap/Nav"
import Navbar from 'react-bootstrap/Navbar';
import { NavLink } from 'react-router-dom';
import { ACCOUNT_ROUTE, ADMIN_ROUTE, BASKET_ROUTE, LIBRARY_ROUTE, LOGIN_ROUTE, REGISTRATION_ROUTE } from '../utils/consts';
import { Button, Container } from 'react-bootstrap';
import { observer } from 'mobx-react-lite';
import { useNavigate } from 'react-router-dom';
import '../App.css'
import '../Media.css'

const NavBar = observer(() => {
    const {user} = useContext(Context)
    const navigate = useNavigate()
    const logOut = () => {
        user.setUser({})
        user.setIsAuth(false)
        localStorage.setItem('token','')
        localStorage.setItem('BookRentals', JSON.stringify([]))
        navigate(LIBRARY_ROUTE)
        window.location.reload();
    }
    return (
        <Navbar bg="dark" variant="dark">
        <Container>
                <NavLink className="nav-bar-link" to={LIBRARY_ROUTE}>Library</NavLink>
                {user.isAuth && (user.user.role === 'ROLE_ADMIN' || user.user.role === 'ROLE_USER') ?
                    <Nav className="nav-bar-link-nav">
                        <Button className="nav-bar-link-nav-button" variant={"outline-light"} onClick={() => navigate(BASKET_ROUTE)}>Корзина</Button>
                        <Button className="nav-bar-link-nav-button" variant={"outline-light"} onClick={() => logOut()}>Выйти</Button>
                        {
                            user.isAuth && (user.user.role === 'ROLE_ADMIN') ?
                                <Button className="nav-bar-link-nav-button" variant={"outline-light"} onClick={() => navigate(ADMIN_ROUTE)}>Админ панель</Button>
                                :
                                <Button className="nav-bar-link-nav-button" variant={"outline-light"} onClick={() => navigate(ACCOUNT_ROUTE)}>Аккаунт</Button>
                        }
                    </Nav>
                    :
                    <Nav className="nav-bar-link-nav">
                        <Button className="nav-bar-link-nav-button" variant={"outline-light"} onClick={() => navigate(BASKET_ROUTE)}>Корзина</Button>
                        <Button className="nav-bar-link-nav-button" variant={"outline-light"} onClick={() => navigate(LOGIN_ROUTE)}>Войти</Button>
                        <Button className="nav-bar-link-nav-button" variant={"outline-light"} onClick={() => navigate(REGISTRATION_ROUTE)}>Регистрация</Button>
                    </Nav>
                }
        </Container>
        </Navbar>
    );
});

export default NavBar;