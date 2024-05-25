import React, { useContext } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminGradeItem from './AdminGradeItem';
import '../App.css'
import '../Media.css'

const AdminListGrade = observer(() => {
    const { book } = useContext(Context)
    return (
        <>
            {book.grades.map((grade, i) =>
                <AdminGradeItem key={grade.id} grade={grade} i={i}>

                </AdminGradeItem>
            )}
        </>
    );
});

export default AdminListGrade;