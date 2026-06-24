import React, {useState} from "react";
import InfoIcon from "@mui/icons-material/Info";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import {Box,Button,Card,CardActions,CardContent,Typography} from "@mui/material";
import {useNavigate} from "react-router";
import EditHostDialog from "../editHostDialog/EditHostDialog.jsx";
import DeleteHostDialog from "../deleteHostDialog/DeleteHostDialog.jsx";

const Host = ({host,onEdit,onDelete}) => {
    const [editDialogOpen,setEditDialogOpen] = useState(false)
    const [deleteDialogOpen,setDeleteDialogOpen] = useState(false)
    const navigate = useNavigate();

    function toDetails(id){
        navigate(`/hosts/${id}`);
    }

    return (
        <>
            <Card sx={{boxShadow:3,borderRadius:2,p:1}}>
                <CardContent >
                    <Typography variant="h5">{host.name}</Typography>
                    <Typography variant="body1"
                                sx={{fontSize: "1.25rem"}}>{host.surname}</Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<InfoIcon/>} onClick={() => toDetails(host.id)}>Info</Button>
                    <Box>
                        <Button size="small" color="warning" startIcon={<EditIcon/>} sx={{mr:"0.25rem"}} onClick={() => setEditDialogOpen(true)}>Edit</Button>
                        <Button size="small" color="error" startIcon={<DeleteIcon/>} onClick={() => setDeleteDialogOpen(true)}>Delete</Button>
                    </Box>
                </CardActions>
            </Card>

            <EditHostDialog open={editDialogOpen}
            onClose={() => setEditDialogOpen(false)}
            onEdit={onEdit}
            host={host}/>
            <DeleteHostDialog
            open={deleteDialogOpen}
            onClose={() => setDeleteDialogOpen(false)}
            onDelete={onDelete}
            host={host}
            />
        </>

    );
};

export default Host;