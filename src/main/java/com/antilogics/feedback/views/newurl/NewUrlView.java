package com.antilogics.feedback.views.newurl;

import com.antilogics.feedback.domain.Product;
import com.antilogics.feedback.service.ProductService;
import com.antilogics.feedback.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Route(value = "newurl", layout = MainView.class)
@PageTitle("Новый товар")
@CssImport("./styles/views/newurl/new-url-view.css")
@Component
@Scope(value = "prototype")
public class NewUrlView extends HorizontalLayout {

    @Resource
    private ProductService productService;
    @Value("${product_password}")
    private String password;


    public NewUrlView() {
        setId("new-url-view");
        var jsonField = new TextField("Товар");
        var passwordField = new PasswordField("Пароль");
        var addButton = new Button("Добавить");
        add(jsonField, passwordField, addButton);
        setVerticalComponentAlignment(Alignment.END, jsonField, passwordField, addButton);
        addButton.addClickListener(e-> {
            try {
                if (passwordField.getValue().equals(password)) {
                    var p = new JSONObject(jsonField.getValue());
                    Product product = new Product();
                    product.setUrl(p.getString("url"));
                    product.setName(p.getString("title"));
                    product.setPrice(p.getInt("price"));
                    product.setWeight(p.getInt("weight"));
                    product.setImageUrl(p.getString("image"));
                    product.setCreateDate(new Date());
                    productService.saveAndFlush(product);
                    Notification.show("Added product");
                    jsonField.setValue("");
                }
                else {
                    Notification.show("Unknown password");
                }
            }
            catch (Exception e1) {
                Notification.show(e1.getMessage());
            }
        });
    }
}
