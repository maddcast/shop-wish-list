package com.antilogics.feedback.dao;

import com.antilogics.feedback.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByModerated(boolean moderated);
}
