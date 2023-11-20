import React, { useState } from 'react';

interface RegisterProps {
  onRegister: (username: string) => void;
}

const RegisterProps: React.FC<RegisterProps> = ({ onRegister }) => {
  const [username, setUsername] = useState('');

  const handleRegister = (e: React.FormEvent) => {
    e.preventDefault();
    onRegister(username);
  };

  return (
    <div>
      <h2>Registro</h2>
      <form onSubmit={handleRegister}>
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
        <button type="submit">Registrarse</button>
      </form>
    </div>
  );
};

export default RegisterProps;
