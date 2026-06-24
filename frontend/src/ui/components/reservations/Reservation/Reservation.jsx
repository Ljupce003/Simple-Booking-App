import React from 'react';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import {Button, Card, CardActions, CardContent, TableCell, TableRow, Typography} from "@mui/material";

const Reservation = ({reservation,onReserve}) => {

    const handleConfirm = () => {
        onReserve(reservation.id);

    }

    return (
        <>
            <TableRow>
                <TableCell>{new Date(reservation.startDate).toLocaleDateString()}</TableCell>
                <TableCell>{new Date(reservation.endDate).toLocaleDateString()}</TableCell>
                <TableCell>{reservation.guestNum}</TableCell>
                <TableCell>{reservation.smestuvanjeName}</TableCell>
                <TableCell align="center">
                    {!reservation.confirmed && (
                        <Button
                            size="small"
                            color="info"
                            startIcon={<CheckCircleIcon/>}
                            onClick={handleConfirm}
                        >
                            Confirm
                        </Button>
                    )}
                </TableCell>
            </TableRow>

            {/*<Card sx={{boxShadow:3,borderRadius:2,p:1}}>*/}
            {/*    <CardContent >*/}
            {/*        <Typography variant="subtitle1">{Date.parse(reservation.startDate)}</Typography>*/}
            {/*        <Typography variant="subtitle1">{Date.parse(reservation.endDate)}</Typography>*/}
            {/*        <Typography variant="subtitle1" fontWeight="bold">Number of guests: {reservation.guestNum}</Typography>*/}
            {/*        <Typography variant="subtitle1" fontWeight="bold">{reservation.smestuvanjeName}</Typography>*/}
            {/*    </CardContent>*/}
            {/*    {reservation && !reservation.confirmed &&*/}
            {/*        <CardActions sx={{justifyContent: "space-between"}}>*/}
            {/*            <Button size="small" color="info" startIcon={<BackSpace/>} onClick={() => `Reservation is confirmed with id ${reservation.id}`}>Confirm</Button>*/}
            {/*        </CardActions>*/}
            {/*    }*/}
            {/*</Card>*/}
        </>
    );
};

export default Reservation;