import {useEffect, useState} from 'react';
import countryRepository from "../../repository/countryRepository.js";

const UseCountriesDetails = (id) => {
    const [state,setState] = useState({
        "name":"",
        "continent":"",
        }
    );

    useEffect(() => {
        countryRepository
            .findById(id)
            .then((response) => {
                setState(response.data);
            })
            .catch((err) => console.log(err));
    },[id]);


    return state;
};

export default UseCountriesDetails;