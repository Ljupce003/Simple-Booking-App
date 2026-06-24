import React, {useState} from 'react';
import {useNavigate} from "react-router";
import userRepository from "../../../../repository/userRepository.js";
import {
    Box,
    Button,
    Container,
    FormControl,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    TextField,
    Typography
} from "@mui/material";

const innitFormData = {
    "username": "",
    "password": "",
    "repeatPassword": "",
    "name": "",
    "surname": "",
    "role": ""
}

const Register = () => {

    const navigate = useNavigate();

    const [formData,setFormData] = useState(innitFormData);

    const handleChange = (event) => {
        const {name,value} = event.target;
        setFormData({...formData, [name]:value});
    }

    const handleSubmit = () => {
        userRepository
            .login(formData)
            .then(() => {
                console.log("user successfully registered");
                setFormData(innitFormData);
                navigate("/login");
            })
            .catch(err => console.log(err));
    }

    return (
        <Container maxWidth="sm">
            <Paper elevation={3} sx={{padding: 4, mt: 4}}>
                <Typography variant="h5" align="center" gutterBottom>Register</Typography>
                <Box>
                    <TextField
                        fullWidth label="Name"
                        name="name"
                        margin="normal"
                        required
                        value={formData.name}
                        onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="Surname"
                        name="surname"
                        margin="normal"
                        required
                        value={formData.surname}
                        onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="Username"
                        name="username"
                        margin="normal"
                        required
                        value={formData.username}
                        onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="Password"
                        name="password"
                        type="password"
                        margin="normal"
                        required
                        value={formData.password}
                        onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="Repeat Password"
                        name="repeatPassword"
                        type="password"
                        margin="normal"
                        required
                        value={formData.repeatPassword}
                        onChange={handleChange}
                    />
                    <FormControl fullWidth margin="dense" required>
                        <InputLabel>Role</InputLabel>
                        <Select
                            name="role"
                            label="Role"
                            variant="outlined"
                            value={formData.role}
                            onChange={handleChange}
                        >
                            <MenuItem key="user" value="ROLE_USER">User</MenuItem>
                            <MenuItem key="admin" value="ROLE_ADMIN">Administrator</MenuItem>
                        </Select>
                    </FormControl>
                    <Button fullWidth variant="contained" type="submit" sx={{mt: 2}} onClick={handleSubmit}>
                        Register
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
};

export default Register;