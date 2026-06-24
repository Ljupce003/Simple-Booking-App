import axiosInstance from "../axios/axios.js";

const smestuvanjeRepository = {
    findAll: async () => {
        return await axiosInstance.get("/smestuvanje");
    },
    findById: async (id) => {
        return await axiosInstance.get(`/smestuvanje/${id}`);
    },
    add: async (data) => {
        return await axiosInstance.post("/smestuvanje/add",data);
    },
    edit: async (id,data) => {
        return await axiosInstance.put(`/smestuvanje/edit/${id}`,data);
    },
    delete: async (id) => {
        return await axiosInstance.delete(`/smestuvanje/delete/${id}`);
    },
    fetchById: async (id) => {
        return await axiosInstance.get(`/smestuvanje/fetch-by-id/${id}`)
    },
    categories: async () => {
        return await axiosInstance.get("/smestuvanje/categories");
    },
    reserve: async (id) => {
        return await axiosInstance.post(`/smestuvanje/reserve/${id}`);
    }
}

export default smestuvanjeRepository;