package com.antilogics.feedback.views;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GlobalPasswordField extends PasswordField {
    @Value("${product_password}")
    private String password;
    @Value("${notification_duration}")
    private int notificationDuration;


    public boolean valid() {
        boolean valid = getValue().equals(password);
        if (!valid) {
            Notification.show("Неправильный пароль", notificationDuration, Notification.Position.MIDDLE);
        }
        return valid;
    }
}
