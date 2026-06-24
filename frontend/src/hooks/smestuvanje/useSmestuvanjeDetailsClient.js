import {useEffect, useState} from 'react';
import smestuvanjeRepository from "../../repository/smestuvanjeRepository.js";
import hostRepository from "../../repository/hostRepository.js";
import countryRepository from "../../repository/countryRepository.js";

const initData =  {

    "loading":true,
    "smestuvanjeDetail":{
        "smestuvanje": {
            "name":"",
            "category":"",
            "numRooms":""
        },
        "host": {
            "name": "",
            "surname":"",
            "countryID": ""
        },
        "country": {
            "name":"",
            "continent":""
        }
    }


}

// {
//     "name": "Stan",
//     "category": "FLAT",
//     "hostID": 1,
//     "numRooms": 4
// }

// {
//     "name": "Peter",
//     "surname": "Griffin",
//     "countryID": 1,
//     "id": 1
// }

// {
//     "id": 1,
//     "name": "Macedonia",
//     "continent": "Europe"
// }

const UseSmestuvanjeDetailsClient = (id) => {
    const [smestuvanjeStateC,setSmestuvanjeStateC] = useState(initData);

    useEffect(() => {
        setSmestuvanjeStateC(initData);
        smestuvanjeRepository
                .findById(id)
                .then((sResponse) => {
                    setSmestuvanjeStateC(prevS => ({
                        ...prevS,smestuvanjeDetail: {
                            ...prevS.smestuvanjeDetail,
                            smestuvanje: sResponse.data
                        }
                        ,loading: false
                    }));
                    if(sResponse.data.hostID != null){
                        hostRepository
                            .findById(sResponse.data.hostID)
                            .then((hResponse) => {
                                setSmestuvanjeStateC(prev => ({
                                    ...prev,smestuvanjeDetail: {
                                        ...prev.smestuvanjeDetail,
                                        host:hResponse.data
                                    }
                                    ,loading: false
                                }));
                                if(hResponse.data.countryID != null){
                                    countryRepository
                                        .findById(hResponse.data.countryID)
                                        .then((cResponse) => {
                                            setSmestuvanjeStateC(prev => ({
                                                ...prev, smestuvanjeDetail: {
                                                    ...prev.smestuvanjeDetail,
                                                    country: cResponse.data
                                                },
                                                loading: false
                                            }));
                                        })
                                        .catch((err) => console.log(err));}
                            })
                            .catch((err) => console.log(err));
                    }
                })
                .catch((err) => console.log(err))

    },[id]);

    return smestuvanjeStateC
};

export default UseSmestuvanjeDetailsClient;




/*useEffect(() => {
        smestuvanjeRepository
            .findById(id)
            .then((sResponse) => {
                setSmestuvanje(prevS => ({...prevS,
                    "name": sResponse.data.name,
                    "category": sResponse.data.category,
                    "hostID": sResponse.data.hostID,
                    "numRooms": sResponse.data.numRooms,
                    "id":sResponse.data.id
                }));
                hostRepository
                    .findById(sResponse.data.hostID)
                    .then((hResponse) => {
                        setSmestuvanje(prev => ({
                            ...prev,
                            "hostDTO":{
                                "name":hResponse.data.name,
                                "surname":hResponse.data.surname,
                                "countryDTO": {
                                    "id": "",
                                    "name": "",
                                    "continent": ""
                                }
                            }
                        }));
                        countryRepository
                            .findById(hResponse.data.countryID)
                            .then((cResponse) => {
                                setSmestuvanje(prev => {

                                })
                            })

                    })
            })
    })*/