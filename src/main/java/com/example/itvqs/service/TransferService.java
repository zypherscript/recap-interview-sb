package com.example.itvqs.service;

import com.example.itvqs.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

  private final AccountRepository accountRepository;

  //  @Retryable(maxAttempts = 10)
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public boolean transfer(long fromId, long toId, long cents) { //synchronized
    boolean status = true;

    long fromBalance = accountRepository.getBalance(fromId);

    if (fromBalance >= cents) {
      status &= accountRepository.addBalance(fromId, (-1) * cents) > 0;

      status &= accountRepository.addBalance(toId, cents) > 0;
    } else {
      throw new IllegalArgumentException("Insufficient balance.");
    }

    return status;
  }
}
