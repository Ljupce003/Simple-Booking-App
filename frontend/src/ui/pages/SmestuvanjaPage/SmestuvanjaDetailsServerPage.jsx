import React from 'react';
import {Box, CircularProgress} from "@mui/material";
import {useParams} from "react-router";
import useSmestuvanjeDetailsServer from "../../../hooks/smestuvanje/useSmestuvanjeDetailsServer.js";
import SmestuvanjeDetailServer from "../../components/smestuvanja/SmestuvanjeDetailServer/SmestuvanjeDetailServer.jsx";

const SmestuvanjaDetailsServerPage = () => {
    const {id} = useParams();
    const {smestuvanje,loading} = useSmestuvanjeDetailsServer(id);

    return (
        <Box className="products-box">
            {loading && (
                <Box className="progress-box">
                    <CircularProgress/>
                </Box>
            )}
            {!loading && <SmestuvanjeDetailServer SmestuvanjeDetailS={smestuvanje}/>}
        </Box>
    );
};

export default SmestuvanjaDetailsServerPage;