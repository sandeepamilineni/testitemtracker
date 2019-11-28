package com.inventory.model;

import java.math.BigDecimal;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


public class Item {

	@Size(max = 20, min = 3, message = "{user.name.invalid}")
	@NotEmpty(message=" Enter item name")
   private String serialNumber;

   @Size(max = 20, min = 3, message = "{user.name.invalid}")
   @NotEmpty(message=" Enter item name")
   private String name;

   @NotNull(message="Enter item price")
   @Digits(integer= 10, fraction = 5, message = "Not a number or number too large")
   private BigDecimal  price;
   
   
   public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

 
}
