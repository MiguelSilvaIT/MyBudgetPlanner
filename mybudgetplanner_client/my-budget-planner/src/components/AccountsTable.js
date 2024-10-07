import React from 'react';
import PropTypes from 'prop-types'; // Adicionar validação de tipos com PropTypes
import './AccountsTable.css'; // Estilo para a tabela de contas

const AccountsTable = ({ accounts }) => (
  <table className="accounts-table">
    <thead>
      <tr>
        <th>Account Name</th>
        <th>Balance</th>
        <th>Transactions Count</th>
      </tr>
    </thead>
    <tbody>
      {accounts.map((account) => (
        <tr key={account.id}>
          <td>{account.accountName}</td>
          <td>${account.balance.toFixed(2)}</td>
          <td>{account.transactions.length}</td>
        </tr>
      ))}
    </tbody>
  </table>
);

// Validação dos tipos de propriedades
AccountsTable.propTypes = {
  accounts: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      accountName: PropTypes.string.isRequired,
      balance: PropTypes.number.isRequired,
      transactions: PropTypes.arrayOf(
        PropTypes.shape({
          id: PropTypes.number.isRequired,
          createdDate: PropTypes.string.isRequired,
          description: PropTypes.string.isRequired,
          value: PropTypes.number.isRequired,
        })
      ).isRequired,
    })
  ).isRequired,
};

export default AccountsTable;
