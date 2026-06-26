import api from "./api";

export const getAllShelters = async () => {
    return await api.get("/shelter/all");
};

export const getShelterById = async (id) => {
    return await api.get(`/shelter/${id}`);
};

export const addShelter = async (shelter) => {
    return await api.post("/shelter/add", shelter);
};

export const updateShelter = async (id, shelter) => {
    return await api.put(`/shelter/update/${id}`, shelter);
};

export const deleteShelter = async (id) => {
    return await api.post(`/shelter/delete/${id}`);
};

export const getSheltersByNgo = (ngoId) => {
    return api.get(`/shelter/ngo/${ngoId}`);
};

export const getAvailableShelters = () => {
    return api.get("/shelter/available");
};