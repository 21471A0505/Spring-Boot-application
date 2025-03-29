// File: frontend/app/page.js
"use client";

import { useEffect, useState } from "react";

export default function Home() {
  const [posts, setPosts] = useState([]);
  const [title, setTitle] = useState("");
  const [body, setBody] = useState("");
  const [authCode, setAuthCode] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchPosts();
  }, []);

  const fetchPosts = async () => {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/list");
      const data = await res.json();
      setPosts(data);
    } catch (err) {
      setError("Failed to load posts");
    }
    setLoading(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await fetch("http://localhost:8080/post", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          PinggyAuthHeader: authCode,
        },
        body: JSON.stringify({ title, body }),
      });
      if (!res.ok) throw new Error("Submission failed");
      setTitle("");
      setBody("");
      setAuthCode("");
      fetchPosts();
    } catch (err) {
      setError("Submission failed");
    }
  };

  return (
    <main className="p-8">
      <h1 className="text-2xl mb-4">Post Submission</h1>
      <form onSubmit={handleSubmit} className="space-y-4 mb-8">
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Title"
          required
          className="border p-2 w-full"
        />
        <textarea
          value={body}
          onChange={(e) => setBody(e.target.value)}
          placeholder="Body"
          required
          className="border p-2 w-full"
        ></textarea>
        <input
          type="text"
          value={authCode}
          onChange={(e) => setAuthCode(e.target.value)}
          placeholder="Auth Code"
          required
          className="border p-2 w-full"
        />
        <button type="submit" className="bg-blue-500 text-white px-4 py-2">Submit</button>
        {error && <p className="text-red-500">{error}</p>}
      </form>

      <h2 className="text-xl mb-2">Posts</h2>
      {loading ? (
        <p>Loading posts...</p>
      ) : (
        <ul>
          {posts.map((post, index) => (
            <li key={index} className="border p-2 mb-2">
              <h3 className="font-bold">{post.title}</h3>
              <p>{post.body}</p>
              <p className="text-sm text-gray-500">Auth: {post.pinggyAuthHeader}</p>
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}
