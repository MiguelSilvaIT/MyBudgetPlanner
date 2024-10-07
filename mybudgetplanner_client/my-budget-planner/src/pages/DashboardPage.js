import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AccountsTable from '../components/AccountsTable'; // Componente para exibir contas
import ErrorMessage from '../components/ErrorMessage'; // Componente para mensagens de erro
import './DashboardPage.css'; // Arquivo CSS para estilos

const DashboardPage = () => {
  const [accounts, setAccounts] = useState([]); // Para armazenar as contas
  const [totalBalance, setTotalBalance] = useState(0); // Saldo total
  const [firstname, setFirstname] = useState(''); // Nome do usuário
  const [error, setError] = useState(''); // Armazenar mensagens de erro

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const token = localStorage.getItem('token');
        
        if (!token) {
          throw new Error('Authentication token not found. Please log in.');
        }

        // Fazer as requisições para buscar as informações do usuário e suas contas
        const [userResponse, accountsResponse] = await Promise.all([
          axios.get('http://localhost:8080/api/users/me', {
            headers: { Authorization: `Bearer ${token}` },
          }),
          axios.get('http://localhost:8080/api/accounts/me', {
            headers: { Authorization: `Bearer ${token}` },
          })
        ]);

        // Configurar o nome e saldo total do usuário
        if (userResponse.data?.data) {
          setFirstname(userResponse.data.data.firstname);
          setTotalBalance(userResponse.data.data.totalBalance);
        }

        // Configurar as contas do usuário
        if (accountsResponse.data?.data) {
          setAccounts(accountsResponse.data.data);
        }

      } catch (error) {
        console.error('Error fetching dashboard data:', error.message);
        setError(error.message || 'Failed to load dashboard data.');
      }
    };

    fetchDashboardData();
  }, []);

  return (
    <div className="dashboard-container">
      <h2>Welcome, {firstname}!</h2>

      {/* Exibir saldo total */}
      <div className="balance-info">
        Total Balance: <strong>${totalBalance.toFixed(2)}</strong>
      </div>

      {/* Exibir mensagem de erro */}
      {error && <ErrorMessage message={error} />}

      {/* Exibir contas ou mensagem de vazio */}
      {accounts.length === 0 && !error ? (
        <div className="no-accounts">You currently have no accounts.</div>
      ) : (
        <AccountsTable accounts={accounts} /> // Mostrar a tabela de contas
      )}
    </div>
  );
};

export default DashboardPage;
