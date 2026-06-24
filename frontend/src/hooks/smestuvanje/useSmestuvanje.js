import {useCallback, useEffect, useState} from 'react';
import smestuvanjeRepository from "../../repository/smestuvanjeRepository.js";

const initialState = {
    "smestuvanja": [],
    "loading": true
}

const UseSmestuvanje = () => {

    const [state,setState] = useState(initialState);

    const fetchSmestuvanja = useCallback(() => {
        setState(initialState)
        smestuvanjeRepository
            .findAll()
            .then((response) => {
                setState({
                    "smestuvanja": response.data,
                    "loading": false
                })
            })
            .catch((err) => console.log(err));
    },[])

    const onAdd = useCallback((data) => {
        smestuvanjeRepository
            .add(data)
            .then(() => {
                console.log("A Smestuvanje has been added successfully");
                fetchSmestuvanja();
            })
        },[fetchSmestuvanja]);

    const onEdit = useCallback((id,data) => {
        smestuvanjeRepository
            .edit(id,data)
            .then(() => {
                console.log(`A Smestuvanje has been edited with id: ${id}`);
                fetchSmestuvanja();
            })
            .catch((err) => console.log(err))
    },[fetchSmestuvanja]);

    const onDelete = useCallback((id) => {
        smestuvanjeRepository
            .delete(id)
            .then(() => {
                console.log(`A Smestuvanje has been deleted with id: ${id}`);
                fetchSmestuvanja();
            })
            .catch((err) => console.log(err));
    },[fetchSmestuvanja]);

    const onReserve = useCallback((id) => {
        smestuvanjeRepository
            .reserve(id)
            .then(() => {
                console.log(`A Smestuvanje has been reserved with id: ${id}`);
                fetchSmestuvanja();
            })
            .catch((err) => console.log(err));
    },[fetchSmestuvanja]);



    useEffect(() => {
        fetchSmestuvanja();
    },[fetchSmestuvanja]);

    return {...state,onAdd,onEdit,onDelete,onReserve};
};

export default UseSmestuvanje;