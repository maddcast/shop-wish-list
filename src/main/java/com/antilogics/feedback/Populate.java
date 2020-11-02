package com.antilogics.feedback;

import com.antilogics.feedback.domain.Product;
import com.antilogics.feedback.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class Populate {

    @Resource
    private ProductService productService;
    @Value("${populate_example}")
    private boolean populateExample;


    @PostConstruct
    public void init() {
        if (populateExample) {
            productService.saveAndFlush(
                    createProduct2("https://cdn1.ozone.ru/s3/multimedia-n/6004549703.jpg", "Мука универсальная Garnec, без глютена, 600 г", "https://www.ozon.ru/context/detail/id/156468239", 145, 600, new Date(), "Супер", true, 3)
            );
            productService.saveAndFlush(
                    createProduct("https://cdn1.ozone.ru/s3/multimedia-u/6013277694.jpg", "Овсяные хлопьяиз голозерного овса без глютена \"Сташевское\". Набор 2 пачки", "https://www.ozon.ru/context/detail/id/172383094", 286, 750, new Date())
            );
            productService.saveAndFlush(
                    createProduct("https://cdn1.ozone.ru/s3/multimedia-b/6000365951.jpg", "конфеты \"Чернослив в шоколаде\"", "https://www.ozon.ru/context/detail/id/157876448", 220, 135, new Date())
            );
        }
    }


    private static Product createProduct(String image, String name, String url, int price, int weight, Date date) {
        Product p = new Product();
        p.setImageUrl(image);
        p.setName(name);
        p.setUrl(url);
        p.setPrice(price);
        p.setWeight(weight);
        p.setCreateDate(date);

        return p;
    }


    private static Product createProduct2(String image, String name, String url, int price, int weight, Date date,
                                         String comment, boolean orderAgain, int stars) {
        Product p = new Product();
        p.setImageUrl(image);
        p.setName(name);
        p.setUrl(url);
        p.setPrice(price);
        p.setWeight(weight);
        p.setCreateDate(date);
        p.setModerated(true);
        p.setComment(comment);
        p.setOrderAgain(orderAgain);
        p.setStars(stars);

        return p;
    }
}
