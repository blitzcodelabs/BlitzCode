import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "@/firebase";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  //
  const navigate = useNavigate();

  const onLogin = async (values: { email: string; password: string }) => {
    signInWithEmailAndPassword(auth, values.email, values.password)
      .then((userCredential) => {
        // Signed in
        const user = userCredential.user;
        navigate("/home");
        console.log(user);
      })
      .catch((error) => {
        const errorCode = error.code;
        const errorMessage = error.message;
        console.log(errorCode, errorMessage);
      });
  };
  // @ts-ignore
  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          onLogin({ email: email, password: password });
        }}
      >
        <input
          type="text"
          className="input-field"
          placeholder={"email"}
          name={"email"}
          onChange={(e) => setEmail(e.target.value)}
          value={email}
        ></input>
        <br />
        <input
          type="password"
          className="input-field"
          placeholder={"password"}
          name={"password"}
          onChange={(e) => setPassword(e.target.value)}
          value={password}
        ></input>
        <br />
        <input type="submit" className="submit-button"></input>
      </form>
    </div>
  );
};

export default Login;
