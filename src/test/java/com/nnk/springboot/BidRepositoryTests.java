package com.nnk.springboot;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class BidRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BidListRepository bidListRepository;

  @Test
  public void bidQuantityValidationTest() {
    Bid bid = new Bid();
    bid.setAccount("Test Account");
    bid.setType("Test Type");
    bid.setBidQuantity(-5.0); // Negative value should fail

    Assertions.assertThrows(ConstraintViolationException.class, () -> {
      entityManager.persistAndFlush(
          bid); // This should throw an exception due to validation failure
    });
  }
}
