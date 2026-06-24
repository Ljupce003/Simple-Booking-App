import React from 'react';
import useCountriesDetails from "../../../hooks/countries/useCountriesDetails.js";
import {Box} from "@mui/material";

import {useParams} from "react-router";
import CountryDetails from "../../components/countries/CountryDetails/CountryDetails.jsx";

const CountriesDetailsPage = () => {
    const {id} = useParams();
    const countryDetails = useCountriesDetails(id);

    return (
        <Box className="products-box">
            {/*{loading && (*/}
            {/*    <Box className="progress-box">*/}
            {/*        <CircularProgress/>*/}
            {/*    </Box>*/}
            {/*)}*/}
            {/*{!loading && <Countries countries={countries}/>}*/}
            <CountryDetails country={countryDetails}/>
        </Box>
    );
};

export default CountriesDetailsPage;