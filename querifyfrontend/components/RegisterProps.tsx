import React, { useState } from 'react';

interface RegisterProps {
  onRegister: (usuario: string) => void;
}

const RegisterComponente: React.FC<RegisterProps> = ({ onRegister }) => {
  const [usuario, setUsuario] = useState('');

  const handleRegister = (e: React.FormEvent) => {
    e.preventDefault();
    onRegister(usuario);
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleRegister}>
        <label>
          Usuario:
          <input
            type="text"
            value={usuario}
            onChange={(e) => setUsuario(e.target.value)}
            required
          />
        </label>
        <br />
        <button type="submit">Registrarse</button>
      </form>
    </div>
  );
};

export default RegisterComponente;
