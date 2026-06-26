import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    getSheltersByNgo,
    deleteShelter
} from "../services/ShelterService";

function ShelterManagement() {

    const navigate = useNavigate();

    const [shelters, setShelters] = useState([]);

    useEffect(() => {

        fetchShelters();

    }, []);

    const fetchShelters = async () => {

        try {

            const ngoId = Number(
                sessionStorage.getItem("userId")
            );

            const res = await getSheltersByNgo(
                ngoId
            );

            setShelters(res.data);

        } catch (err) {

            console.log(err);

        }

    };

    const handleDelete = async (id) => {

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this shelter?"
        );

        if (!confirmDelete) return;

        try {

            await deleteShelter(id);

            fetchShelters();

        } catch (err) {

            console.log(err);

        }

    };

    return (

        <div style={styles.container}>

            <div style={styles.header}>

                <h1>Shelter Management</h1>

                <button
                    style={styles.createButton}
                    onClick={() =>
                        navigate("/ngo/shelters/create")
                    }
                >
                    + Create Shelter
                </button>

            </div>

            <table
                border="1"
                cellPadding="10"
                style={styles.table}
            >

                <thead>

                    <tr>

                        <th>ID</th>

                        <th>Name</th>

                        <th>Capacity</th>

                        <th>Occupancy</th>

                        <th>Available</th>

                        <th>Status</th>

                        <th>Actions</th>

                    </tr>

                </thead>

                <tbody>

                    {

                        shelters.length === 0

                            ?

                            (

                                <tr>

                                    <td
                                        colSpan="7"
                                        style={{
                                            textAlign: "center"
                                        }}
                                    >

                                        No Shelters Available

                                    </td>

                                </tr>

                            )

                            :

                            shelters.map((shelter) => (

                                <tr key={shelter.id}>

                                    <td>{shelter.id}</td>

                                    <td>{shelter.name}</td>

                                    <td>{shelter.capacity}</td>

                                    <td>{shelter.currentOccupancy}</td>

                                    <td>

                                        {

                                            shelter.capacity -

                                            shelter.currentOccupancy

                                        }

                                    </td>

                                    <td>{shelter.status}</td>

                                    <td>

                                        <button
                                            onClick={() =>
                                                navigate(
                                                    `/ngo/shelters/edit/${shelter.id}`
                                                )
                                            }
                                        >
                                            Edit
                                        </button>

                                        <button
                                            style={{
                                                marginLeft: "10px",
                                                background: "red",
                                                color: "white"
                                            }}
                                            onClick={() =>
                                                handleDelete(
                                                    shelter.id
                                                )
                                            }
                                        >
                                            Delete
                                        </button>

                                        {/* ✅ ONLY ADDITION */}
                                        <button
                                            style={{
                                                marginLeft: "10px",
                                                background: "blue",
                                                color: "white"
                                            }}
                                            onClick={() =>
                                                navigate(`/ngo/shelters/${shelter.id}/inventory`)
                                            }
                                        >
                                            Inventory
                                        </button>

                                    </td>

                                </tr>

                            ))

                    }

                </tbody>

            </table>

        </div>

    );

}

const styles = {

    container: {

        padding: "30px"

    },

    header: {

        display: "flex",

        justifyContent: "space-between",

        alignItems: "center",

        marginBottom: "20px"

    },

    createButton: {

        padding: "10px 20px",

        cursor: "pointer",

        background: "green",

        color: "white",

        border: "none"

    },

    table: {

        width: "100%",

        borderCollapse: "collapse"

    }

};

export default ShelterManagement;