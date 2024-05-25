import React, { useContext, useEffect, useRef, useState } from 'react';
import { observer } from "mobx-react-lite"
import { Context } from "../index";
import Chart from 'chart.js/auto'
import '../App.css'
import '../Media.css'

const AdminStatisticBuyItem = observer(() => {
    const { statisticLib } = useContext(Context)
    const chartRef = useRef(null);
    useEffect(() => {

        const chart = new Chart(chartRef.current, {
            type: 'bar',
            data: {
                labels: ['1 день', '2 день', '3 день', '4 день', '5 день', '6 день', '7 день'],
                datasets: [
                    {
                        label: 'Продажи',
                        data: [statisticLib.firstDay, statisticLib.secondDay, statisticLib.thirdDay, statisticLib.fourthDay, statisticLib.fifthDay, statisticLib.sixthDay, statisticLib.seventhDay],
                        backgroundColor: 'rgba(75, 192, 192, 0.6)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                    },
                ],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
            },
        });
        
        return () => {
            chart.destroy();
        };
    }, []);
    return <canvas ref={chartRef} />;
});

export default AdminStatisticBuyItem;