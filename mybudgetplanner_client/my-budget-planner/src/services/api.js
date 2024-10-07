// src/services/api.js
import axios from 'axios';
import { getToken } from './authService';

// Criar uma instância do Axios
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Adicionar um interceptor para incluir o token de autenticação em cada requisição
api.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;
