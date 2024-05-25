import React, { useContext, useEffect } from 'react';
import { Container } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { fetchRentals } from '../http/rentalAPI';
import AdminStatisticBuy from './AdminStatisticBuy';
import '../App.css'
import '../Media.css'

const AdminStatistics = observer(() => {
    const { book } = useContext(Context)

    useEffect(() => {
        fetchRentals().then(data => book.setRentals(data))
    }, [])
    return (
        <Container>
            <Row className="admin-top">
                <h1>
                    Статистика
                </h1>
            </Row>
            <Row className="admin-statistic-row">
                <AdminStatisticBuy/>
            </Row>
        </Container>
    );
});

export default AdminStatistics;