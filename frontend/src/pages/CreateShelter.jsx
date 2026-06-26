import { useState } from "react";
import { useNavigate } from "react-router-dom";

import MapPicker from "../components/MapPicker";
import { addShelter } from "../services/ShelterService";

function CreateShelter() {

    const navigate = useNavigate();

    const [form, setForm] = useState({

        name: "",

        latitude: "",

        longitude: "",

        capacity: "",

        currentOccupancy: "",

        status: "OPEN"

    });

    const handleChange = (e) => {

        setForm({

            ...form,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const shelter = {

                ...form,

                ngoId: Number(sessionStorage.getItem("userId"))

            };

            await addShelter(shelter);

            alert("Shelter created successfully.");

            navigate("/ngo/shelters");

        } catch (err) {

            console.log(err);

            alert("Unable to create shelter.");

        }

    };

    return (

        <div style={styles.container}>

            <h2>Create Shelter</h2>

            <form onSubmit={handleSubmit}>

                <input
                    type="text"
                    name="name"
                    placeholder="Shelter Name"
                    value={form.name}
                    onChange={handleChange}
                    style={styles.input}
                    required
                />

                <input
                    type="number"
                    name="capacity"
                    placeholder="Capacity"
                    value={form.capacity}
                    onChange={handleChange}
                    style={styles.input}
                    required
                />

                <input
                    type="number"
                    name="currentOccupancy"
                    placeholder="Current Occupancy"
                    value={form.currentOccupancy}
                    onChange={handleChange}
                    style={styles.input}
                    required
                />

                <select
                    name="status"
                    value={form.status}
                    onChange={handleChange}
                    style={styles.input}
                >

                    <option value="OPEN">OPEN</option>

                    <option value="FULL">FULL</option>

                    <option value="CLOSED">CLOSED</option>

                </select>

                <h3>Select Shelter Location</h3>

                <MapPicker
                    setCoordinates={(coords) =>
                        setForm({
                            ...form,
                            latitude: coords.latitude,
                            longitude: coords.longitude
                        })
                    }
                />

                <p>

                    <strong>Latitude :</strong>

                    {form.latitude || " Not Selected"}

                </p>

                <p>

                    <strong>Longitude :</strong>

                    {form.longitude || " Not Selected"}

                </p>

                <div style={styles.buttonContainer}>

                    <button
                        type="submit"
                        style={styles.saveButton}
                    >

                        Create Shelter

                    </button>

                    <button
                        type="button"
                        style={styles.cancelButton}
                        onClick={() =>
                            navigate("/ngo/shelters")
                        }
                    >

                        Cancel

                    </button>

                </div>

            </form>

        </div>

    );

}

const styles = {

    container: {

        width: "650px",

        margin: "30px auto",

        padding: "20px",

        boxShadow: "0 0 10px gray"

    },

    input: {

        width: "100%",

        padding: "10px",

        marginBottom: "15px"

    },

    buttonContainer: {

        marginTop: "20px",

        display: "flex",

        gap: "15px"

    },

    saveButton: {

        padding: "10px 20px",

        background: "green",

        color: "white",

        border: "none",

        cursor: "pointer"

    },

    cancelButton: {

        padding: "10px 20px",

        background: "gray",

        color: "white",

        border: "none",

        cursor: "pointer"

    }

};

export default CreateShelter;