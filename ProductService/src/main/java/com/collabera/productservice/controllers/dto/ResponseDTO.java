package com.collabera.productservice.controllers.dto;

import java.util.List;

import com.collabera.productservice.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ResponseDTO {
	public String message;
	public List<ProductDTO> products;
	public ProductDTO product;
}
