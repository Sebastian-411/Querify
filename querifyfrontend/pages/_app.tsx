import { AppProps } from "next/app";
import router from "next/router"; // Import Next.js router
import { useEffect } from "react";

function MyApp({ Component, pageProps }: AppProps) {
  useEffect(() => {
    const isLoggedIn = localStorage.getItem("isLoggedIn");
    // eslint-disable-next-line react-hooks/rules-of-hooks

    // Redirigir a la página principal si está autenticado
    if (isLoggedIn === "true") {
      router.push("/");
    } else {
      router.push("/SignUp");
    }
  }, []);

  return <Component {...pageProps} />;
}

export default MyApp;
