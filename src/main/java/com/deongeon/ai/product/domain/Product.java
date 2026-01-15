package com.deongeon.ai.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String brand;
	private String category;

	private double price;
	private String imageUrl;

	private int viewCount = 0;

	private double costCny;
	private double weightKg;
	private double sellPriceKrw;

}
