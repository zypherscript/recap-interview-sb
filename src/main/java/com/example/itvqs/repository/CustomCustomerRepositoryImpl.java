package com.example.itvqs.repository;

import com.example.itvqs.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @SuppressWarnings("unchecked")
  public List<Customer> findByNameWithNativeCustomQuery(String name) {
    String sql = "SELECT * FROM customers WHERE name = :value"; //OR ?1
    return entityManager.createNativeQuery(sql, Customer.class)
        .setParameter("value", name)
        .getResultList();
  }

  @Override
  public List<Customer> queryCustomersByName(String name) {
    var cb = entityManager.getCriteriaBuilder();
    var query = cb.createQuery(Customer.class);
    var root = query.from(Customer.class);

    List<Predicate> predicates = new ArrayList<>();
    if (name != null) {
      predicates.add(cb.equal(root.get("name"), name));
    }

    query.select(root).where(predicates.toArray(new Predicate[0]));

    return entityManager.createQuery(query).getResultList();
  }
}
