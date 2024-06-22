import "../App.css";
import AppNavbar from "../components/AppNavbar";
import { Button, Container } from "reactstrap";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div>
      <AppNavbar />
      <Container fluid>
        <Button color="link">
          <Link to="/groups">Manage JUG Tour</Link>
        </Button>
      </Container>
    </div>
  );
};

export default Home;
