package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class CurvePointRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CurvePointRepository curvePointRepository;

  @Test
  public void termAndValueValidationTest() {
    // Creating a CurvePoint with invalid values for term and value
    CurvePoint curvePoint = new CurvePoint();
    curvePoint.setCurveId(1);
    curvePoint.setTerm(-1.0);  // Negative value should fail for term
    curvePoint.setValue(-100.0);  // Negative value should fail for value

    // This should throw an exception due to validation failure for term and value
    Assertions.assertThrows(ConstraintViolationException.class, () -> {
      entityManager.persistAndFlush(curvePoint);
    });
  }
}
