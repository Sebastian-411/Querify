import Login from "@/components/LoginProps";
import CreateQuery from "@/components/QueryProps";
import RegisterComponente from "@/components/RegisterProps";
import "@/styles/globals.css";
import axios, { AxiosError } from "axios";
import React, { useEffect, useState } from "react";

export default function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [errorLogin, setErrorLogin] = useState<boolean>(false);
  const [errorRegister, setErrorRegister] = useState<boolean>(false);
  const [datos, setDatos] = useState<any[]>([]);
  const [user, setUser] = useState<any>();
  const [queries, setQueries] = useState<any[]>([]);

  // Usar useEffect para cargar el estado desde localStorage al montar el componente
  useEffect(() => {
    const storedState = localStorage.getItem("isLoggedIn");
    const storedUser = localStorage.getItem("user");
    if (storedState) {
      setLoggedIn(JSON.parse(storedState));
    }
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []); // El segundo parámetro vacío asegura que el efecto se ejecute solo una vez al montar el componente

  useEffect(() => {
    // Definir la función asíncrona para hacer la solicitud a la API
    const fetchData = async () => {
      try {
        // Realizar la solicitud GET a la API
        const response = await axios.get("http://localhost:8080/api/users");
        // Actualizar el estado con los datos de la respuesta
        setDatos(response.data);
      } catch (error) {
        console.error("Error al obtener datos de la API", error);
      }
    };

    // Llamar a la función para realizar la solicitud cuando el componente se monte
    fetchData();
  }, []); // El segundo parámetro vacío asegura que el efecto se ejecute solo una vez al montar el componente

  useEffect(() => {
    // Definir la función asíncrona para hacer la solicitud a la API
    const fetchData = async () => {
      try {
        // Realizar la solicitud GET a la API
        const response = await axios.get(
          `http://localhost:8080/api/users/${user?.id}/queries`
        );
        // Actualizar el estado con los datos de la respuesta
        setQueries(response.data);
      } catch (error) {
        console.error("Error al obtener datos de la API", error);
      }
    };

    // Llamar a la función para realizar la solicitud cuando el componente se monte
    fetchData();
  }, [user?.id]); // El segundo parámetro vacío asegura que el efecto se ejecute solo una vez al montar el componente

  const handleLogin = async (username: string) => {
    try {
      // Realizar la solicitud POST a la API
      const response = await axios.post(
        "http://localhost:8080/api/users/login",
        {
          user: username,
        }
      );
      setLoggedIn(true);
      setErrorLogin(false);
      setUser(response.data);
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("user", JSON.stringify(response.data));
    } catch (error: any) {
      if (error?.code === AxiosError.ERR_BAD_REQUEST) {
        setErrorLogin(true);
      }
    }
  };

  const handleRegistro = async (username: string) => {
    try {
      // Realizar la solicitud POST a la API
      const response = await axios.post("http://localhost:8080/api/users", {
        user: username,
      });
      setLoggedIn(true);
      setErrorRegister(false);
      setUser(response.data);
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("user", JSON.stringify(response.data));
    } catch (error: any) {
      console.log(error);
    }
  };

  const handleCreateQuery = async (newQuery: {
    title: string;
    content: string;
  }) => {
    try {
      console.log({ ...newQuery, user: { id: user?.id } });
      const response = await axios.post(`http://localhost:8080/api/queries`, {
        ...newQuery,
        user: { id: user?.id },
      });
    } catch (error) {
      console.log(error);
    }
  };

  const logOut = () => {
    setLoggedIn(false);
    localStorage.setItem("isLoggedIn", "false");
    localStorage.setItem("user", JSON.stringify({}));
  };

  return (
    <div>
      {datos.length > 0 ? (
        <ul>
          {datos.map((item) => (
            <li key={item.id}>{item.user}</li>
          ))}
        </ul>
      ) : (
        <p>Cargando datos...</p>
      )}
      <br />
      <br />
      <br />
      <br />
      {isLoggedIn ? (
        <>
          <p>{user?.user}</p>
          <button onClick={logOut}>AQUI</button>
          <br />
          <br />
          <br />
          <br />
          <CreateQuery onCreateQuery={handleCreateQuery} />
          <br />
          <br />
          <br />
          <br />
          {queries.length > 0 ? (
            <div>
              <ul>
                {queries.map((item) => (
                  <li key={item.id}>{item.title}</li>
                ))}
              </ul>
              <br />
              <br />
              <br />
            </div>
          ) : (
            <p>Cargando queries...</p>
          )}
        </>
      ) : (
        <>
          <Login onLogin={handleLogin} />
          {errorLogin ? <p>El usuario no existe</p> : <></>}
          <br />
          <br />
          <br />
          <br />
          <RegisterComponente onRegister={handleRegistro} />
          {errorRegister ? <p>El usuario ya existe</p> : <></>}
        </>
      )}
    </div>
  );
}
