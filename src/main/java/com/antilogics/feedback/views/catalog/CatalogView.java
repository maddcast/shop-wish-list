package com.antilogics.feedback.views.catalog;

import com.antilogics.feedback.domain.Product;
import com.antilogics.feedback.service.ProductService;
import com.antilogics.feedback.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

@Route(value = "catalog", layout = MainView.class)
@PageTitle("Весь каталог")
@CssImport(value = "./styles/views/catalog/catalog-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
@RouteAlias(value = "", layout = MainView.class)
@Component
@Scope(value = "prototype")
public class CatalogView extends Div implements AfterNavigationObserver {

    @Resource
    private ProductService productService;

    private final Grid<Product> grid = new Grid<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public CatalogView() {
        setId("веськаталог-view");
        addClassName("веськаталог-view");
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

        HorizontalLayout stars = new HorizontalLayout();
        stars.addClassName("actions");
        stars.setSpacing(false);
        stars.getThemeList().add("spacing-s");

        for (int i = 0; i < product.getStars(); i++) {
            stars.add(new IronIcon("vaadin", "star"));
        }

        description.add(header, post, stars);
        card.add(image, description);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        grid.setItems(productService.getList(true));
    }
}
