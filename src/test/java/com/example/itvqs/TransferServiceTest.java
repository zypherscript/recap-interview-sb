package com.example.itvqs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.itvqs.entity.Account;
import com.example.itvqs.repository.AccountRepository;
import com.example.itvqs.service.TransferService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class TransferServiceTest {

  @Autowired
  private TransferService transferService;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  @DirtiesContext
  @Disabled("wip")
  public void testParallelExecution() throws InterruptedException {
    var alice = new Account();
    alice.setId(1L);
    alice.setBalance(10L);
    alice.setOwner("Alice");
    accountRepository.save(alice);
    var bob = new Account();
    bob.setId(2L);
    bob.setBalance(0L);
    bob.setOwner("Bob");
    accountRepository.save(bob);

    assertEquals(10L, accountRepository.getBalance(1));
    assertEquals(0L, accountRepository.getBalance(2));

    int threadCount = 12;
    var executorService = Executors.newFixedThreadPool(threadCount);
    var futures = executorService.invokeAll(
        IntStream
            .range(0, threadCount)
            .mapToObj(
                i -> (Callable<Boolean>) () ->
                    transferService.transfer(1L, 2L, 5L))
            .toList()
    );
    for (var future : futures) {
      try {
        future.get();
      } catch (InterruptedException | ExecutionException e) {
        log.error("Transfer failed", e);
      }
    }

    log.info(accountRepository.findAll().toString());
  }
}
