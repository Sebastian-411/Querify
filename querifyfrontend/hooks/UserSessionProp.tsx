// Import necessary components
import LogOut from "@/components/LoggedComponents/LogOut";
import QueryLayout from "@/components/LoggedComponents/Query/QueryLayout";
import CreateQuery from "@/components/LoggedComponents/Query/QueryProps";
import React, { useEffect, useState } from "react";
import router from "next/router";
import CreatePost from "@/components/LoggedComponents/Post/CreatePosts";
import ShowPosts from "@/components/LoggedComponents/Post/ShowPost";

// Define the component for the user session properties
function UserSessionProp() {
  // State variables to track login status and user information
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [user, setUser] = useState<any>();

  // Effect to check and update login status and user information
  useEffect(() => {
    // Check local storage for login status
    const storedState = localStorage.getItem("isLoggedIn");
    const storedUser = localStorage.getItem("user");

    // Update state based on stored values
    if (storedState && !isLoggedIn) {
      setLoggedIn(true);
    }
    if (storedUser && !user) {
      setUser(JSON.parse(storedUser));
    }
  }, [isLoggedIn, user]);

  // Function to handle user logout
  const logOut = () => {
    setLoggedIn(false); // Set login status to false
    localStorage.setItem("isLoggedIn", "false"); // Update local storage
    localStorage.setItem("user", JSON.stringify({}));
    router.push("/SignUp"); // Redirect to the sign-up page
  };

  // Render the user session properties
  return (
    <div>
      {/* Render the logout button */}
      <LogOut logOut={logOut} />

      {/* Render the QueryLayout component */}
      <QueryLayout />
    </div>
  );
}

export default UserSessionProp;
