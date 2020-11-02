package com.antilogics.feedback.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String imageUrl;
    @Column
    private String name;
    @Column
    private String url;
    @Column
    private int price;
    @Column
    private int weight;
    @Column
    private Date createDate;
    @Column
    private Date updateDate;
    @Column
    private boolean moderated;
    @Column
    private boolean orderAgain;
    @Column
    private String comment;
    @Column
    private int stars;
}