import { useNavigate } from "react-router-dom";

function NGODashboard() {

    const navigate = useNavigate();

    const logout = () => {

        sessionStorage.clear();
        navigate("/login");

    };

    return (

        <div style={styles.container}>

            <h1 style={styles.title}>NGO Dashboard</h1>

            <div style={styles.cardContainer}>

                {/* Shelter Management */}
                <div
                    style={styles.card}
                    onClick={() => navigate("/ngo/shelters")}
                >
                    <h2>🏠 Shelter Management</h2>
                    <p>
                        Manage shelters, capacity,
                        occupancy and availability.
                    </p>
                </div>

                {/* Relief Requests */}
                <div
                    style={styles.card}
                    onClick={() => navigate("/ngo/requests")}
                >
                    <h2>🆘 Relief Requests</h2>
                    <p>
                        Handle citizen requests for
                        food, rescue and shelter support.
                    </p>
                </div>

            </div>

            <button
                style={styles.logout}
                onClick={logout}
            >
                Logout
            </button>

        </div>

    );

}

const styles = {

    container: {

        padding: "50px",
        textAlign: "center",
        background: "#0f0f0f",
        minHeight: "100vh",
        color: "white"

    },

    title: {

        fontSize: "32px",
        marginBottom: "40px"

    },

    cardContainer: {

        display: "flex",
        justifyContent: "center",
        gap: "40px",
        flexWrap: "wrap"

    },

    card: {

        width: "280px",
        padding: "25px",
        background: "white",
        color: "black",
        borderRadius: "12px",
        boxShadow: "0 10px 25px rgba(0,0,0,0.3)",
        cursor: "pointer",
        transition: "0.3s"

    },

    logout: {

        marginTop: "50px",
        padding: "10px 25px",
        background: "red",
        color: "white",
        border: "none",
        cursor: "pointer",
        borderRadius: "6px"

    }

};

export default NGODashboard;