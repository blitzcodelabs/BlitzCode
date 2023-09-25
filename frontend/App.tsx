import router from '@frontend/routes';
import { RouterProvider } from 'react-router-dom';
import "@frontend/globals.css"

export default function App() {
  return <RouterProvider router={router} />;
}
