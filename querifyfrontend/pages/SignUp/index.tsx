// Import necessary components
import LoginProps from "@/components/LoginProps";
import RegisterProps from "@/components/RegisterProps";
import axios from "axios"; // Import the axios library for making HTTP requests
import router from "next/router"; // Import Next.js router
import React from "react";

// Define the main page component
function index() {
  // Function to handle login
  const handleLogin = async (username: string) => {
    try {
      // Make a POST request to the login endpoint
      const response = await axios.post(
        "http://localhost:8080/api/users/login",
        {
          user: username,
        }
      );

      // Save user information to local storage
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("user", JSON.stringify(response.data));

      // Redirect to the main page only if the login was successful
      router.push("/");
    } catch (error: any) {
      if (error.response && error.response.status === 401) {
        alert("El usuario no se encuentra registrado");
      }
    }
  };

  // Function to handle user registration
  const handleRegistro = async (username: string) => {
    try {
      // Make a POST request to the registration endpoint
      const response = await axios.post("http://localhost:8080/api/users", {
        user: username,
      });

      // Save user information to local storage
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("user", JSON.stringify(response.data));

      // Redirect to the main page only if the registration was successful
      router.push("/");
    } catch (error: any) {
      console.log(error); // Handle errors by logging them to the console
    }
  };

  // Render the login and registration components
  return (
    <div>
      <LoginProps onLogin={handleLogin} />
      <RegisterProps onRegister={handleRegistro} />
    </div>
  );
}

export default index;
