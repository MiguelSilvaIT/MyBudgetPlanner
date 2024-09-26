// src/pages/HomePage.js
import React from 'react';
import { Link } from 'react-router-dom';

const HomePage = () => (
  <div style={{ padding: '20px', textAlign: 'center' }}>
    <h1>Welcome to Budget Planner</h1>
    <Link to="/login">
      <button>Login</button>
    </Link>
    <Link to="/register">
      <button>Register</button>
    </Link>
  </div>
);

export default HomePage;
