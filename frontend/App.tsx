import router from '@frontend/routes.js';
import { RouterProvider } from 'react-router-dom';

export default function App() {
  return <RouterProvider router={router} />;
}
