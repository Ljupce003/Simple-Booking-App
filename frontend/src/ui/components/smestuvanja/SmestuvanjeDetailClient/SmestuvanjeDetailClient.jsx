import React from 'react';
import {useNavigate} from "react-router";
import BackSpace from "@mui/icons-material/Backspace"
import {Button,Card,CardActions,CardContent,Typography} from "@mui/material";

const SmestuvanjeDetailClient = ({smestuvanjeDetailC}) => {

    const navigate = useNavigate();

    return (
        <>
            <Card sx={{boxShadow:3,borderRadius:2,p:1}}>
                <CardContent>
                    <Typography variant="h5">{smestuvanjeDetailC.smestuvanje.name}</Typography>
                    <Typography variant="body1"
                                sx={{fontSize: "1.25rem"}}>{smestuvanjeDetailC.smestuvanje.category}</Typography>
                    <Typography variant="body1"
                                sx={{fontSize: "1.25rem"}}>{smestuvanjeDetailC.smestuvanje.numRooms}</Typography>

                    <CardContent>
                        <Typography variant="h5">{smestuvanjeDetailC.host.name}</Typography>
                        <Typography variant="body1"
                                    sx={{fontSize: "1.25rem"}}>{smestuvanjeDetailC.host.surname}</Typography>

                        <CardContent>
                            <Typography variant="h5">{smestuvanjeDetailC.country.name}</Typography>
                            <Typography variant="body1"
                                        sx={{fontSize: "1.25rem"}}>{smestuvanjeDetailC.country.continent}</Typography>
                        </CardContent>

                    </CardContent>

                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<BackSpace/>} onClick={() => navigate("/smestuvanja")}>Back</Button>
                </CardActions>

            </Card>

        </>
    );
};

export default SmestuvanjeDetailClient;


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