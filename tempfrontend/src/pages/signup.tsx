import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth } from "@/firebase";

const Signup = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const onSubmitSignupForm = async (values: {
    email: string;
    password: string;
  }) => {
    console.log(email);
    console.log(password);
    await createUserWithEmailAndPassword(auth, values.email, values.password)
      .then((userCredential) => {
        const user = userCredential.user;
        console.log(user);
        navigate("/");
      })
      .catch((error) => {
        console.log(error.code, error.message);
      });
  };
  // @ts-ignore
  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          onSubmitSignupForm({ email: email, password: password });
        }}
      >
        <input
          type="text"
          className="input-field"
          placeholder={"email"}
          name={"email"}
          onChange={(e) => setEmail(e.target.value)}
          value={email}
        />
        <br />
        <input
          type="password"
          className="input-field"
          placeholder={"password"}
          name={"password"}
          onChange={(e) => setPassword(e.target.value)}
          value={password}
        />
        <br />
        <input type="submit" className="submit-button"></input>
      </form>
    </div>
  );
};

export default Signup;
