import React from 'react';
import PropTypes from 'prop-types';

const ErrorMessage = ({ message }) => (
  <div style={{ color: 'red', marginBottom: '10px' }}>
    {message}
  </div>
);

// Validação de propriedade
ErrorMessage.propTypes = {
  message: PropTypes.string.isRequired,
};

export default ErrorMessage;
