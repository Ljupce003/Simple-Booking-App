import React from 'react';
import {Grid} from "@mui/material";
import Host from "../Host/Host.jsx";

const Hosts = ({hosts,onEdit,onDelete}) => {
    return (
        <Grid container spacing={{xs:2,md:3}}>
            {hosts.map((host,id) => (
                <Grid key={id} size={{xs:12,sm:6,md:4,lg:3}}>
                    <Host
                        key={id}
                        id={id}
                        host={host}
                        onEdit={onEdit}
                        onDelete={onDelete}
                    ></Host>
                </Grid>
            ))}
        </Grid>
    );
};

export default Hosts;