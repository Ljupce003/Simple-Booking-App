import {useEffect, useState} from "react";
import AuthContext from "../context/authContext.js";
import React from "react";

const decode = (jwtToken) => {
    try {
        return JSON.parse(atob(jwtToken.split(".")[1]));
    }catch (err){
        console.log(err);
        return null;
    }
};

const initState = {"user":null, loading:true};
const nullState = {"user":null, loading:false};


const AuthProvider = ({children}) => {
    const [state,setState] = useState(initState);

    const login = (jwtToken) => {
        const payload = decode(jwtToken);
        if (payload){
            localStorage.setItem("token",jwtToken);
            setState({
                user: payload,
                loading: false
            });
        }
    };

    const logout = () => {
        const jwtToken = localStorage.getItem("token");
        if (jwtToken){
            localStorage.removeItem("token");
            setState(nullState)
        }
    }

    useEffect(() => {
        const jwtToken = localStorage.getItem("token");
        if (jwtToken){
            const payload = decode(jwtToken);
            if(payload){
                setState({user: payload,loading: false})
            }
            else {
                setState(nullState)
            }
        }
        else {
            setState(nullState)
        }
    },[])



    return (
        <AuthContext.Provider value={{login,logout,...state,isLoggedIn: !!state.user}}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;

