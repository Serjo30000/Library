import React, { useContext, useEffect } from 'react';
import { Container } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import GenreBar from '../components/GenreBar';
import BookList from '../components/BookList';
import { observer } from 'mobx-react-lite';
import { Context } from '..';
import { fetchGenres } from '../http/genreAPI';
import { fetchBooks } from '../http/bookAPI';
import '../App.css'
import '../Media.css'

const Library = observer(() => {
    const {book} = useContext(Context)

    useEffect(()=>{
        fetchGenres().then(data => book.setGenres(data))
        fetchBooks().then(data => book.setBooks(data))
    },[])
    return (
        <Container>
            <Row className="library-row">
                <Col md={3}>
                    <GenreBar/>
                </Col>
                <Col md={9}>
                    <BookList />
                </Col>
            </Row>
        </Container>
    );
});

export default Library;