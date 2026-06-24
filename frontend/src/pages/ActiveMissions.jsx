import { useEffect, useState } from "react";
import axios from "axios";

function ActiveMissions() {

  const [missions,
        setMissions] =
        useState([]);

  const fetchMissions =
    async () => {

      try {

        const response =
          await axios.get(
            "http://localhost:8080/assignment/active"
          );

        setMissions(
          response.data
        );

      } catch(error) {

        console.error(error);
      }
    };

  useEffect(() => {

    fetchMissions();

  }, []);

  return (

    <div style={{ padding: "20px" }}>

      <h2>
        Active Missions
      </h2>

      <table
        border="1"
        cellPadding="10"
      >

        <thead>

          <tr>

            <th>
              Assignment
            </th>

            <th>
              Incident
            </th>

            <th>
              Resource
            </th>

            <th>
              Rescue Team
            </th>

            <th>
              Status
            </th>

          </tr>

        </thead>

        <tbody>

          {missions.map(
            mission => (

              <tr
                key={
                  mission.assignmentId
                }
              >

                <td>
                  {
                    mission.assignmentId
                  }
                </td>

                <td>
                  {
                    mission.incidentTitle
                  }
                </td>

                <td>
                  {
                    mission.resourceName
                  }
                </td>

                <td>
                  {
                    mission.rescueTeam
                  }
                </td>

                <td>
                  {
                    mission.status
                  }
                </td>

              </tr>

            )
          )}

        </tbody>

      </table>

    </div>

  );
}

export default ActiveMissions;