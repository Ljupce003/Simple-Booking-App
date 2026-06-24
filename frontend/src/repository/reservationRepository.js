import axiosInstance from "../axios/axios.js";

const reservationRepository = {

    getReservations: async (data) => {
        return await axiosInstance.post("/reservation/userReservations",data)
    },

    confirmReserve: async (id) => {
        return await axiosInstance.put(`/user/confirmReserve/${id}`,null)
    },

    confirmReserveAll: async () => {
        return await axiosInstance.post(`/user/confirmReserve`,null)
    }

}
export default reservationRepository;