package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class TradeRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private TradeRepository tradeRepository;

  @Test
  public void buyQuantityValidationTest() {
    // Creating a Trade with an invalid buyQuantity
    Trade trade = new Trade();
    trade.setAccount("Account Test");
    trade.setType("Type Test");
    trade.setBuyQuantity(0.0); // This should fail as it does not meet the minimum requirement

    // This test expects a ConstraintViolationException due to the invalid buyQuantity
    Assertions.assertThrows(ConstraintViolationException.class, () -> {
      entityManager.persistAndFlush(trade);
    });
  }
}
