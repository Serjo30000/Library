import { React, useContext } from 'react';
import { Routes, Route  } from 'react-router-dom'
import { userRoutes, adminRoutes, publicRoutes } from '../routes';
import {Context} from "../index";
import { Container } from 'react-bootstrap';
import '../App.css'
import '../Media.css'

const AppRouter = () => {
    const {user} = useContext(Context)
    return (
        <div style={{ minHeight: '100vh', display: 'flex'}}>
            <Routes>
                {user.isAuth && user.user.role === 'ROLE_ADMIN' && adminRoutes.map(({ path, Component }) =>
                    <Route key={path} path={path} element={Component} exact />
                )}
                {user.isAuth && user.user.role === 'ROLE_USER' && userRoutes.map(({ path, Component }) =>
                    <Route key={path} path={path} element={Component} exact />
                )}
                {publicRoutes.map(({ path, Component }) =>
                    <Route path={path} element={Component} />
                )}
            </Routes>
        </div>
        
    );
};

export default AppRouter;