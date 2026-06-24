import React from 'react';
import {useNavigate} from "react-router";
import BackSpace from "@mui/icons-material/Backspace"
import {Button,Card,CardActions,CardContent,Typography} from "@mui/material";

const SmestuvanjeDetailServer = ({SmestuvanjeDetailS}) => {
    const navigate = useNavigate();

    return (
        <>
            <Card sx={{boxShadow:3,borderRadius:2,p:1}}>
                <CardContent>
                    <Typography variant="h5">{SmestuvanjeDetailS.name}</Typography>
                    <Typography variant="body1"
                                sx={{fontSize: "1.25rem"}}>{SmestuvanjeDetailS.category}</Typography>
                    <Typography variant="body1"
                                sx={{fontSize: "1.25rem"}}>{SmestuvanjeDetailS.numRooms}</Typography>

                    { SmestuvanjeDetailS.hostDTO!=null &&
                        <CardContent>
                            <Typography variant="h5">{SmestuvanjeDetailS.hostDTO.name}</Typography>
                            <Typography variant="body1"
                                        sx={{fontSize: "1.25rem"}}>{SmestuvanjeDetailS.hostDTO.surname}</Typography>

                            {SmestuvanjeDetailS.hostDTO.countryDTO!=null &&
                                <CardContent>
                                    <Typography variant="h5">{SmestuvanjeDetailS.hostDTO.countryDTO.name}</Typography>
                                    <Typography variant="body1"
                                                sx={{fontSize: "1.25rem"}}>{SmestuvanjeDetailS.hostDTO.countryDTO.continent}</Typography>
                                </CardContent>
                            }


                        </CardContent>
                    }


                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<BackSpace/>} onClick={() => navigate("/smestuvanja")}>Back</Button>
                </CardActions>

            </Card>

        </>
    );
};

export default SmestuvanjeDetailServer;



// {
//     "name": "Stan",
//     "category": "FLAT",
//     "hostDTO": {
//         "name": "Peter",
//         "surname": "Griffin",
//         "countryDTO": {
//              "id": 1,
//             "name": "Macedonia",
//             "continent": "Europe"
//     }
// },
//     "numRooms": 4
// }