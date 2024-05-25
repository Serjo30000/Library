import React from 'react';
import { Col, Container, Image, Row } from 'react-bootstrap';
import Navbar from 'react-bootstrap/Navbar';
import { NavLink } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { ADMIN_ROUTE, API_ROUTE, LIBRARY_ROUTE, MAIL_ROUTE, PHONE_ROUTE, VK_ROUTE } from '../utils/consts';
import vk from '../assets/static/vk.png';
import mail from '../assets/static/mail.png';
import phone from '../assets/static/phone.png';
import '../App.css'
import '../Media.css'

const Basement = () => {
    const navigate = useNavigate()
    return (
        <Navbar bg="dark" variant="dark" >
            <Container className="d-flex justify-content-center flex-wrap " style={{ minHeight: '100vh', background: '#212529', alignContent: 'flex-start' }}>
                <div className="basement-container-up">
                        
                </div>
                <div className="basement-container-body">
                    <Col md={3} className="basement-container-body-left">
                        <h1 className="basement-container-body-left-h">
                            О интернет магазине 'Library'
                        </h1>
                        <div className="basement-container-body-left-text">
                            Этот сайт и приложение были созданы для получения знаний в использовании React, а также Java c микросервисной архитектурой приложения с применением написанного API, задокументированного в Swagger.
                        </div>
                    </Col>
                    <Col md={3} className="basement-container-body-right">
                        <h1 className="basement-container-body-right-h">
                            Ссылки и контакты
                        </h1>
                        <div className="basement-container-body-right-link">
                            <NavLink to={API_ROUTE} className='basement-container-body-right-link-text nav-link d-inline' target='_blank'>
                                API
                            </NavLink>
                            <NavLink to={LIBRARY_ROUTE} className='basement-container-body-right-link-text nav-link d-inline' target='_blank'>
                                Главная
                            </NavLink>
                            <NavLink to={ADMIN_ROUTE} className='basement-container-body-right-link-text nav-link d-inline' target='_blank'>
                                Админ панель
                            </NavLink>
                        </div>
                        <div className="basement-container-body-right-link">
                            <NavLink to={VK_ROUTE} className='basement-container-body-right-link-text nav-link d-inline' target='_blank'>
                                <Image className="basement-container-body-right-link-text-image" src={vk} />
                            </NavLink>
                            <NavLink to={MAIL_ROUTE} className='basement-container-body-right-link-text nav-link d-inline' target='_blank'>
                                <Image className="basement-container-body-right-link-text-image" src={mail} />
                            </NavLink>
                            <NavLink to={PHONE_ROUTE} className='basement-container-body-right-link-text nav-link d-inline' target='_blank'>
                                <Image className="basement-container-body-right-link-text-image" src={phone} />
                            </NavLink>
                        </div>
                    </Col>
                </div>
                
            </Container>
        </Navbar>
    );
};

export default Basement;