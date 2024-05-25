import React, { useContext, useEffect, useState } from 'react';
import {BrowserRouter} from 'react-router-dom';
import AppRouter from './components/AppRouter';
import NavBar from './components/NavBar';
import Basement from './components/Basement';
import { Context } from '.';
import { check, defaultAdmin } from './http/userAPI';
import { observer } from "mobx-react-lite"
import { Spinner } from 'react-bootstrap';

const App = observer(() => {
  const {user} = useContext(Context)
  const [loading, setLoading] = useState(true)
  if (localStorage.getItem('BookRentals') == null || localStorage.getItem('BookRentals') == ''){
    localStorage.setItem('BookRentals', JSON.stringify([]))
  }

  useEffect(() => {
    defaultAdmin()
    check(localStorage.getItem('token')).then(data =>{
      user.setUser(data)
      user.setIsAuth(true)
    }).catch(data =>{
      user.setUser({})
      user.setIsAuth(false)
    }).finally(() => setLoading(false))
  },[])

  if (loading){
    return <Spinner animation={"grow"}/>
  }
  // console.log(user.isAuth)
  return (
    <BrowserRouter>
      <NavBar />
      <AppRouter />
      <Basement />
    </BrowserRouter>
  );
});

export default App;