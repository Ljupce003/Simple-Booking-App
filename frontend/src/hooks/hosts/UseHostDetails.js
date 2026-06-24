import {useEffect, useState} from 'react';
import hostRepository from "../../repository/hostRepository.js";
import countryRepository from "../../repository/countryRepository.js";

// {
//     "name": "string",
//     "surname": "string",
//     "countryID": 9007199254740991
// }

const UseHostDetails = (id) => {
    const [hostDetail,setHostDetail] = useState(
    {
        "name": "blank",
        "surname": "blank",
        "countryID": -1,
        "country": {
            "name":"blank",
            "continent":"blank"
        }
    });

    useEffect(() => {
        hostRepository
            .findById(id)
            .then((response) => {
                setHostDetail(prev =>
                    ({...prev,
                        "name":response.data.name,
                    "surname":response.data.surname,
                    "countryID":response.data.countryID})
                );

                countryRepository
                    .findById(response.data.countryID)
                    .then((countryResponse) => {
                        if(countryResponse !== undefined && countryResponse.data !== undefined)
                            setHostDetail((prevState) =>
                            {
                                return {...prevState, "country":countryResponse.data};
                            })
                    })
                    .catch((country_err) => console.log(country_err))
            })
    },[id])

    return hostDetail;
};

export default UseHostDetails;