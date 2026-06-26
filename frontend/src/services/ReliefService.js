import api from "./api";

// ---------------- CREATE ----------------
export const createReliefRequest = (data) => {
    return api.post("/api/relief", data);
};

// ---------------- GET ALL ----------------
export const getAllReliefRequests = () => {
    return api.get("/api/relief");
};

// ---------------- APPROVE ----------------
export const approveRequest = (id) => {
    return api.put(`/api/relief/approve/${id}`);
};

// ---------------- REJECT ----------------
export const rejectRequest = (id) => {
    return api.put(`/api/relief/reject/${id}`);
};

export const getCitizenReliefRequests = (citizenId) => {
    return api.get(`/api/relief/citizen/${citizenId}`);
};