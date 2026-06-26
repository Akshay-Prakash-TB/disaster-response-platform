import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { getAvailableShelters } from "../services/ShelterService";
import { createReliefRequest } from "../services/ReliefService";

function CreateReliefRequest() {

    const navigate = useNavigate();

    const [shelters, setShelters] = useState([]);

    const [form, setForm] = useState({

        shelterId: "",

        food: "",

        water: "",

        medicine: "",

        blankets: ""

    });

    useEffect(() => {

        fetchShelters();

    }, []);

    const fetchShelters = async () => {

        try {

            const res =
                await getAvailableShelters();

            setShelters(res.data);

        } catch (err) {

            console.log(err);

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

            const request = {

                citizenId: Number(
                    sessionStorage.getItem("userId")
                ),

                shelterId: Number(form.shelterId),

                food: Number(form.food),

                water: Number(form.water),

                medicine: Number(form.medicine),

                blankets: Number(form.blankets)

            };

            await createReliefRequest(request);

            alert("Relief request submitted successfully.");

            navigate("/citizen");

        } catch (err) {

            console.log(err);

            alert("Unable to submit request.");

        }

    };

    return (

        <div style={styles.container}>

            <h2>Request Relief</h2>

            <form onSubmit={handleSubmit}>

                <select
                    name="shelterId"
                    value={form.shelterId}
                    onChange={handleChange}
                    style={styles.input}
                    required
                >

                    <option value="">
                        Select Shelter
                    </option>

                    {

                        shelters.map((shelter) => (

                            <option
                                key={shelter.id}
                                value={shelter.id}
                            >

                                {shelter.name}

                            </option>

                        ))

                    }

                </select>

                <input
                    type="number"
                    name="food"
                    placeholder="Food"
                    value={form.food}
                    onChange={handleChange}
                    style={styles.input}
                    required
                />

                <input
                    type="number"
                    name="water"
                    placeholder="Water"
                    value={form.water}
                    onChange={handleChange}
                    style={styles.input}
                    required
                />

                <input
                    type="number"
                    name="medicine"
                    placeholder="Medicine"
                    value={form.medicine}
                    onChange={handleChange}
                    style={styles.input}
                    required
                />

                <input
                    type="number"
                    name="blankets"
                    placeholder="Blankets"
                    value={form.blankets}
                    onChange={handleChange}
                    style={styles.input}
                    required
                />

                <div style={styles.buttons}>

                    <button
                        type="submit"
                        style={styles.submit}
                    >

                        Submit Request

                    </button>

                    <button
                        type="button"
                        style={styles.cancel}
                        onClick={() =>
                            navigate("/citizen")
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

        width: "600px",

        margin: "30px auto",

        padding: "20px",

        boxShadow: "0 0 10px gray"

    },

    input: {

        width: "100%",

        padding: "10px",

        marginBottom: "15px"

    },

    buttons: {

        display: "flex",

        gap: "15px"

    },

    submit: {

        padding: "10px 20px",

        background: "green",

        color: "white",

        border: "none",

        cursor: "pointer"

    },

    cancel: {

        padding: "10px 20px",

        background: "gray",

        color: "white",

        border: "none",

        cursor: "pointer"

    }

};

export default CreateReliefRequest;