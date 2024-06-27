import "../App.css";
import AppNavbar from "../components/AppNavbar";
import { Button, Container } from "reactstrap";
import { Link } from "react-router-dom";
import { useCookies } from "react-cookie";
import { useEffect, useState } from "react";

const Home = () => {

    const [authenticated, setAuthenticated] = useState(false);
    const [loading, setLoading] = useState(false);
    const [user, setUser] = useState(null);
    const [cookies] = useCookies(["XSRF-TOKEN"]);

    useEffect(() => {
        setLoading(true);

        fetch("/api/user", { credentials: "include" })
            .then((response) => response.text())
            .then((data) => {
                if (data === "") {
                    setAuthenticated(false);
                } else {
                    setUser(JSON.parse(data));
                    setAuthenticated(true);
                }

                setLoading(false);
            })
    }, [setAuthenticated, setLoading, setUser]);

    const login = () => {
        let port = (window.location.port ? ':' + window.location.port : '');
        if (port === ':3000') {
            port = ':8080';
        }
        window.location.href = `//${window.location.hostname}${port}/api/private`;
    };

    const logout = () => {
        fetch("/api/logout", { 
            method: "POST",
            credentials: "include",
            headers: {
                "X-XSRF-TOKEN": cookies["XSRF-TOKEN"],
            },
        })
        .then((res) => res.json())
        .then( response => {
            window.location.href = `${response.logoutUrl}?id_token_hint=${response.idToken}` 
            + `&post_logout_redirect_uri=${window.location.origin}`;
        })
    };

    const message = user ? <h2>Welcome, ${user.name}!</h2> :
        <p>Please login to manage JUG Tour</p>;

    const button = authenticated ?
        <div>
            <Button color="link"><Link to="/groups">Manage JUG Tour</Link></Button>
            <br />
            <Button color="link" onClick={logout}>Logout</Button>
        </div>
        :
        <Button color="link" onClick={login}>Login</Button>;

    if (loading) {
        return <p>Loading...</p>;
    }

  return (
    <div>
      <AppNavbar />
      <Container fluid>
        {message}
        {button}
      </Container>
    </div>
  );
};

export default Home;
