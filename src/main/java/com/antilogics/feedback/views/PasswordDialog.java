package com.antilogics.feedback.views;

import com.antilogics.feedback.service.PasswordService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Scope(value = "prototype")
public class PasswordDialog extends Dialog {
    @Value("${product_password}")
    private String correctPassword;
    @Value("${notification_duration}")
    private int notificationDuration;
    @Resource
    private PasswordService passwordService;


    @PostConstruct
    private void init() {
        var panel = new VerticalLayout();
        panel.addClassName("ждётоценки-view");
        var passwordField = new PasswordField("Пароль");
        panel.add(passwordField);
        Button confirmButton = new Button("Подтвердить", event -> {
            boolean valid = passwordField.getValue().equals(correctPassword);
            if (!valid) {
                Notification.show("Неправильный пароль", notificationDuration, Notification.Position.MIDDLE);
            }
            else {
                passwordService.setSessionPassword(passwordField.getValue());
                close();
            }
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Отмена", event -> {
            close();
        });
        panel.add(new HorizontalLayout(confirmButton, cancelButton));

        add(panel);
    }
}
