package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class RatingRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private RatingRepository ratingRepository;

  @Test
  public void orderNumberValidationTest() {
    Rating rating = new Rating();
    rating.setMoodysRating("AAA");
    rating.setSandPRating("AAA");
    rating.setFitchRating("AAA");
    rating.setOrderNumber(-1);  // Negative value should fail

    Assertions.assertThrows(ConstraintViolationException.class, () -> {
      entityManager.persistAndFlush(rating); // This should throw an exception due to validation failure
    });
  }
}
