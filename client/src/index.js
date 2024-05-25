import React, {createContext} from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import UserLibrary from './library/UserLibrary';
import BookLibrary from './library/BookLibrary';
import StatisticLibrary from './library/StatisticLibrary';

export const Context = createContext(null)

ReactDOM.render(
  <Context.Provider value={{
    user: new UserLibrary(),
    book: new BookLibrary(),
    statisticLib: new StatisticLibrary()
  }}>
    <App />
  </Context.Provider>,
  document.getElementById('root')
);
