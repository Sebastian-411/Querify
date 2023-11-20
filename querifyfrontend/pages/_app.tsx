import { AppProps } from "next/app";
import router, { useRouter } from "next/router";
import { useEffect } from "react";

function MyApp({ Component, pageProps }: AppProps) {
  useEffect(() => {
    const isLoggedIn = localStorage.getItem("isLoggedIn");

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
