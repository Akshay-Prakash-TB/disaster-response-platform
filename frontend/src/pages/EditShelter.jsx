import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import MapPicker from "../components/MapPicker";

import {
    getShelterById,
    updateShelter
} from "../services/ShelterService";

function EditShelter() {

    const navigate = useNavigate();

    const { id } = useParams();

    const [form, setForm] = useState({

        name: "",

        latitude: "",

        longitude: "",

        capacity: "",

        currentOccupancy: "",

        status: "OPEN"

    });

    useEffect(() => {

        fetchShelter();

    }, []);

    const fetchShelter = async () => {

        try {

            const res = await getShelterById(id);

            setForm({

                name: res.data.name,

                latitude: res.data.latitude,

                longitude: res.data.longitude,

                capacity: res.data.capacity,

                currentOccupancy: res.data.currentOccupancy,

                status: res.data.status

            });

        } catch (err) {

            console.log(err);

            alert("Unable to load shelter.");

        }

    };

    const handleChange = (e) => {

        setForm({

            ...form,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const updatedShelter = {

                ...form,

                ngoId: Number(
                    sessionStorage.getItem("userId")
                )

            };

            await updateShelter(id, updatedShelter);

            alert("Shelter updated successfully.");

            navigate("/ngo/shelters");

        } catch (err) {

            console.log(err);

            alert("Unable to update shelter.");

        }

    };

    return (

        <div style={styles.container}>

            <h2>Edit Shelter</h2>

            <form onSubmit={handleSubmit}>

                <input
                    type="text"
                    name="name"
                    value={form.name}
                    onChange={handleChange}
                    placeholder="Shelter Name"
                    style={styles.input}
                    required
                />

                <input
                    type="number"
                    name="capacity"
                    value={form.capacity}
                    onChange={handleChange}
                    placeholder="Capacity"
                    style={styles.input}
                    required
                />

                <input
                    type="number"
                    name="currentOccupancy"
                    value={form.currentOccupancy}
                    onChange={handleChange}
                    placeholder="Current Occupancy"
                    style={styles.input}
                    required
                />

                <select
                    name="status"
                    value={form.status}
                    onChange={handleChange}
                    style={styles.input}
                >

                    <option value="OPEN">
                        OPEN
                    </option>

                    <option value="FULL">
                        FULL
                    </option>

                    <option value="CLOSED">
                        CLOSED
                    </option>

                </select>

                <h3>Select Shelter Location</h3>

                <MapPicker
                    latitude={form.latitude}
                    longitude={form.longitude}
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

                    {form.latitude}

                </p>

                <p>

                    <strong>Longitude :</strong>

                    {form.longitude}

                </p>

                <div style={styles.buttonContainer}>

                    <button
                        type="submit"
                        style={styles.updateButton}
                    >

                        Update Shelter

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

        display: "flex",

        gap: "15px",

        marginTop: "20px"

    },

    updateButton: {

        padding: "10px 20px",

        background: "blue",

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

export default EditShelter;