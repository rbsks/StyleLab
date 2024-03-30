package com.stylelab.auth.application;

public record SignInUser(
        String email,
        String password) {

    public static SignInUser create(String email, String password) {

        return new SignInUser(email, password);
    }
}
