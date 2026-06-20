import { useEffect, useState } from "react";
import axios from "axios";

function RescueTeamDashboard() {

  const [assignments,
        setAssignments] =
        useState([]);

  const fetchAssignments =
    async () => {

      try {

        const response =
          await axios.get(
            "http://localhost:8080/assignment/assigned"
          );

        setAssignments(
          response.data
        );

      } catch(error) {
        console.error(error);
      }
  };

  const acceptMission =
    async (id) => {

      try {

        await axios.put(
          `http://localhost:8080/assignment/${id}/accept`
        );

        fetchAssignments();

      } catch(error) {
        console.error(error);
      }
  };

  const completeMission =
    async (id) => {

      try {

        await axios.put(
          `http://localhost:8080/assignment/${id}/complete`
        );

        fetchAssignments();

      } catch(error) {
        console.error(error);
      }
  };

  useEffect(() => {
    fetchAssignments();
  }, []);

  return (
    <div>

      <h2>
        Rescue Team Dashboard
      </h2>

      <table
        border="1"
        cellPadding="10"
      >

        <thead>
          <tr>
            <th>
              Assignment ID
            </th>

            <th>
              Incident ID
            </th>

            <th>
              Resource ID
            </th>

            <th>
              Status
            </th>

            <th>
              Actions
            </th>
          </tr>
        </thead>

        <tbody>

          {assignments.map(
            (assignment) => (

            <tr
              key={assignment.id}
            >

              <td>
                {assignment.id}
              </td>

              <td>
                {assignment.incidentId}
              </td>

              <td>
                {assignment.resourceId}
              </td>

              <td>
                {assignment.status}
              </td>

              <td>

                {assignment.status ===
                    "ASSIGNED" && (

                    <button
                        onClick={() =>
                        acceptMission(
                            assignment.id
                        )
                        }
                    >
                        Accept
                    </button>
                )}

                {assignment.status ===
                    "IN_PROGRESS" && (

                    <button
                        onClick={() =>
                        completeMission(
                            assignment.id
                        )
                        }
                    >
                        Complete
                    </button>
                )}

              </td>

            </tr>

          ))}

        </tbody>

      </table>

    </div>
  );
}

export default RescueTeamDashboard;