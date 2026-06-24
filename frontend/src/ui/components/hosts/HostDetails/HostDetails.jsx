import React from 'react';
import {useNavigate} from "react-router";
import BackSpace from "@mui/icons-material/Backspace"
import {Button,Card,CardActions,CardContent,Typography} from "@mui/material";


// {
//     "name": "string",
//     "surname": "string",
//     "countryID": 9007199254740991
// }

const HostDetails = ({hostDetail}) => {
    const navigate = useNavigate();

    return (
        <>
            <Card sx={{boxShadow:3,borderRadius:2,p:1}}>
                <CardContent >
                    <Typography variant="body1">{hostDetail.name}</Typography>
                    <Typography variant="body1" fontWeight="bold">{hostDetail.surname}</Typography>
                    <CardContent>
                        <Typography variant="subtitle1">{hostDetail.country.name}</Typography>
                        <Typography variant="subtitle1" fontWeight="bold"
                                    sx={{fontSize: "1rem"}}>{hostDetail.country.continent}</Typography>
                    </CardContent>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<BackSpace/>} onClick={() => navigate("/hosts")}>Back</Button>
                </CardActions>
            </Card>
        </>
    );
};

export default HostDetails;