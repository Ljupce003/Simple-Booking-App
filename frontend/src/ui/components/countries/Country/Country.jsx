import React, {useState} from "react";
import InfoIcon from "@mui/icons-material/Info";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import {Box,Button,Card,CardActions,CardContent,Typography} from "@mui/material";
import {useNavigate} from "react-router";
import EditCountryDialog from "../editCountryDialog/editCountryDialog.jsx";
import DeleteCountryDialog from "../deleteCountryDialog/deleteCountryDialog.jsx";

const Country = ({country,onEdit,onDelete}) => {
    const navigate = useNavigate();
    const [editDialogOpen,setEditDialogOpen] = useState(false);
    const [deleteDialogOpen,setDeleteDialogOpen] = useState(false);




    function toDetails(id){
        navigate(`/countries/${id}`);
    }


    return (
        <>
            <Card sx={{boxShadow:3,borderRadius:2,p:1}}>
                <CardContent >
                    <Typography variant="h5">{country.name}</Typography>
                    <Typography variant="body1" fontWeight="bold"
                                sx={{fontSize: "1.25rem"}}>{country.continent}</Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<InfoIcon/>} onClick={() => toDetails(country.id)}>Info</Button>
                    <Box>
                        <Button size="small" color="warning" startIcon={<EditIcon/>} sx={{mr:"0.25rem"}} onClick={() => setEditDialogOpen(true)}>Edit</Button>
                        <Button size="small" color="error" startIcon={<DeleteIcon/>} onClick={() => setDeleteDialogOpen(true)}>Delete</Button>
                    </Box>
                </CardActions>
            </Card>
            <EditCountryDialog
                open={editDialogOpen}
                onClose={() => setEditDialogOpen(false)}
                country={country}
                onEdit={onEdit}
            />
            <DeleteCountryDialog
                open={deleteDialogOpen}
                onClose={() => setDeleteDialogOpen(false)}
                country={country}
                onDelete={onDelete}
            />
        </>
    );
};

export default Country;