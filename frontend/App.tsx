import router from 'Frontend/routes';
import { RouterProvider } from 'react-router-dom';
import "Frontend/globals.css"

export default function App() {
  return <RouterProvider router={router} />;
}
