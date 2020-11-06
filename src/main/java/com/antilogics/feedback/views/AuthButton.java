package com.antilogics.feedback.views;

import com.antilogics.feedback.service.PasswordService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Scope("prototype")
public class AuthButton extends Button {
    @Resource
    private ApplicationContext applicationContext;


    public void setOnClick(Runnable runnable) {
        addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            var passwordService = applicationContext.getBean(PasswordService.class);

            if (passwordService.valid()) {
                runnable.run();
            }
            else {
                var dialog = applicationContext.getBean(PasswordDialog.class);
                dialog.addOpenedChangeListener((ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>) dialogOpenedChangeEvent -> {
                    if (passwordService.valid()) {
                        runnable.run();
                    }
                });
                dialog.open();
            }
        });
    }
}
