
import './App.css'
import {BrowserRouter, Routes, Route} from "react-router";
import Layout from "./ui/components/layout/Layout/Layout.jsx";
import HomePage from "./ui/pages/HomePage/HomePage.jsx";
import CountriesPage from "./ui/pages/CountriesPage/CountriesPage.jsx";
import CountriesDetailsPage from "./ui/pages/CountriesPage/CountriesDetailsPage.jsx";
import HostsDetailsPage from "./ui/pages/HostsPage/HostsDetailsPage.jsx";
import HostsPage from "./ui/pages/HostsPage/HostsPage.jsx";
import SmestuvanjaPage from "./ui/pages/SmestuvanjaPage/SmestuvanjaPage.jsx";
import SmestuvanjaDetailsClientPage from "./ui/pages/SmestuvanjaPage/SmestuvanjaDetailsClientPage.jsx";
import SmestuvanjaDetailsServerPage from "./ui/pages/SmestuvanjaPage/SmestuvanjaDetailsServerPage.jsx";
import ProtectedRoute from "./ui/components/routing/ProtectedRoute/ProtectedRoute.jsx";
import Register from "./ui/components/auth/Register/Register.jsx";
import Login from "./ui/components/auth/Login/Login.jsx";
import ReservationPage from "./ui/pages/ReservationPage/ReservationPage.jsx";





const App = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/register" element={<Register/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<HomePage/>}/>
                    <Route path="countries" element={<CountriesPage/>}/>
                    <Route path="countries/:id" element={<CountriesDetailsPage/>}/>
                    <Route path="hosts/:id" element={<HostsDetailsPage/>}/>
                    <Route path="hosts" element={<HostsPage/>}/>
                    <Route path="smestuvanja/:id" element={<HostsDetailsPage/>}/>
                    <Route path="smestuvanja" element={<SmestuvanjaPage/>}/>
                    <Route element={<ProtectedRoute role={["ROLE_HOST"]}/>}>
                        <Route path="smestuvanjeDClient/:id" element={<SmestuvanjaDetailsClientPage/>}/>
                        <Route path="smestuvanjeDServer/:id" element={<SmestuvanjaDetailsServerPage/>}/>
                    </Route>
                    <Route element={<ProtectedRoute role={["ROLE_USER","ROLE_HOST"]}/>}>
                        <Route path="reservations" element={<ReservationPage/>}/>
                    </Route>


                </Route>
            </Routes>
        </BrowserRouter>
    )

};

export default App;
