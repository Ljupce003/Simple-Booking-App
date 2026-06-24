import BackSpace from "@mui/icons-material/Backspace"
import {Button,Card,CardActions,CardContent,Typography} from "@mui/material";
import {useNavigate} from "react-router";

const CountryDetails = ({country}) => {
    const navigate = useNavigate();


    return (
        <>
            <Card sx={{boxShadow:3,borderRadius:2,p:1}}>
                <CardContent >
                    <Typography variant="h5">{country.name}</Typography>
                    <Typography variant="body1" fontWeight="bold"
                                sx={{fontSize: "1.25rem"}}>{country.continent}</Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                        <Button size="small" color="info" startIcon={<BackSpace/>} onClick={() => navigate("/countries")}>Back</Button>
                </CardActions>
            </Card>
        </>
    );
};

export default CountryDetails;