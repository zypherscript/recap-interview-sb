package com.example.itvqs.repository;

import com.example.itvqs.entity.Customer;
import java.util.List;

public interface CustomCustomerRepository {

  List<Customer> findByNameWithNativeCustomQuery(String name);

  List<Customer> queryCustomersByName(String name);
}
