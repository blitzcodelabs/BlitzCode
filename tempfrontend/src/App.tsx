// import "@/globals.css";
// import { Route, Routes } from "react-router-dom";
// import { BrowserRouter } from 'react-router-dom';
// import Signup from "@/pages/Signup";
// import Login from "@/pages/Login";

// function Home() {
//   return <h1>Hello World!</h1>
// }

// export default function App() {
//   return (
//     <div>
//       <h1>Welcome</h1>
//       <a href={"login"}>Login</a>
//       <a href={"signup"}>Signup</a>
//       <a href={"home"}>Home</a>
//       <BrowserRouter>
//         <div>
//           <section>
//             <Routes>
//               <Route path="/signup" element={<Signup />} />
//               <Route path="/login" element={<Login />} />
//               <Route path="/home" element={<Home />} />
//             </Routes>
//           </section>
//         </div>
//       </BrowserRouter>
//     </div>
//   );
// }

import "@/globals.css";
import Home from "@/pages/home"

export default function App() {
  return <Home />;
}
