import React, { useState } from "react";

// Define the interface for the props that the CreateQuery component receives
interface CreateQueryProps {
  onCreateQuery: (newQuery: { title: string; content: string }) => void; // Function to create a new query
}

// Define the CreateQuery component using the provided props
const CreateQuery: React.FC<CreateQueryProps> = ({ onCreateQuery }) => {
  // State variables to manage the title and content of the new query
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  // Function to handle the creation of a new query
  const handleCreateQuery = (e: React.FormEvent) => {
    e.preventDefault();
    // Invoke the callback function with the new query information
    onCreateQuery({ title, content });
  };

  // Render the CreateQuery component with input fields for title and content
  return (
    <div>
      {/* Display the title and content input fields */}
      <h2>Crear Consulta</h2>
      <form onSubmit={handleCreateQuery}>
        <label>
          Título:
          {/* Input field for the title */}
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
          {/* Textarea for the content */}
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </label>
        <br />
        {/* Button to submit the form and create the query */}
        <button type="submit">Crear Consulta</button>
      </form>
    </div>
  );
};

export default CreateQuery;
