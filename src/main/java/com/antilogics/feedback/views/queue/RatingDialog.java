package com.antilogics.feedback.views.queue;

import com.antilogics.feedback.domain.Product;
import com.antilogics.feedback.service.ProductService;
import com.antilogics.feedback.views.GlobalPasswordField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Scope(value = "prototype")
public class RatingDialog extends Dialog {

    @Resource
    private ProductService productService;
    @Resource
    private GlobalPasswordField passwordField;

    private Checkbox orderAgain;
    private TextArea comment;
    private RadioButtonGroup<Integer> stars;


    public void createUI(Product product) {
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        var panel = new VerticalLayout();
        panel.addClassName("ждётоценки-view");
        Image image = new Image();
        image.setSrc(product.getImageUrl());
        Span nameLabel = new Span(product.getName());
        nameLabel.addClassName("name");
        Span priceLabel = new Span("Цена: " + product.getPrice() + " р.");
        priceLabel.addClassName("post");
        Span weightLabel = new Span("Вес: " + product.getWeight() + " г.");
        weightLabel.addClassName("post");
        Anchor link = new Anchor(product.getUrl(), product.getUrl());
        link.setTarget("_blank");
        panel.add(new HorizontalLayout(image, new VerticalLayout(nameLabel, priceLabel, weightLabel, link)));
        orderAgain = new Checkbox("Заказывать ещё", false);
        orderAgain.setValue(true);
        panel.add(orderAgain);
        comment = new TextArea("Комментарий");
        panel.add(comment);
        stars = new RadioButtonGroup<>();
        stars.setLabel("Рейтинг");
        stars.setItems(1, 2, 3, 4, 5);
        panel.add(stars);
        panel.add(passwordField);

        Button confirmButton = new Button("Оценить", event -> {
            if (updateProduct(product)) {
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

    private boolean updateProduct(Product product) {
        boolean valid = passwordField.valid();
        if (valid) {
            product.setModerated(true);
            product.setOrderAgain(orderAgain.getValue());
            product.setComment(comment.getValue());
            product.setStars(stars.getOptionalValue().orElse(0));
            productService.saveAndFlush(product);
        }
        return valid;
    }
}
