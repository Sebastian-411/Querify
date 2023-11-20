import React, { useState } from 'react';

interface CreateQueryProps {
  onCreateQuery: (newQuery: { title: string; content: string }) => void;
}

const CreateQuery: React.FC<CreateQueryProps> = ({ onCreateQuery }) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleCreateQuery = (e: React.FormEvent) => {
    e.preventDefault();
    onCreateQuery({ title, content });
  };

  return (
    <div>
      <h2>Crear Consulta</h2>
      <form onSubmit={handleCreateQuery}>
        <label>
          Título:
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </label>
        <br />
        <label>
          Contenido:
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </label>
        <br />
        <button type="submit">Crear Consulta</button>
      </form>
    </div>
  );
};

export default CreateQuery;
