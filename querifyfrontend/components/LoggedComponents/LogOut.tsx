import React from "react";

interface LogOutProps {
  logOut: () => void;
}

const LogOut: React.FC<LogOutProps> = ({ logOut }) => {
  return (
    <div>
      <button onClick={logOut}>LOG OUT</button>
    </div>
  );
};

export default LogOut;
