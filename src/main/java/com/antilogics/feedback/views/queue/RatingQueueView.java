package com.antilogics.feedback.views.queue;

import com.antilogics.feedback.domain.Product;
import com.antilogics.feedback.service.ProductService;
import com.antilogics.feedback.views.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Route(value = "queue", layout = MainView.class)
@PageTitle("Ждёт оценки")
@CssImport(value = "./styles/views/queue/queue-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
@Component
@Scope(value = "prototype")
public class RatingQueueView extends Div implements AfterNavigationObserver {

    @Resource
    private ProductService productService;
    @Resource
    private ApplicationContext applicationContext;

    private List<Product> products;
    private final Grid<Product> grid = new Grid<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");


    public RatingQueueView() {
        setId("ждётоценки-view");
        addClassName("ждётоценки-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        add(grid);
    }

    private HorizontalLayout createCard(Product product) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        Image image = new Image();
        image.setSrc(product.getImageUrl());
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(product.getName());
        name.addClassName("name");
        Span date = new Span(sdf.format(product.getCreateDate()));
        date.addClassName("date");
        header.add(name, date);

        Span post = new Span(product.getComment());
        post.addClassName("post");

        var dialog = applicationContext.getBean(RatingDialog.class);
        dialog.createUI(product);
        dialog.addOpenedChangeListener((ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>) dialogOpenedChangeEvent -> {
            if (product.isModerated()) {
                products.remove(product);
                grid.getDataProvider().refreshAll();
            }
        });

        Button rateButton = new Button("Оценить");
        rateButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            dialog.open();
        });

        description.add(header, post, rateButton);
        card.add(image, description);
        return card;
    }


    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        products = productService.getList(false);
        grid.setItems(products);
    }
}
