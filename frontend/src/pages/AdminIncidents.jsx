import { useEffect, useState } from "react";
import axios from "axios";

function AdminIncidents() {
  const [incidents, setIncidents] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");

  const fetchIncidents = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/incident/all"
      );

      setIncidents(response.data);
    } catch (error) {
      console.error("Error fetching incidents:", error);
    }
  };

  const searchIncidents = async () => {
  try {
    if (searchKeyword.trim() === "") {
      fetchIncidents();
      return;
    }

    const response = await axios.get(
      `http://localhost:8080/incident/search?keyword=${searchKeyword}`
    );

    setIncidents(response.data);
  } catch (error) {
    console.error(error);
  }
  };

  const filterIncidents = async (status) => {
        try {
            if (status === "ALL") {
            fetchIncidents();
            return;
            }

            const response = await axios.get(
            `http://localhost:8080/incident/filter?status=${status}`
            );

            setIncidents(response.data);
        } catch (error) {
            console.error(error);
        }
    };

  const updateStatus = async (id, status) => {
    try {
      await axios.put(
        `http://localhost:8080/incident/${id}/status?status=${status}`
      );

      fetchIncidents();
    } catch (error) {
      console.error("Error updating status:", error);
    }
  };

  useEffect(() => {
    fetchIncidents();
  }, []);

  return (
    <div>
      <h2>All Incidents</h2>

        <div style={{ marginBottom: "20px" }}>
        <input
            type="text"
            placeholder="Search by title..."
            value={searchKeyword}
            onChange={(e) => setSearchKeyword(e.target.value)}
        />

        <button
            onClick={searchIncidents}
            style={{ marginLeft: "10px" }}
        >
        Search
        </button>

        <button
            onClick={fetchIncidents}
            style={{ marginLeft: "10px" }}
        >
        Reset
        </button>
        </div>

        <div style={{ marginBottom: "20px" }}>
            <label>Status Filter: </label>

            <select
                value={statusFilter}
                onChange={(e) => {
                const status = e.target.value;
                setStatusFilter(status);
                filterIncidents(status);
                }}
            >
                <option value="ALL">All</option>
                <option value="OPEN">OPEN</option>
                <option value="ASSIGNED">ASSIGNED</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="RESOLVED">RESOLVED</option>
            </select>
        </div>

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Severity</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {incidents.map((incident) => (
            <tr key={incident.id}>
              <td>{incident.id}</td>
              <td>{incident.title}</td>
              <td>{incident.description}</td>
              <td>{incident.severity}</td>
              <td>{incident.status}</td>

            <td>
            <button
                onClick={() =>
                updateStatus(incident.id, "ASSIGNED")
                }
            >
                Assign
            </button>

            <button
                onClick={() =>
                updateStatus(incident.id, "IN_PROGRESS")
                }
                style={{ marginLeft: "10px" }}
            >
                Start
            </button>

            <button
                onClick={() =>
                updateStatus(incident.id, "RESOLVED")
                }
                style={{ marginLeft: "10px" }}
            >
                Resolve
            </button>
            </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AdminIncidents;