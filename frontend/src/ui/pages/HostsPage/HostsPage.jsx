import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import useHosts from "../../../hooks/hosts/useHosts.js";
import Hosts from "../../components/hosts/Hosts/Hosts.jsx";
import AddHostDialog from "../../components/hosts/addHostDialog/AddHostDialog.jsx";

const HostsPage = () => {
    const {hosts,loading,onEdit,onDelete,onAdd} = useHosts();
    const [addHostDialogOpen,setHostDialogOpen] = useState(false)
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
                        <Button variant="contained" color="primary" onClick={() => setHostDialogOpen(true)}>
                            Add Country
                        </Button>
                    </Box>
                    <Hosts hosts={hosts} onEdit={onEdit} onDelete={onDelete}/>
                </>
            }
            <AddHostDialog
                open={addHostDialogOpen}
                onClose={() => setHostDialogOpen(false)}
                onAdd={onAdd}
            />
        </Box>
    );
};

export default HostsPage;