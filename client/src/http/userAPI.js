import { $authHost, $host } from './index';
import { jwtDecode as jwt_decode } from 'jwt-decode';

export const registration = async (name, surname, patronymic, phone, email, login, password) => {
    const { data } = await $host.put('api/userLibraries/save', { name, surname, patronymic, phone, email, login, password })
}

export const defaultAdmin = async () => {
    const { data } = await $host.put('api/userLibraries/createDefaultAdmin')
}

export const logIn = async (login, password) => {
    const { data } = await $host.post('api/auth/login', { login, password })
    localStorage.setItem('token', data.token)
    return jwt_decode(data.token)
}

export const check = async (token) => {
    const obj = jwt_decode(token)
    const { data } = await $authHost.post('api/auth/check', { login: obj.login, role: obj.role})
    localStorage.setItem('token', data.token)
    return jwt_decode(data.token)
}

export const fetchUserLibrary = async (login) => {
    const { data } = await $host.get(`api/userLibraries/user/${login}`)
    return data
}

export const fetchUserLibraries = async () => {
    const { data } = await $host.get(`api/userLibraries`)
    return data
}

export const deleteUserLibrary = async (login) => {
    const { data } = await $authHost.delete(`api/userLibraries/delete/${login}`)
}

export const editUserLibrary = async (login) => {
    const { data } = await $authHost.get(`api/userLibraries/${login}/updateRoleByLogin`)
    return data
}