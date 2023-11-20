import React, { useState } from "react";

interface CreatePostProps {
  queries: any[]; // Reemplaza 'any[]' con el tipo de tus queries
  onCreatePost: (queryId: string, autorComment: string) => void;
}

const CreatePost: React.FC<CreatePostProps> = ({ queries, onCreatePost }) => {
  const [content, setContent] = useState("");
  const [selectedQuery, setSelectedQuery] = useState("");

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const handleQueryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedQuery(e.target.value);
  };

  const handleCreatePost = (e: React.FormEvent) => {
    e.preventDefault();

    // Validar que se ingresen ambos campos antes de crear el post
    if (content.trim() === "" || selectedQuery.trim() === "") {
      alert("Por favor, complete todos los campos antes de crear el post.");
      return;
    }

    // Llamar a la función onCreatePost con los valores necesarios
    onCreatePost(selectedQuery, content);

    // Limpiar los campos después de crear el post
    setContent("");
    setSelectedQuery("");
  };

  return (
    <div>
      <h2>Crear Nuevo Post</h2>
      <form onSubmit={handleCreatePost}>
        <br />
        <label>
          Contenido:
          <textarea value={content} onChange={handleContentChange} required />
        </label>
        <br />
        <label>
          Seleccionar Query:
          <select value={selectedQuery} onChange={handleQueryChange} required>
            <option value="" disabled>
              Seleccione un Query
            </option>
            {queries.map((query) => (
              <option key={query.id} value={query.id}>
                {query.title}
              </option>
            ))}
          </select>
        </label>
        <br />
        <button type="submit">Crear Post</button>
      </form>
    </div>
  );
};

export default CreatePost;
