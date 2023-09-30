import "@frontend/globals.css"
import {Route, Routes} from "react-router-dom";
import { BrowserRouter as Router} from 'react-router-dom';
import Signup from "@frontend/pages/Signup";
import Login from "@frontend/pages/Login";

function Home() {
  return <h1>Hello World, Rubik Font Test2</h1>
}

export default function App() {
    return (
        <div>
            <h1>Welcome</h1>
            <a href={"login"}>Login</a>
            <br/>
            <a href={"signup"}>Signup</a>
            <br/>
            <a href={"home"}>Home</a>
            <Router>
                <div>
                    <section>
                        <Routes>
                            <Route path="/signup" element={<Signup/>}/>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/home" element={<Home/>}/>
                        </Routes>
                    </section>
                </div>
            </Router>
        </div>
        );
}
