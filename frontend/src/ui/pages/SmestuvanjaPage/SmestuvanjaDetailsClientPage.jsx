import React from 'react';
import {useParams} from "react-router";
import useSmestuvanjeDetailsClient from "../../../hooks/smestuvanje/useSmestuvanjeDetailsClient.js";
import {Box, CircularProgress} from "@mui/material";
import SmestuvanjeDetailClient from "../../components/smestuvanja/SmestuvanjeDetailClient/SmestuvanjeDetailClient.jsx";

const SmestuvanjaDetailsClientPage = () => {
    const {id} = useParams();
    const {smestuvanjeDetail,loading} =useSmestuvanjeDetailsClient(id);

    return (
        <Box className="products-box">
            {loading && (
                <Box className="progress-box">
                    <CircularProgress/>
                </Box>
            )}
            {!loading && (<SmestuvanjeDetailClient smestuvanjeDetailC={smestuvanjeDetail}/>)}
        </Box>
    );
};

export default SmestuvanjaDetailsClientPage;