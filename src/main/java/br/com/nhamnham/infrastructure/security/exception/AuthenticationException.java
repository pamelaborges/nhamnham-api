package br.com.nhamnham.infrastructure.security.exception;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException() {
        super("Usuário e/ou senha inválidos");
    }
}
