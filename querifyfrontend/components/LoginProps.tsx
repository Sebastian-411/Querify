import React, { useState } from 'react';

// Define the interface for the props that the LoginProps component receives
interface LoginProps {
  onLogin: (username: string) => void;  // Function to handle user login
}

// Define the LoginProps component using the provided props
const LoginProps: React.FC<LoginProps> = ({ onLogin }) => {
  // State variable to track the entered username
  const [username, setUsername] = useState('');

  // Function to handle the login form submission
  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();  // Prevent the default form submission behavior
    onLogin(username);  // Call the provided onLogin function with the entered username
  };

  // Render the LoginProps component
  return (
    <div>
      {/* Login form */}
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
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

        {/* Submit button for user login */}
        <button type="submit">Iniciar sesión</button>
      </form>
    </div>
  );
};

export default LoginProps;
