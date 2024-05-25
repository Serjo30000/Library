import Admin from "./pages/Admin";
import Basket from "./pages/Basket";
import Auth from "./pages/Auth";
import Registration from "./pages/Registration";
import Library from "./pages/Library";
import Book from "./pages/Book";
import Payment from "./pages/Payment";
import Account from "./pages/Account";
import Rental from "./pages/Rental";
import { ADMIN_ROUTE, ADMIN_BOOK, LOGIN_ROUTE, REGISTRATION_ROUTE, LIBRARY_ROUTE, BASKET_ROUTE, BOOK_ROUTE, PAYMENT_ROUTE, ACCOUNT_ROUTE, RENTAL_ROUTE, ADMIN_AUTHOR, ADMIN_GENRE, ADMIN_GRADE, ADMIN_PUBLISHER, ADMIN_RENTAL, ADMIN_STATISTICS, ADMIN_USER } from "./utils/consts";
import AdminBook from "./components/AdminBook";
import AdminAuthor from "./components/AdminAuthor";
import AdminGenre from "./components/AdminGenre";
import AdminGrade from "./components/AdminGrade";
import AdminPublisher from "./components/AdminPublisher";
import AdminRental from "./components/AdminRental";
import AdminStatistics from "./components/AdminStatistics";
import AdminUser from "./components/AdminUser";

export const userRoutes =  [
    {
        path: PAYMENT_ROUTE,
        Component: <Payment/>
    },
    {
        path: ACCOUNT_ROUTE,
        Component: <Account />
    },
    {
        path: RENTAL_ROUTE + '/:id',
        Component: <Rental />
    }
]

export const adminRoutes = [
    {
        path: ADMIN_ROUTE,
        Component: <Admin />
    },
    {
        path: ADMIN_BOOK,
        Component: <AdminBook />
    },
    {
        path: ADMIN_AUTHOR,
        Component: <AdminAuthor />
    },
    {
        path: ADMIN_GENRE,
        Component: <AdminGenre />
    },
    {
        path: ADMIN_GRADE,
        Component: <AdminGrade />
    },
    {
        path: ADMIN_PUBLISHER,
        Component: <AdminPublisher />
    },
    {
        path: ADMIN_RENTAL,
        Component: <AdminRental />
    },
    {
        path: ADMIN_STATISTICS,
        Component: <AdminStatistics />
    },
    {
        path: ADMIN_USER,
        Component: <AdminUser />
    }
]

export const publicRoutes =  [
    {
        path: LOGIN_ROUTE,
        Component: <Auth/>
    },
    {
        path: REGISTRATION_ROUTE,
        Component: <Registration/>
    },
    {
        path: LIBRARY_ROUTE,
        Component: <Library/>
    },
    {
        path: BOOK_ROUTE + '/:id',
        Component: <Book/>
    },
    {
        path: BASKET_ROUTE,
        Component: <Basket />
    }
]