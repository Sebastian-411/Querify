import React, { useState } from "react";

interface AddCommentProps {
  onAddComment: (postId: string, text: string) => void;
  postId: string
}

const AddComment: React.FC<AddCommentProps> = ({ onAddComment, postId }) => {
  const [commentText, setCommentText] = useState("");

  const handleCommentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setCommentText(e.target.value);
  };

  const handleAddComment = () => {
    // Validar que el comentario no esté vacío
    if (commentText.trim() === "") {
      alert("Por favor, ingrese un comentario antes de enviar.");
      return;
    }

    // Llamar a la función de agregar comentario del padre
    onAddComment(postId, commentText);

    // Limpiar el campo de texto después de agregar el comentario
    setCommentText("");
  };

  return (
    <div>
      <h4>Agregar Comentario:</h4>
      <textarea
        value={commentText}
        onChange={handleCommentChange}
        placeholder="Escribe tu comentario..."
        required
      />
      <br />
      <button onClick={handleAddComment}>Agregar Comentario</button>
    </div>
  );
};

export default AddComment;
