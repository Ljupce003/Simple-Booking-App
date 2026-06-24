import React from 'react';
import {Grid} from "@mui/material";
import Host from "../../hosts/Host/Host.jsx";
import Reservation from "../Reservation/Reservation.jsx";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';

const Reservations = ({reservations,onReserve}) => {
    return (

        <TableContainer component={Paper} sx={{ mt: 3 }}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Start Date</TableCell>
                        <TableCell>End Date</TableCell>
                        <TableCell>Guests</TableCell>
                        <TableCell>Smestuvanje</TableCell>
                        <TableCell align="center"></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {reservations.map((reservation) => (
                        <Reservation key={reservation.id} reservation={reservation} onReserve={onReserve} />
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        // <Grid container spacing={{xs:2,md:3}}>
        //     {reservations.map((reservation) => (
        //         <Grid key={reservation.id} size={{xs:12,sm:6,md:4,lg:3}}>
        //             <Reservation
        //                 key={reservation.id}
        //                 id={reservation.id}
        //                 reservation={reservation}
        //             ></Reservation>
        //         </Grid>
        //     ))}
        // </Grid>
    );
};

export default Reservations;