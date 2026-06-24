import React, {useState} from 'react';
import {
    Button, Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    TextField
} from "@mui/material";
import useHosts from "../../../../hooks/hosts/useHosts.js";
import useSmestuvanjeCategories from "../../../../hooks/useSmestuvanjeCategories.js";



const EditSmestuvanjeDialog = ({open,onClose,onEdit,smestuvanje}) => {
    const [formData,setFormData] = useState(smestuvanje)
    const {hosts} = useHosts();
    const categories = useSmestuvanjeCategories()



    function handleChange(event){
        const {name,value} = event.target;
        setFormData({...formData,[name]:value});
    }

    function handleSubmit(){
        onEdit(smestuvanje.id,formData);
        setFormData(formData);
        onClose();
    }



    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Edit Product</DialogTitle>
            <DialogContent>
                <TextField
                    margin="dense"
                    label="Name"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    fullWidth
                />
                <TextField
                    margin="dense"
                    label="numRooms"
                    name="numRooms"
                    value={formData.numRooms}
                    onChange={handleChange}
                    fullWidth
                />
                <FormControl fullWidth margin="dense">
                    <InputLabel>Category</InputLabel>
                    <Select
                        name="category"
                        value={formData.category}
                        onChange={handleChange}
                        label="country"
                        variant="outlined">
                        {categories.map((category) => (
                            <MenuItem key={category} value={category}>{category}</MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <FormControl fullWidth margin="dense">
                    <InputLabel>Host</InputLabel>
                    <Select
                        name="hostID"
                        value={formData.hostID}
                        onChange={handleChange}
                        label="Host"
                        variant="outlined">
                        {hosts.map((host,id) => (
                            <MenuItem key={id} value={host.id}>{host.name}</MenuItem>
                        ))}
                    </Select>
                </FormControl>


            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSubmit} variant="contained" color="primary">Edit</Button>
            </DialogActions>
        </Dialog>
    );
};

export default EditSmestuvanjeDialog;