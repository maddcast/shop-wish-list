package com.antilogics.feedback.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class PasswordService {
    @Value("${product_password}")
    private String correctPassword;

    private String sessionPassword;


    public void setSessionPassword(String sessionPassword) {
        this.sessionPassword = sessionPassword;
    }

    public boolean valid() {
        return correctPassword.equals(sessionPassword);
    }
}
