package com.example.itvqs.mapper;

import com.example.itvqs.dto.CustomerDTO;
import com.example.itvqs.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "email", target = "email")
  CustomerDTO customerToCustomerDTO(Customer customer);
}
