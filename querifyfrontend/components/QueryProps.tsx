import React, { useState } from "react";

interface CreateQueryProps {
  onCreateQuery: (newQuery: { title: string; content: string }) => void;
}

const CreateQuery: React.FC<CreateQueryProps> = ({ onCreateQuery }) => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const handleCreateQuery = (e: React.FormEvent) => {
    e.preventDefault();

    // Validar que se ingresen ambos campos antes de crear la consulta
    if (title.trim() === "" || content.trim() === "") {
      alert(
        "Por favor, ingrese tanto el título como el contenido de la consulta."
      );
      return;
    }

    // Crear objeto de consulta
    const newQuery = {
      title: title,
      content: content,
    };

    // Llamar a la función proporcionada para crear la consulta
    onCreateQuery(newQuery);

    // Limpiar los campos después de crear la consulta
    setTitle("");
    setContent("");
  };

  return (
    <div>
      <h2>Crear Nueva Consulta</h2>
      <form onSubmit={handleCreateQuery}>
        <label>
          Título:
          <input
            type="text"
            value={title}
            onChange={handleTitleChange}
            required
          />
        </label>
        <br />
        <label>
          Contenido:
          <textarea value={content} onChange={handleContentChange} required />
        </label>
        <br />
        <button type="submit">Crear Consulta</button>
      </form>
    </div>
  );
};

export default CreateQuery;
