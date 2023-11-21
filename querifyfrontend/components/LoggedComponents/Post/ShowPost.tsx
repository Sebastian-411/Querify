import axios from "axios";
import React, { useEffect, useState } from "react";
import Comment from "./Comment";
import AddComment from "./AddComment";
import QueryShow from "../Query/QueryShow";

const ShowPosts = () => {
  const [posts, setPosts] = useState<any[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userT = localStorage.getItem("user");
        const user = userT ? JSON.parse(userT) : null;

        const response = await axios.get(`http://localhost:8080/api/posts`);
        setPosts(response.data);
      } catch (error) {
        console.error("Error al obtener data de la API", error);
      }
    };

    fetchData();
  }, []);

  const handleLike = async (post: any) => {
    const userT = localStorage.getItem("user");
    const user = userT ? JSON.parse(userT) : null;
    try {
      const response = await axios.post(
        `http://localhost:8080/api/posts/${post?.id}/like/${user?.id}`
      );
      console.log(`Diste like al post ${post}`);
      window.location.reload();
    } catch (error: any) {
      if (error.response && error.response.status === 400) {
        alert("Ya le diste like! :)");
      } else {
        console.error("Error al dar like:", error);
      }
    }
  };

  const handleAddComment = async (postId: string, text: string) => {
    const userT = localStorage.getItem("user");
    const user = userT ? JSON.parse(userT) : null;
    try {
      const response = await axios.post(
        `http://localhost:8080/api/posts/${postId}/comment?userId=${user?.id}&text=${text}`
      );
    } catch (error: any) {
      console.error("Error al comentar:", error);
    }
    window.location.reload();
  };

  return (
    <div>
      <h2>Lista de Posts</h2>
      {posts.map((post) => (
        <div key={post?.id}>
          <h3>{post?.title}</h3>
          <QueryShow query={post?.content} />
          <p>Likes: {post?.likes}</p>
          <button
            onClick={() => {
              handleLike(post);
            }}
          >
            Dar Like
          </button>
          <div>
            <h4>Comentarios:</h4>
            {post?.comments.map((comment: any) => (
              <>
                <Comment key={comment?.id} comment={comment} />
              </>
            ))}
            <br />
            <AddComment onAddComment={handleAddComment} postId={post.id} />
          </div>
        </div>
      ))}
    </div>
  );
};

export default ShowPosts;
