import React from 'react';
import PropTypes from 'prop-types'; // Adicionar PropTypes para validação de tipos
import './TransactionTable.css'; // Separar o estilo para o componente

const TransactionTable = ({ transactions }) => (
  <table className="transaction-table">
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
          <td>${transaction.value.toFixed(2)}</td>
          <td>{transaction.createdDate}</td>
          <td>{transaction.type}</td>
        </tr>
      ))}
    </tbody>
  </table>
);

// Validação dos tipos de propriedades
TransactionTable.propTypes = {
  transactions: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      description: PropTypes.string.isRequired,
      value: PropTypes.number.isRequired,
      createdDate: PropTypes.string.isRequired,
      type: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export default TransactionTable;
