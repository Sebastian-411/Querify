import React from "react";

const Comment = (comment: any) => {
  const refactorComment = comment?.comment;
  return (
    <div>
      <p>
        <strong>Usuario:</strong> {refactorComment.user?.user}
      </p>
      <p>{refactorComment.text}</p>
      <hr />
    </div>
  );
};

export default Comment;
