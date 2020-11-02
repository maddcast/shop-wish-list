package com.antilogics.feedback.service;

import com.antilogics.feedback.dao.ProductRepository;
import com.antilogics.feedback.domain.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {
    @Resource
    private ProductRepository productRepository;


    public <S extends Product> S saveAndFlush(S product) {
        return productRepository.saveAndFlush(product);
    }

    public List<Product> getList(boolean moderated) {
        return productRepository.findByModerated(moderated);
    }
}
