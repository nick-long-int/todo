package com.emobile.springtodo.exception;

public record ResponseExceptionDto(int status, String message, String timestamp) {
}
