import React from "react";

const Comment = (comment: any) => {
  const refactorComment = comment?.comment;
  return (
    <div>
      <p>{refactorComment.text}</p>
      <p>
        <strong>Usuario:</strong> {refactorComment.user?.user}
      </p>
      <hr />
    </div>
  );  
};

export default Comment;
