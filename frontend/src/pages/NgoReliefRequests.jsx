import { useEffect, useState } from "react";

import {
    getAllReliefRequests,
    approveRequest,
    rejectRequest
} from "../services/ReliefService";

function NgoReliefRequests() {

    const [requests, setRequests] = useState([]);

    useEffect(() => {
        fetchRequests();
    }, []);

    const fetchRequests = async () => {
        try {
            const res = await getAllReliefRequests();
            setRequests(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    const handleApprove = async (id) => {
        try {
            await approveRequest(id);
            alert("Request Approved & Inventory Updated");
            fetchRequests();
        } catch (err) {
            console.log(err);
            alert("Approval failed");
        }
    };

    const handleReject = async (id) => {
        try {
            await rejectRequest(id);
            alert("Request Rejected");
            fetchRequests();
        } catch (err) {
            console.log(err);
            alert("Rejection failed");
        }
    };

    return (
        <div style={{ padding: "30px" }}>

            <h1>Relief Requests</h1>

            <button
                onClick={fetchRequests}
                style={{
                    marginBottom: "20px",
                    padding: "8px 15px"
                }}
            >
                Refresh
            </button>

            <table border="1" cellPadding="10" width="100%">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Shelter ID</th>
                        <th>Food</th>
                        <th>Water</th>
                        <th>Medicine</th>
                        <th>Blankets</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>

                    {requests.length === 0 ? (
                        <tr>
                            <td colSpan="8" style={{ textAlign: "center" }}>
                                No Requests Found
                            </td>
                        </tr>
                    ) : (
                        requests.map((req) => (
                            <tr key={req.id}>

                                <td>{req.id}</td>
                                <td>{req.shelterId}</td>
                                <td>{req.food}</td>
                                <td>{req.water}</td>
                                <td>{req.medicine}</td>
                                <td>{req.blankets}</td>
                                <td>{req.status}</td>

                                <td>

                                    <button
                                        onClick={() => handleApprove(req.id)}
                                        disabled={req.status !== "PENDING"}
                                        style={{
                                            background: "green",
                                            color: "white",
                                            marginRight: "10px"
                                        }}
                                    >
                                        Approve
                                    </button>

                                    <button
                                        onClick={() => handleReject(req.id)}
                                        disabled={req.status !== "PENDING"}
                                        style={{
                                            background: "red",
                                            color: "white"
                                        }}
                                    >
                                        Reject
                                    </button>

                                </td>

                            </tr>
                        ))
                    )}

                </tbody>

            </table>

        </div>
    );
}

export default NgoReliefRequests;