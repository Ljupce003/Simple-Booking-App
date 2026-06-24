import React from 'react';
import {Grid} from "@mui/material";
import Smestuvanje from "../Smestuvanje/Smestuvanje.jsx";


const Smestuvanja = ({smestuvanja,onEdit,onDelete,onReserve}) => {
    return (
        <Grid container spacing={{xs:2,md:3}}>
            {smestuvanja.map((smestuvanje,id) => (
                <Grid key={id} size={{xs:12,sm:6,md:4}}>
                    <Smestuvanje
                        key={smestuvanje.id}
                        id={smestuvanje.id}
                        smestuvanje={smestuvanje}
                        onEdit={onEdit}
                        onDelete={onDelete}
                        onReserve={onReserve}
                    ></Smestuvanje>
                </Grid>
            ))}
        </Grid>
    );
};

export default Smestuvanja;