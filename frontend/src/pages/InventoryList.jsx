import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { getInventoryByShelter } from "../services/InventoryService";

function InventoryList() {

    const { shelterId } = useParams();
    const navigate = useNavigate();

    const [inventory, setInventory] = useState(null);

    useEffect(() => {
        fetchInventory();
    }, []);

    const fetchInventory = async () => {
        try {
            const res = await getInventoryByShelter(shelterId);
            setInventory(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div style={{ padding: "30px" }}>

            <h1>Inventory List</h1>
            <h3>Shelter ID: {shelterId}</h3>

            {/* CREATE BUTTON */}
            <button
                style={{
                    background: "green",
                    color: "white",
                    padding: "10px",
                    marginBottom: "20px"
                }}
                onClick={() =>
                    navigate(`/ngo/shelters/${shelterId}/inventory/create`)
                }
            >
                + Add Inventory
            </button>

            {/* TABLE */}
            {inventory ? (

                <table border="1" cellPadding="10" style={{ width: "100%" }}>

                    <thead>
                        <tr>
                            <th>Food</th>
                            <th>Water</th>
                            <th>Medicine</th>
                            <th>Blankets</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>{inventory.food}</td>
                            <td>{inventory.water}</td>
                            <td>{inventory.medicine}</td>
                            <td>{inventory.blankets}</td>

                            <td>

                                <button
                                    onClick={() =>
                                        navigate(
                                            `/ngo/shelters/${shelterId}/inventory/edit/${inventory.id}`
                                        )
                                    }
                                >
                                    Edit
                                </button>

                            </td>
                        </tr>
                    </tbody>

                </table>

            ) : (
                <p>No Inventory Found</p>
            )}

        </div>
    );
}

export default InventoryList;