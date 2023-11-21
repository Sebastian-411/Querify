import React, { useState } from "react";

// Define the interface for the props that the RegisterProps component receives
interface RegisterProps {
  onRegister: (username: string) => void; // Function to handle user registration
}

// Define the RegisterProps component using the provided props
const RegisterProps: React.FC<RegisterProps> = ({ onRegister }) => {
  // State variable to track the entered username
  const [username, setUsername] = useState("");

  // Function to handle the registration form submission
  const handleRegister = (e: React.FormEvent) => {
    e.preventDefault(); // Prevent the default form submission behavior
    onRegister(username); // Call the provided onRegister function with the entered username
  };

  // Render the RegisterProps component
  return (
    <div>
      {/* Registration form */}
      <h2>Registro</h2>
      <form onSubmit={handleRegister}>
        {/* Input for entering the username */}
        <label>
          Usuario:
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </label>
        <br />

        {/* Submit button for user registration */}
        <button type="submit">Registrarse</button>
      </form>
    </div>
  );
};

export default RegisterProps;
