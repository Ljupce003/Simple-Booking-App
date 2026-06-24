import  {useEffect, useState} from 'react';
import smestuvanjeRepository from "../repository/smestuvanjeRepository.js";

const UseSmestuvanjeCategories = () => {
    const [categories,setCategories] = useState([])



    useEffect(()=> {
        smestuvanjeRepository
            .categories()
            .then((response) => {
                setCategories(response.data);
            })
    },[])

    return categories;
};

export default UseSmestuvanjeCategories;