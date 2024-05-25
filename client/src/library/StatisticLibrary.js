import { makeAutoObservable } from "mobx"

export default class StatisticLibrary {
    constructor() {
        this._firstDay = {}
        this._secondDay = {}
        this._thirdDay = {}
        this._fourthDay = {}
        this._fifthDay = {}
        this._sixthDay = {}
        this._seventhDay = {}
        makeAutoObservable(this)
    }

    setFirstDay(firstDay) {
        this._firstDay = firstDay
    }

    setSecondDay(secondDay) {
        this._secondDay = secondDay
    }

    setThirdDay(thirdDay) {
        this._thirdDay = thirdDay
    }

    setFourthDay(fourthDay) {
        this._fourthDay = fourthDay
    }

    setFifthDay(fifthDay) {
        this._fifthDay = fifthDay
    }

    setSixthDay(sixthDay) {
        this._sixthDay = sixthDay
    }

    setSeventhDay(seventhDay) {
        this._seventhDay = seventhDay
    }

    get firstDay() {
        return this._firstDay
    }

    get secondDay() {
        return this._secondDay
    }

    get thirdDay() {
        return this._thirdDay
    }

    get fourthDay() {
        return this._fourthDay
    }

    get fifthDay() {
        return this._fifthDay
    }

    get sixthDay() {
        return this._sixthDay
    }

    get seventhDay() {
        return this._seventhDay
    }
}