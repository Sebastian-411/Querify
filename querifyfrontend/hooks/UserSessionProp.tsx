import LogOut from "@/components/LoggedComponents/LogOut";
import QueryLayout from "@/components/LoggedComponents/QueryLayout";
import CreateQuery from "@/components/LoggedComponents/QueryProps";
import React, { useEffect, useState } from "react";
import router from "next/router";
import CreatePost from "@/components/LoggedComponents/Post/CreatePosts";
import ShowPosts from "@/components/LoggedComponents/Post/ShowPost";

function UserSessionProp() {
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [user, setUser] = useState<any>();

  useEffect(() => {
    const storedState = localStorage.getItem("isLoggedIn");
    const storedUser = localStorage.getItem("user");
    if (storedState && !isLoggedIn) {
      setLoggedIn(true);
    }
    if (storedUser && !user) {
      setUser(JSON.parse(storedUser));
    }
  }, [isLoggedIn, user]);

  const logOut = () => {
    setLoggedIn(false);
    localStorage.setItem("isLoggedIn", "false");
    localStorage.setItem("user", JSON.stringify({}));
    router.push("/SignUp");
  };

  return (
    <div>
      <LogOut logOut={logOut} />
      <QueryLayout />
    </div>
  );
}

export default UserSessionProp;
