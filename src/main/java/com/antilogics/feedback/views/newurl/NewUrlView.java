package com.antilogics.feedback.views.newurl;

import com.antilogics.feedback.domain.Product;
import com.antilogics.feedback.service.ProductService;
import com.antilogics.feedback.views.AuthButton;
import com.antilogics.feedback.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    @Resource
    private AuthButton addButton;
    @Value("${notification_duration}")
    private int notificationDuration;


    @PostConstruct
    private void init() {
        setId("new-url-view");
        var jsonField = new TextField("Товар");
        addButton.setText("Добавить");
        add(jsonField, addButton);
        setVerticalComponentAlignment(Alignment.END, jsonField, addButton);
        addButton.setOnClick(() -> {
            try {
                var p = new JSONObject(jsonField.getValue());
                Product product = new Product();
                product.setUrl(p.getString("url"));
                product.setName(p.getString("title"));
                product.setPrice(p.getInt("price"));
                product.setWeight(p.getInt("weight"));
                product.setImageUrl(p.getString("image"));
                product.setCreateDate(new Date());
                productService.saveAndFlush(product);
                Notification.show("Добавил продукт", notificationDuration, Notification.Position.MIDDLE);
                jsonField.setValue("");
            }
            catch (Exception e1) {
                Notification.show(e1.getMessage(), notificationDuration, Notification.Position.MIDDLE);
            }
        });
    }
}
