import api from "./api";

// ---------------- CREATE ----------------
export const createInventory = (data) => {
    return api.post("/api/inventory", data);
};

// ---------------- GET ALL ----------------
export const getAllInventories = () => {
    return api.get("/api/inventory");
};

// ---------------- GET BY ID ----------------
export const getInventoryById = (id) => {
    return api.get(`/api/inventory/${id}`);
};

// ---------------- GET BY SHELTER ----------------
export const getInventoryByShelter = (shelterId) => {
    return api.get(`/api/inventory/shelter/${shelterId}`);
};

// ---------------- UPDATE ----------------
export const updateInventory = (id, data) => {
    return api.put(`/api/inventory/${id}`, data);
};

// ---------------- DELETE ----------------
export const deleteInventory = (id) => {
    return api.delete(`/api/inventory/${id}`);
};