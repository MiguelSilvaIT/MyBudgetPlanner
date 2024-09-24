package com.miguel.mybudgetplanner.response;

import java.util.Arrays;
import java.util.List;

public class ResponseUtil {

    // Método para criar uma resposta de sucesso
    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return new ApiResponse<>(
                true,
                message,
                data,
                null,
                0, // Sem código de erro em cenários de sucesso
                System.currentTimeMillis(),
                path
        );
    }

    // Método para criar uma resposta de erro com uma lista de erros
    public static <T> ApiResponse<T> error(List<String> errors, String message, int errorCode, String path) {
        return new ApiResponse<>(
                false,
                message,
                null,
                errors,
                errorCode,
                System.currentTimeMillis(),
                path
        );
    }

    // Método para criar uma resposta de erro com uma única mensagem de erro
    public static <T> ApiResponse<T> error(String error, String message, int errorCode, String path) {
        return error(Arrays.asList(error), message, errorCode, path);
    }
}