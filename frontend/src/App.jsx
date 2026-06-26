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
import CitizenTrackingMap from "./pages/CitizenTrackingMap";
import ActiveMissions from "./pages/ActiveMissions";
import NGODashboard from "./pages/NGODashboard";
import ShelterManagement from "./pages/ShelterManagement";
import CreateShelter from "./pages/CreateShelter";
import EditShelter from "./pages/EditShelter";
import InventoryList from "./pages/InventoryList";
import InventoryForm from "./pages/InventoryForm";
import NgoReliefRequests from "./pages/NgoReliefRequests";
import CreateReliefRequest from "./pages/CreateReliefRequest";
import MyReliefRequests from "./pages/MyReliefRequests";

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
        <Route path="/citizen-tracking" element={<CitizenTrackingMap />} />
        <Route path="/admin/missions" element={<ActiveMissions />} />
        <Route path="/ngo" element={<NGODashboard/>} />
        <Route path="/ngo/shelters" element={<ShelterManagement/>} />
        <Route path="/ngo/shelters/create" element={<CreateShelter/>} />
        <Route path="/ngo/shelters/edit/:id" element={<EditShelter/>} />
        <Route path="/ngo/shelters/:shelterId/inventory" element={<InventoryList />} />
        <Route path="/ngo/shelters/:shelterId/inventory/create" element={<InventoryForm />} />
        <Route path="/ngo/shelters/:shelterId/inventory/edit/:inventoryId" element={<InventoryForm />} />
        <Route path="/ngo/requests" element={<NgoReliefRequests />} />
        <Route path="/citizen/relief-request" element={<CreateReliefRequest />} />
        <Route path="/citizen/relief-requests" element={<MyReliefRequests />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;