import { useEffect, useState} from 'react';
import smestuvanjeRepository from "../../repository/smestuvanjeRepository.js";

// {
//     "name": "Stan",
//     "category": "FLAT",
//     "hostDTO": {
//     "name": "Peter",
//         "surname": "Griffin",
//         "countryDTO": {
//         "id": 1,
//             "name": "Macedonia",
//             "continent": "Europe"
//     }
// },
//     "numRooms": 4
// }


const initialState = {
    "loading": true,
    "smestuvanje": {
        "name": "",
        "category": "",
        "hostDTO": {
            "name": "",
            "surname": "",
            "countryDTO": {
                "id": "",
                "name": "",
                "continent": ""
            }
        },
        "numRooms": ""

    }

}


const UseSmestuvanjeDetailsServer = (id) => {
    const [smestuvanjeDstate,setSmestuvanjeDstate] = useState(initialState)

    
    useEffect( () => {
        setSmestuvanjeDstate(initialState)
        smestuvanjeRepository
            .fetchById(id)
            .then((response) => {
                let data = response.data;

                setSmestuvanjeDstate({
                    loading: false,
                    smestuvanje: data
                });
                console.log("Fetch used")
            })
            .catch(err => console.log(err))
        console.log("Fetch called");
    },[id]);

    return smestuvanjeDstate;
};

export default UseSmestuvanjeDetailsServer;