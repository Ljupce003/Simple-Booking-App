import React from 'react';
import useAuth from "../../../../hooks/useAuth.js";
import {Navigate,Outlet} from "react-router";

const ProtectedRoute = ({role}) => {
    const {user,loading} = useAuth();

    if(loading) return null;

    if(user == null)
        return <Navigate to="/login" replace/>

    const acceptedRoles = Array.isArray(role) ? role : [role];
    const userRoles = Array.isArray(user.roles) ? user.roles : [user.roles];

    if(role){
        const isAuthorized = acceptedRoles.some(r => userRoles.includes(r));
        if (!isAuthorized)
            return <Navigate to="/login" replace/>
    }






    return (
        <Outlet/>
    );
};

export default ProtectedRoute;