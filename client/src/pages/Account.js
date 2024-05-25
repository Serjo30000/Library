import React, { useContext, useEffect } from 'react';
import { observer } from "mobx-react-lite"
import ListGroup from 'react-bootstrap/ListGroup';
import { Container } from 'react-bootstrap';
import { fetchRentals } from '../http/rentalAPI';
import { Context } from '..';
import RentalList from '../components/RentalList';
import '../App.css'
import '../Media.css'

const Account = observer(() => {
    const { book,user } = useContext(Context)
    useEffect(() => {
        fetchRentals().then(data =>{
            book.setRentals(data.filter(function (r) {
                if (r.userLibrary.login === user.user.login) {
                    return r
                }
            }))
        })
    }, [])
    return (
        <Container>
            <ListGroup>
                <ListGroup.Item className="account-scroll">
                    <div className="account-item">
                        <div className="account-item-text">
                            {'Номер'}
                        </div>
                        <div className="account-item-text">
                            {'Название'}
                        </div>
                        <div className="account-item-text">
                            {'Дата публикации'}
                        </div>
                        <div className="account-item-text">
                            {'Кол-во'}
                        </div>
                        <div className="account-item-text">
                            {'Цена'}
                        </div>
                        <div className="account-item-text">
                            {'Жанр'}
                        </div>
                        <div className="account-item-text">
                            {'Издатель'}
                        </div>
                        <div className="account-item-text">
                            {'Автор'}
                        </div>
                        <div className="account-item-text">
                            {'Куплено'}
                        </div>
                        <div className="account-item-text">
                            {'Дата начала'}
                        </div>
                        <div className="account-item-text">
                            {'Дата конца'}
                        </div>
                        <div className="account-item-text">
                            {'Удалить'}
                        </div>
                        <div className="account-item-text">
                            {'Посмотреть'}
                        </div>
                    </div>
                </ListGroup.Item>
                <RentalList />
            </ListGroup>
        </Container>
    );
});

export default Account;