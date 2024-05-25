import axios from "axios";

const $host = axios.create({
    baseURL: 'http://localhost:8080/'
})

const $authHost = axios.create({
    baseURL: 'http://localhost:8080/'
})

const authInterceptor = config => {
    config.headers.authorization = `Bearer ${localStorage.getItem('token')}`
    return config
}

$authHost.interceptors.request.use(authInterceptor)

const $fileImageHost = axios.create({
    baseURL: 'http://localhost:8080/'
})

const fileInterceptor = config => {
    config.headers.authorization = `Bearer ${localStorage.getItem('token')}`
    config.headers['Content-Type'] = 'multipart/form-data'
    return config
}

$fileImageHost.interceptors.request.use(fileInterceptor)

export {
    $host,
    $authHost,
    $fileImageHost
}