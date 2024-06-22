import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import GroupList from "./components/GroupList";
import GroupEdit from "./components/GroupEditForm";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/groups" element={<GroupList />} />
        <Route path="/groups/:id" element={<GroupEdit />} />
      </Routes>
    </Router>
  );
};

export default App;
