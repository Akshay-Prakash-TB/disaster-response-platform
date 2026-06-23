import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import IncidentReport from "./pages/IncidentReport";
import AdminIncidents from "./pages/AdminIncidents";
import Resources from "./pages/Resources";
import IncidentMap from "./pages/IncidentMap";
import RescueTeamDashboard from "./pages/RescueTeamDashboard";
import CitizenDashboard from "./pages/CitizenDashboard";
import AssignmentHistory from "./pages/AssignmentHistory";
import VehicleTrackingMap from "./pages/VehicleTrackingMap";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/incident" element={<IncidentReport />} />
        <Route path="/admin/incidents" element={<AdminIncidents />} />
        <Route path="/resources" element={<Resources />} />
        <Route path="/map" element={<IncidentMap />} />
        <Route path="/rescue" element={<RescueTeamDashboard />} />
        <Route path="/citizen" element={<CitizenDashboard />} />
        <Route path="/assignments" element={<AssignmentHistory />} />
        <Route path="/tracking" element={<VehicleTrackingMap />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;