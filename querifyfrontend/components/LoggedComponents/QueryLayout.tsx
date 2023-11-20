import React, { useEffect, useState } from "react";
import CreateQuery from "./QueryProps";
import axios from "axios";
import CreatePost from "./Post/CreatePosts";
import ShowPosts from "./Post/ShowPost";

export default function QueryLayout() {
  const [queries, setQueries] = useState<any[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userT = localStorage.getItem("user");
        const user = userT ? JSON.parse(userT) : null;
        const response = await axios.get(
          `http://localhost:8080/api/users/${user?.id}/queries`
        );
        setQueries(response.data);
      } catch (error) {
        console.error("Error al obtener queries de la API", error);
      }
    };

    fetchData();
  }, []);
  const handleCreateQuery = async (newQuery: {
    title: string;
    content: string;
  }) => {
    try {
      const userT = localStorage.getItem("user");
      const user = userT ? JSON.parse(userT) : null;
      console.log({ ...newQuery, user: { id: user?.id } });
      const response = await axios.post(`http://localhost:8080/api/queries`, {
        ...newQuery,
        user: { id: user?.id },
      });
    } catch (error) {
      console.log(error);
    }
  };

  const createPost = async (queryId: string, autorComment: string) => {
    try {
      const userT = localStorage.getItem("user");
      const user = userT ? JSON.parse(userT) : null;

      const response = await axios.post(
        `http://localhost:8080/api/posts/${user?.id}/${queryId}?autorComment=${autorComment}`
      );

      console.log(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div>
      <div>
        <CreateQuery onCreateQuery={handleCreateQuery} />
        {queries?.length > 0 ? (
          <div>
            <h1>My queries</h1>
            <ul>
              {queries.map((item: any) => (
                <li key={item.id}>
                  {item.title} <br /> {item.content}
                </li>
              ))}
            </ul>
          </div>
        ) : (
          <p>Cargando queries...</p>
        )}
      </div>
      <br />
      <CreatePost queries={queries} onCreatePost={createPost} />
      <ShowPosts />
    </div>
  );
}
