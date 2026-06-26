import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
    createInventory,
    updateInventory
} from "../services/InventoryService";

function InventoryForm() {

    const { shelterId, inventoryId } = useParams();
    const navigate = useNavigate();

    const isEdit = Boolean(inventoryId);

    const [form, setForm] = useState({
        food: "",
        water: "",
        medicine: "",
        blankets: ""
    });

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async () => {

        try {

            const payload = {
                shelterId: Number(shelterId),
                ...form
            };

            if (isEdit) {
                await updateInventory(inventoryId, payload);
            } else {
                await createInventory(payload);
            }

            alert("Saved successfully");

            navigate(`/ngo/shelters/${shelterId}/inventory`);

        } catch (err) {
            console.log(err);
            alert("Error saving inventory");
        }
    };

    return (
        <div style={{ padding: "30px" }}>

            <h1>{isEdit ? "Edit Inventory" : "Create Inventory"}</h1>

            <div style={{ display: "flex", flexDirection: "column", width: "300px", gap: "10px" }}>

                <input name="food" placeholder="Food" onChange={handleChange} />
                <input name="water" placeholder="Water" onChange={handleChange} />
                <input name="medicine" placeholder="Medicine" onChange={handleChange} />
                <input name="blankets" placeholder="Blankets" onChange={handleChange} />

                <button onClick={handleSubmit}>
                    {isEdit ? "Update" : "Create"}
                </button>

                <button
                    onClick={() =>
                        navigate(`/ngo/shelters/${shelterId}/inventory`)
                    }
                >
                    Back
                </button>

            </div>

        </div>
    );
}

export default InventoryForm;