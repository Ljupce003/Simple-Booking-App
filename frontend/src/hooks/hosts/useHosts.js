import {useCallback, useEffect, useState} from 'react';
import hostRepository from "../../repository/hostRepository.js";

const initialState = {
    "hosts": [],
    "loading": true
}

const UseHosts = () => {
    const [state,setState] = useState(initialState);


    const fetchHosts = useCallback(() => {
        setState(initialState)
        hostRepository
            .findAll()
            .then((response) =>{
                setState({
                    "hosts":response.data,
                    "loading":false
                });
            } ).catch((err) => console.log(err))
    },[]);


    const onAdd = useCallback((data) => {
        hostRepository
            .add(data)
            .then(() => {
                console.log("A host has been added successfully");
                fetchHosts();
            })
            .catch((err) => console.log(err))
    },[fetchHosts])

    const onEdit = useCallback((id,data) => {
        hostRepository
            .edit(id,data)
            .then(() => {
                console.log(`A host has been edited successfully with id: ${id}`);
                fetchHosts();
            })
            .catch((err) => console.log(err))
    },[fetchHosts])

    const onDelete = useCallback((id) => {
        hostRepository
            .delete(id)
            .then(() => {
                console.log(`A host has been deleted successfully with id: ${id}`);
                fetchHosts();
            })
            .catch((err) => console.log(err))
    },[fetchHosts])



    useEffect(() => {
        fetchHosts();
    },[fetchHosts]);

    return {...state,onAdd,onEdit,onDelete};
};

export default UseHosts;