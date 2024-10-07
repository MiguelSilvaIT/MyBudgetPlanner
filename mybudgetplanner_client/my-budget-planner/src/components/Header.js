// src/components/Header.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { isAuthenticated, logout } from '../services/authService';

const Header = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header style={{ padding: '10px', textAlign: 'right' }}>
      {isAuthenticated() ? (
        <>
          <span>Welcome, User!</span> {/* Pode ser atualizado para mostrar o nome do utilizador autenticado */}
          <button onClick={handleLogout} style={{ marginLeft: '10px' }}>
            Logout
          </button>
        </>
      ) : (
        <span>Please log in</span>
      )}
    </header>
  );
};

export default Header;
