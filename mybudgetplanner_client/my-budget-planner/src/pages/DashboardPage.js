// src/pages/DashboardPage.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const DashboardPage = () => {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8080/api/transactions', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setTransactions(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchTransactions();
  }, []);

  return (
    <div style={{ padding: '20px' }}>
      <h2>Dashboard</h2>
      <table border="1" style={{ margin: 'auto', width: '80%' }}>
        <thead>
          <tr>
            <th>Description</th>
            <th>Value</th>
            <th>Date</th>
            <th>Type</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((transaction) => (
            <tr key={transaction.id}>
              <td>{transaction.description}</td>
              <td>{transaction.value}</td>
              <td>{transaction.createdDate}</td>
              <td>{transaction.type}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DashboardPage;
