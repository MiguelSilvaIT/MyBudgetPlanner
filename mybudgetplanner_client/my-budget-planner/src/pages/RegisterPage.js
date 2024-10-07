// src/pages/RegisterPage.js
import React, { useState } from 'react';
import axios from 'axios';

const RegisterPage = () => {
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessages, setErrorMessages] = useState([]); // Adicionado para armazenar mensagens de erro

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessages([]); // Limpar mensagens de erro antes do novo pedido

    try {
      await axios.post('http://localhost:8080/api/v1/auth/register', {
        firstname,
        lastname,
        email,
        password,
      });
      window.location.href = '/login';
    } catch (error) {
      // Verifica se há uma resposta do backend
      if (error.response && error.response.data && error.response.data.errors) {
        setErrorMessages(error.response.data.errors); // Armazenar as mensagens de erro recebidas do backend
      } else {
        setErrorMessages(['Registration failed. Please try again later.']); // Mensagem genérica de erro
      }
    }
  };

  return (
    <div style={{ padding: '20px', textAlign: 'center' }}>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="First Name"
          value={firstname}
          onChange={(e) => setFirstname(e.target.value)}
          required
        />
        <br />
        <input
          type="text"
          placeholder="Last Name"
          value={lastname}
          onChange={(e) => setLastname(e.target.value)}
          required
        />
        <br />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <br />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <br />
        <button type="submit">Register</button>
      </form>

      {/* Exibir mensagens de erro */}
      {errorMessages.length > 0 && (
        <div style={{ color: 'red', marginTop: '10px' }}>
          <ul>
            {errorMessages.map((error, index) => (
              <li key={index}>{error}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default RegisterPage;
