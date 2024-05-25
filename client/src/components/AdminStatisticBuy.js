import React, { useContext, useEffect } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import AdminStatisticBuyItem from './AdminStatisticBuyItem';
import '../App.css'
import '../Media.css'

const AdminStatisticBuy = observer(() => {
    const { book, statisticLib } = useContext(Context)
    useEffect(() => {
        calculationStatisticBuy()
    }, [])

    const calculationStatisticBuy = () => {
        try {
            let currentDate = new Date()
            let firstD = 0
            let secondD = 0
            let thirdD = 0
            let fourthD = 0
            let fifthD = 0
            let sixthD = 0
            let seventhD = 0
            book.rentals.forEach(element => {
                if ((new Date(Date.parse(element.dateStart))).getFullYear() === currentDate.getFullYear() && (new Date(Date.parse(element.dateStart))).getMonth() === currentDate.getMonth() && (new Date(Date.parse(element.dateStart))).getDate() === currentDate.getDate()){
                    seventhD++
                }
                if ((new Date(Date.parse(element.dateStart))).getFullYear() === currentDate.getFullYear() && (new Date(Date.parse(element.dateStart))).getMonth() === currentDate.getMonth() && (new Date(Date.parse(element.dateStart))).getDate() === currentDate.getDate()-1) {
                    sixthD++
                }
                if ((new Date(Date.parse(element.dateStart))).getFullYear() === currentDate.getFullYear() && (new Date(Date.parse(element.dateStart))).getMonth() === currentDate.getMonth() && (new Date(Date.parse(element.dateStart))).getDate() === currentDate.getDate()-2) {
                    fifthD++
                }
                if ((new Date(Date.parse(element.dateStart))).getFullYear() === currentDate.getFullYear() && (new Date(Date.parse(element.dateStart))).getMonth() === currentDate.getMonth() && (new Date(Date.parse(element.dateStart))).getDate() === currentDate.getDate()-3) {
                    fourthD++
                }
                if ((new Date(Date.parse(element.dateStart))).getFullYear() === currentDate.getFullYear() && (new Date(Date.parse(element.dateStart))).getMonth() === currentDate.getMonth() && (new Date(Date.parse(element.dateStart))).getDate() === currentDate.getDate()-4) {
                    thirdD++
                }
                if ((new Date(Date.parse(element.dateStart))).getFullYear() === currentDate.getFullYear() && (new Date(Date.parse(element.dateStart))).getMonth() === currentDate.getMonth() && (new Date(Date.parse(element.dateStart))).getDate() === currentDate.getDate()-5) {
                    secondD++
                }
                if ((new Date(Date.parse(element.dateStart))).getFullYear() === currentDate.getFullYear() && (new Date(Date.parse(element.dateStart))).getMonth() === currentDate.getMonth() && (new Date(Date.parse(element.dateStart))).getDate() === currentDate.getDate()-6) {
                    firstD++
                }
            });
            statisticLib.setFirstDay(firstD)
            statisticLib.setSecondDay(secondD)
            statisticLib.setThirdDay(thirdD)
            statisticLib.setFourthDay(fourthD)
            statisticLib.setFifthDay(fifthD)
            statisticLib.setSixthDay(sixthD)
            statisticLib.setSeventhDay(seventhD)

        }
        catch (error) {
            console.error('Statistic failed:', error.message);
        }
    }
    return (
        <div className='admin-statistic-buy-block'>
            <AdminStatisticBuyItem />
        </div>
    );
});

export default AdminStatisticBuy;