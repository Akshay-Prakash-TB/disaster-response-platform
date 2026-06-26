import { useEffect, useState } from "react";

import { getCitizenReliefRequests } from "../services/ReliefService";
import { getAllShelters } from "../services/ShelterService";

function MyReliefRequests() {

    const [requests, setRequests] = useState([]);
    const [shelters, setShelters] = useState({});

    useEffect(() => {

        fetchShelters();
        fetchRequests();

    }, []);

    const fetchShelters = async () => {

        try {

            const res = await getAllShelters();

            const shelterMap = {};

            res.data.forEach((shelter) => {

                shelterMap[shelter.id] = shelter.name;

            });

            setShelters(shelterMap);

        } catch (err) {

            console.log(err);

        }

    };

    const fetchRequests = async () => {

        try {

            const citizenId = Number(
                sessionStorage.getItem("userId")
            );

            const res =
                await getCitizenReliefRequests(citizenId);

            setRequests(res.data);

        } catch (err) {

            console.log(err);

        }

    };

    return (

        <div style={styles.container}>

            <h2>My Relief Requests</h2>

            {

                requests.length === 0 ?

                    (

                        <p>No relief requests found.</p>

                    )

                    :

                    (

                        <table
                            border="1"
                            cellPadding="10"
                            style={styles.table}
                        >

                            <thead>

                                <tr>

                                    <th>Shelter</th>

                                    <th>Food</th>

                                    <th>Water</th>

                                    <th>Medicine</th>

                                    <th>Blankets</th>

                                    <th>Status</th>

                                </tr>

                            </thead>

                            <tbody>

                                {

                                    requests.map((request) => (

                                        <tr key={request.id}>

                                            <td>

                                                {

                                                    shelters[request.shelterId] ||

                                                    "Unknown Shelter"

                                                }

                                            </td>

                                            <td>{request.food}</td>

                                            <td>{request.water}</td>

                                            <td>{request.medicine}</td>

                                            <td>{request.blankets}</td>

                                            <td>

                                                {

                                                    request.status === "PENDING"

                                                        ? "🟡 PENDING"

                                                        : request.status === "APPROVED"

                                                            ? "🟢 APPROVED"

                                                            : "🔴 REJECTED"

                                                }

                                            </td>

                                        </tr>

                                    ))

                                }

                            </tbody>

                        </table>

                    )

            }

        </div>

    );

}

const styles = {

    container: {

        padding: "30px"

    },

    table: {

        width: "100%",

        borderCollapse: "collapse"

    }

};

export default MyReliefRequests;