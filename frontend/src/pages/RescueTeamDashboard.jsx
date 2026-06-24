import { useEffect, useState } from "react";
import axios from "axios";

function RescueTeamDashboard() {

const [missions, setMissions] =
useState([]);

const fetchMissions =
async () => {
  try {

    const userId =
      sessionStorage.getItem(
        "userId"
      );

    console.log(
      "User ID:",
      userId
    );

    const response =
      await axios.get(
        `http://localhost:8080/assignment/user/${userId}`
      );

    console.log(
      "Missions:",
      response.data
    );

    setMissions(
      response.data
    );

  } catch (error) {

    console.error(error);
  }
};

const acceptMission =
async (id) => {
  try {

    await axios.put(
      `http://localhost:8080/assignment/${id}/accept`
    );

    fetchMissions();

  } catch (error) {

    console.error(error);
  }
};

const completeMission =
async (id) => {
  try {

    await axios.put(
      `http://localhost:8080/assignment/${id}/complete`
    );

    fetchMissions();

  } catch (error) {

    console.error(error);
  }
};

useEffect(() => {

fetchMissions();

}, []);

return (
<div style={{ padding: "20px" }}>

  <h2>
    Rescue Team Dashboard
  </h2>

  {missions.length === 0 ? (

    <p>
      No missions assigned.
    </p>

  ) : (

    missions.map(
      (mission) => (

        <div
          key={
            mission.assignmentId
          }
          style={{
            border:
              "1px solid gray",
            padding:
              "15px",
            marginBottom:
              "15px",
            borderRadius:
              "8px"
          }}
        >

          <h3>
            {mission.title}
          </h3>

          <p>
            {mission.description}
          </p>

          <p>
            Severity:
            {" "}
            {mission.severity}
          </p>

          <p>
            Citizen:
            {" "}
            {mission.citizenName}
          </p>

          <p>
            Email:
            {" "}
            {mission.citizenEmail}
          </p>

          <p>
            Incident ID:
            {" "}
            {mission.incidentId}
          </p>

          <p>
            Latitude:
            {" "}
            {mission.latitude}
          </p>

          <p>
            Longitude:
            {" "}
            {mission.longitude}
          </p>

          <p>
            Status:
            {" "}
            {mission.assignmentStatus}
          </p>

          {mission.assignmentStatus ===
            "ASSIGNED" && (

            <button
              onClick={() =>
                acceptMission(
                  mission.assignmentId
                )
              }
            >
              Accept Mission
            </button>

          )}

          {mission.assignmentStatus ===
            "IN_PROGRESS" && (

            <button
              onClick={() =>
                completeMission(
                  mission.assignmentId
                )
              }
            >
              Complete Mission
            </button>

          )}

        </div>

      )
    )

  )}

</div>
);
}

export default RescueTeamDashboard;