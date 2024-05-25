import { $authHost, $host } from './index';

export const fetchRentalsByUserLibrary = async (userLibrary) => {
    const { data } = await $host.get(`api/rentals/userLibrary/${userLibrary}`)
    return data
}

export const fetchRentals = async () => {
    const { data } = await $host.get('api/rentals')
    return data
}

export const fetchRental = async (rental) => {
    const { data } = await $host.get(`api/rentals/${rental}`)
    return data
}

export const deleteRental = async (id) => {
    const { data } = await $authHost.delete(`api/rentals/${id}`)
}