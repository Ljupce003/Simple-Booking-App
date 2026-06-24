import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import useCountries from "../../../hooks/countries/useCountries.js";
import Countries from "../../components/countries/Countries/Countries.jsx";
import AddCountryDialog from "../../components/countries/addCountryDialog/AddCountryDialog.jsx";

const CountriesPage = () => {
    const {countries, loading,onAdd,onEdit,onDelete} = useCountries();
    const [addCountryDialogOpen,setAddCountryDialogOpen] = useState(false);


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
                        <Button variant="contained" color="primary" onClick={() => setAddCountryDialogOpen(true)}>
                            Add Country
                        </Button>
                    </Box>
                    <Countries countries={countries} onEdit={onEdit} onDelete={onDelete}/>
                </>
            }
            <AddCountryDialog
                open={addCountryDialogOpen}
                onClose={() => setAddCountryDialogOpen(false)}
                onAdd={onAdd}
            />
        </Box>
    );
};

export default CountriesPage;

