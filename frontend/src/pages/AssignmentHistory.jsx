import { useEffect, useState } from "react";
import axios from "axios";

function AssignmentHistory() {

  const [assignments, setAssignments] =
    useState([]);

  const fetchAssignments =
    async () => {

      try {

        const response =
          await axios.get(
            "http://localhost:8080/assignment/all"
          );

        setAssignments(
          response.data
        );

      } catch (error) {

        console.error(error);
      }
    };

  useEffect(() => {

    fetchAssignments();

  }, []);

  return (
    <div style={{ padding: "20px" }}>

      <h2>
        Assignment History
      </h2>

      <table
        border="1"
        cellPadding="10"
      >

        <thead>
          <tr>

            <th>ID</th>

            <th>
              Incident ID
            </th>

            <th>
              Resource ID
            </th>

            <th>Status</th>

            <th>
              Assigned At
            </th>

            <th>
              Accepted At
            </th>

            <th>
              Completed At
            </th>

          </tr>
        </thead>

        <tbody>

          {assignments.map(
            (assignment) => (

              <tr
                key={
                  assignment.id
                }
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
                  {assignment.assignedAt
                    ? new Date(
                        assignment.assignedAt
                      ).toLocaleString()
                    : "-"}
                </td>

                <td>
                  {assignment.acceptedAt
                    ? new Date(
                        assignment.acceptedAt
                      ).toLocaleString()
                    : "-"}
                </td>

                <td>
                  {assignment.completedAt
                    ? new Date(
                        assignment.completedAt
                      ).toLocaleString()
                    : "-"}
                </td>

              </tr>
            )
          )}

        </tbody>

      </table>

    </div>
  );
}

export default AssignmentHistory;