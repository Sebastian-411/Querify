import LoginProps from "@/components/LoginProps";
import RegisterProps from "@/components/RegisterProps";
import axios from "axios";
import router from "next/router";
import React from "react";

function index() {
  const handleLogin = async (username: string) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/login",
        {
          user: username,
        }
      );

      // Guardar en el almacenamiento local
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("user", JSON.stringify(response.data));

      // Redirigir solo si el inicio de sesión fue exitoso
      router.push("/");
    } catch (error: any) {
      console.log(error);
    }
  };

  const handleRegistro = async (username: string) => {
    try {
      const response = await axios.post("http://localhost:8080/api/users", {
        user: username,
      });

      // Guardar en el almacenamiento local
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("user", JSON.stringify(response.data));

      // Redirigir solo si el registro fue exitoso
      router.push("/");
    } catch (error: any) {
      console.log(error);
    }
  };
  return (
    <div>
      <LoginProps onLogin={handleLogin} />
      <RegisterProps onRegister={handleRegistro} />
    </div>
  );
}

export default index;
