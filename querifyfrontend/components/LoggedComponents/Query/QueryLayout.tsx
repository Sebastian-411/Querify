import React, { useEffect, useState } from "react";
import CreateQuery from "./QueryProps";
import axios from "axios";
import CreatePost from "../Post/CreatePosts";
import ShowPosts from "../Post/ShowPost";
import QueryConstructor from "./QueryConstructor";
import QueryShow from "./QueryShow";

// Define the QueryLayout component
export default function QueryLayout() {
  // State variable to manage the queries data
  const [queries, setQueries] = useState<any[]>([]);

  // Effect to fetch data when the component mounts
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Retrieve the user information from local storage
        const userT = localStorage.getItem("user");
        const user = userT ? JSON.parse(userT) : null;

        // Fetch queries data based on user ID
        const response = await axios.get(
          `http://localhost:8080/api/users/${user?.id}/queries`
        );

        // Update the state with the fetched queries data
        setQueries(response.data);
      } catch (error) {
        console.error("Error fetching queries from the API", error);
      }
    };

    fetchData();
  }, []);

  // Function to handle the creation of a new query
  const handleCreateQuery = async (newQuery: {
    title: string;
    content: string;
  }) => {
    try {
      // Retrieve the user information from local storage
      const userT = localStorage.getItem("user");
      const user = userT ? JSON.parse(userT) : null;

      // Post the new query to the API
      const response = await axios.post(`http://localhost:8080/api/queries`, {
        ...newQuery,
        user: { id: user?.id },
      });

      // Log the response data
      console.log(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  // Function to create a new post
  const createPost = async (queryId: string, autorComment: string) => {
    try {
      // Retrieve the user information from local storage
      const userT = localStorage.getItem("user");
      const user = userT ? JSON.parse(userT) : null;

      // Post a new post associated with a query to the API
      const response = await axios.post(
        `http://localhost:8080/api/posts/${user?.id}/${queryId}?autorComment=${autorComment}`
      );

      // Log the response data
      console.log(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  // Render the QueryLayout component
  return (
    <div>
      <div>
        {/* Render the QueryConstructor component for creating new queries */}
        <QueryConstructor onCreateQuery={handleCreateQuery} />
        {queries?.length > 0 ? (
          <div>
            <h1>My queries</h1>
            {/* Render a list of queries with QueryShow component for each */}
            <ul>
              {queries.map((item: any) => (
                <li key={item.id}>
                  {item.title} <br />
                  {/* Render details of the query using QueryShow component */}
                  <QueryShow query={item.content} /> <br />
                </li>
              ))}
            </ul>
          </div>
        ) : (
          <p>Loading queries...</p>
        )}
      </div>
      <br />
      {/* Render the CreatePost component for creating new posts */}
      <CreatePost queries={queries} onCreatePost={createPost} />
      {/* Render the ShowPosts component */}
      <ShowPosts />
    </div>
  );
}
