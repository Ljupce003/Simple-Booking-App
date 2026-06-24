import React from 'react';
import useReservations from "../../../hooks/useReservations.js";
import useAuth from "../../../hooks/useAuth.js";
import {Box, Button, CircularProgress} from "@mui/material";
import Reservations from "../../components/reservations/Reservations/Reservations.jsx";


const ReservationPage = () => {

    const {user} = useAuth();


    const {reservations,loading,confirmReserve,confirmReserveAll,} = useReservations();



    return (
        <Box className="products-box">
            {loading && (
                <Box className="progress-box">
                    <CircularProgress/>
                </Box>
            )}
            {!loading &&
                <>
                    <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                        <Button variant="contained" color="primary" onClick={() => confirmReserveAll()}>
                            Confirm All
                        </Button>
                    </Box>
                    <Reservations reservations={reservations} onReserve={confirmReserve}/>
                </>
            }
        </Box>
    );
};

export default ReservationPage;