import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
    apiKey: "AIzaSyDPOuD9AOqSMWrebWKuVvgfzgIqATVXSAQ",
    authDomain: "blitzcodeapp.firebaseapp.com",
    projectId: "blitzcodeapp",
    storageBucket: "blitzcodeapp.appspot.com",
    messagingSenderId: "188013902392",
    appId: "1:188013902392:web:a2865d327471e8088ac4cd",
    measurementId: "G-0DQFFEMSV3"
};

const app = initializeApp(firebaseConfig);
// @ts-ignore
export const auth = getAuth(app);
export default app