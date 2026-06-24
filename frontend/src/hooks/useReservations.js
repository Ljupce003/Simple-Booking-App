import React, {useCallback, useEffect, useState} from 'react';
import reservationRepository from "../repository/reservationRepository.js";

const initState = {
    "loading": true,
    "reservations":[]
}

const UseReservations = (username) => {

    const [state,setState] = useState(initState);

    const fetchReservation = useCallback((username) => {
        setState(initState);
        reservationRepository
            .getReservations({"username":username})
            .then(response => {
                setState({
                    reservations: response.data,
                    loading: false
                })
            }).catch(err => console.log(err))
    },[])

    const confirmReserve = useCallback((id) => {
        setState(initState)
        reservationRepository
            .confirmReserve(id)
            .then(() => {
                fetchReservation(username);
            })
            .catch(err => console.log(err))
    },[fetchReservation, username])

    const confirmReserveAll = useCallback(() => {
        setState(initState)
        reservationRepository
            .confirmReserveAll()
            .then(() => {
                fetchReservation(username);
            })
            .catch(err => console.log(err))
    },[fetchReservation, username])





    useEffect(() => {
        fetchReservation(username);
    },[fetchReservation,username])


    return {...state,confirmReserve,confirmReserveAll};
};

export default UseReservations;