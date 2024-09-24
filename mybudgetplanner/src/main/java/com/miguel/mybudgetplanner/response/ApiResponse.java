package com.miguel.mybudgetplanner.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;    // Indica se a operação foi bem-sucedida
    private String message;     // Mensagem amigável sobre o resultado da operação
    private T data;             // Dados reais da resposta, se houver
    private List<String> errors; // Lista de mensagens de erro, se houver
    private int errorCode;      // Código de erro específico da aplicação
    private long timestamp;     // Timestamp da resposta
    private String path;        // Caminho do endpoint que originou a resposta
}
