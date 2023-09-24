import router from '@frontend/routes';
import { RouterProvider } from 'react-router-dom';
import "globals.css"

export default function App() {
  return <RouterProvider router={router} />;
}
