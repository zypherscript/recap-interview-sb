package com.example.itvqs.repository;

import com.example.itvqs.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {

  @Query(value = """
      SELECT a.balance
      FROM Account a
      WHERE a.id = :id
      """)
  long getBalance(@Param("id") long id);

  @Query(value = """
      UPDATE accounts
      SET balance = balance + :cents
      WHERE id = :id
      """,
      nativeQuery = true)
  @Modifying
  @Transactional
  int addBalance(@Param("id") long id, @Param("cents") long cents);
}