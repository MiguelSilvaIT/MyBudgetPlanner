// src/services/authService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/auth';

export const login = async (email, password) => {
  try {
    const response = await axios.post(`${API_URL}/authenticate`, {
      email,
      password,
    });

    // Armazenar o token JWT no localStorage
    if (response.data && response.data.token) {
      localStorage.setItem('token', response.data.token);
    }

    return response.data;
  } catch (error) {
    throw error.response.data.errors || ['An error occurred while logging in.'];
  }
};

export const logout = () => {
  // Remover o token do localStorage ao sair
  localStorage.removeItem('token');
};

export const getToken = () => {
  return localStorage.getItem('token');
};

export const isAuthenticated = () => {
  return !!getToken(); // Retorna true se houver um token, caso contrário, false
};
