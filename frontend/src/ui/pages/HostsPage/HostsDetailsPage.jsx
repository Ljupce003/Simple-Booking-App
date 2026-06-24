import React from 'react';
import {Box} from "@mui/material";
import {useParams} from "react-router";
import HostDetails from "../../components/hosts/HostDetails/HostDetails.jsx";
import useHostDetails from "../../../hooks/hosts/UseHostDetails.js";

const HostsDetailsPage = () => {
    const {id} = useParams();
    const hostDetail = useHostDetails(id);
    return (
        <Box className="products-box">

            <HostDetails hostDetail={hostDetail}/>
        </Box>
    );
};

export default HostsDetailsPage;