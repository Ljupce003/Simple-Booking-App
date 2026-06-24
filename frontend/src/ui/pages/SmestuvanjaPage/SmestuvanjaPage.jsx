import React, {useState} from 'react';
import useSmestuvanje from "../../../hooks/smestuvanje/useSmestuvanje.js";
import {Box, Button, CircularProgress} from "@mui/material";
import Smestuvanja from "../../components/smestuvanja/Smestauvanja/Smestuvanja.jsx";
import AddSmestuvanjeDialog from "../../components/smestuvanja/addSmestuvanjeDialog/AddSmestuvanjeDialog.jsx";

const SmestuvanjaPage = () => {
    const {smestuvanja,onDelete,onEdit,onAdd,loading,onReserve} = useSmestuvanje()
    const [addDialogOpen,setAddDialog] = useState(false);

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
                        <Button variant="contained" color="primary" onClick={() => setAddDialog(true)}>
                            Add Smestuvanje
                        </Button>
                    </Box>
                    <Smestuvanja smestuvanja={smestuvanja} onEdit={onEdit} onDelete={onDelete} onReserve={onReserve} />
                </>
            }
            <AddSmestuvanjeDialog
                
                open={addDialogOpen}
                onClose={() => setAddDialog(false)}
                onAdd={onAdd}
            />
        </Box>
    );
};

export default SmestuvanjaPage;