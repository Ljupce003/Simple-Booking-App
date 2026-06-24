import React, {useState} from 'react';
import InfoIcon from "@mui/icons-material/Info";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import AddIcon from "@mui/icons-material/Add";
import {Box,Button,Card,CardActions,CardContent,Typography} from "@mui/material";
import {useNavigate} from "react-router";
import EditSmestuvanjeDialog from "../editSmestuvanjeDialog/EditSmestuvanjeDialog.jsx";
import DeleteSmestuvanjeDialog from "../deleteSmestuvanjeDialog/DeleteSmestuvanjeDialog.jsx";

const Smestuvanje = ({smestuvanje,onEdit,onDelete,onReserve}) => {
    const navigate = useNavigate();
    const [editDialogOpen,setEditDialogOpen] = useState(false);
    const [deleteDialogOpen,setDeleteDialogOpen] = useState(false);

    function toReserve(){
        onReserve(smestuvanje.id)
    }


    function toDetails(id,version) {
        if(version===1) navigate(`/smestuvanjeDServer/${id}`);
        else navigate(`/smestuvanjeDClient/${id}`);
    }

    return (
        <>
            <Card sx={{boxShadow:3,borderRadius:2,p:1,width:"auto"}}>
                <CardContent >
                    <Typography variant="h5">{smestuvanje.name}</Typography>
                    <Typography variant="body1"
                                sx={{fontSize: "1.25rem"}}>{smestuvanje.category}</Typography>
                    <Typography variant="body1"
                                sx={{fontSize: "1.25rem"}}>{smestuvanje.numRooms}</Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<InfoIcon/>} onClick={() => toDetails(smestuvanje.id,1)}>Info</Button>
                    {/*<Button size="small" color="info" startIcon={<InfoIcon/>} onClick={() => toDetails(smestuvanje.id,2)}>Info Client</Button>*/}
                    <Box>
                        <Button size="small" color="warning" startIcon={<EditIcon/>} sx={{mr:"0.25rem"}} onClick={() => setEditDialogOpen(true)}>Edit</Button>
                        <Button size="small" color="error" startIcon={<DeleteIcon/>} onClick={() => setDeleteDialogOpen(true)}>Delete</Button>
                        <Button size="small" color="success" startIcon={<AddIcon/>} onClick={toReserve}>Reserve</Button>
                    </Box>
                </CardActions>
            </Card>
            <EditSmestuvanjeDialog
                onEdit={onEdit}
                smestuvanje={smestuvanje}
                onClose={() => setEditDialogOpen(false)}
                open={editDialogOpen}/>
            <DeleteSmestuvanjeDialog
                open={deleteDialogOpen}
                smestuvanje={smestuvanje}
                onDelete={onDelete}
                onClose={() => setDeleteDialogOpen(false)}
            />

        </>
    );
};

export default Smestuvanje;