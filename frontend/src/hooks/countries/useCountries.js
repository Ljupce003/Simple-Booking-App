import {useCallback, useEffect, useState} from "react";
import countryRepository from "../../repository/countryRepository.js";

const initialState = {
    "countries":[],
    "loading":true,
}

const useCountries = () =>{
    const [state,setState] = useState(initialState);

    const fetchCountries = useCallback(() => {
        setState(initialState)
        countryRepository
            .findAll()
            .then((response) => {
                setState({
                    "countries":response.data,
                    "loading":false,
                });
            })
            .catch((err) => console.log(err));

    },[]);

    const onAdd = useCallback((data) => {
        countryRepository
            .add(data)
            .then(() => {
                console.log("Successfully added product");
                fetchCountries();
            })
    },[fetchCountries]);

    const onEdit = useCallback((id,data) => {
        countryRepository
            .edit(id,data)
            .then(() => {
                console.log(`Successfully edited product with id: ${id}`);
                fetchCountries();
            })

    },[fetchCountries]);

    const onDelete = useCallback((id) => {
        countryRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted product with id: ${id}`);
                fetchCountries();
            })
    },[fetchCountries]);

    useEffect(() => {
        fetchCountries();
    },[fetchCountries]);




    return {...state,onAdd:onAdd,onEdit: onEdit, onDelete: onDelete};
};

export default useCountries;