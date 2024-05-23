package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

  private final CurvePointRepository curvePointRepository;

  @Autowired
  public CurvePointService(CurvePointRepository curvePointRepository) {
    this.curvePointRepository = curvePointRepository;
    Logger.info("CurvePointService initialized.");
  }

  @Transactional(readOnly = true)
  public List<CurvePoint> findAll() {
    List<CurvePoint> allCurvePoints = curvePointRepository.findAll();
    Logger.info("Retrieved all curve points, count: {}", allCurvePoints.size());
    return allCurvePoints;
  }

  @Transactional(readOnly = true)
  public Optional<CurvePoint> findById(Integer id) {
    Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
    if (curvePoint.isPresent()) {
      Logger.info("Found curve point with ID: {}", id);
    } else {
      Logger.warn("No curve point found with ID: {}", id);
    }
    return curvePoint;
  }

  @Transactional
  public CurvePoint save(CurvePoint curvePoint) {
    Logger.info("Saving curve point: {}", curvePoint);
    CurvePoint savedCurvePoint = curvePointRepository.save(curvePoint);
    Logger.info("Curve point saved successfully with ID: {}", savedCurvePoint.getId());
    return savedCurvePoint;
  }

  @Transactional
  public void delete(Integer id) {
    if (curvePointRepository.existsById(id)) {
      curvePointRepository.deleteById(id);
      Logger.info("Deleted curve point with ID: {}", id);
    } else {
      Logger.warn("Attempted to delete non-existing curve point with ID: {}", id);
    }
  }

  @Transactional
  public CurvePoint update(CurvePoint curvePoint) {
    if (curvePoint.getId() != null && curvePointRepository.existsById(curvePoint.getId())) {
      Logger.info("Updating curve point with ID: {}", curvePoint.getId());
      return curvePointRepository.save(curvePoint);
    } else {
      Logger.error("Attempted to update non-existing curve point with ID: {}", curvePoint.getId());
      throw new IllegalArgumentException("CurvePoint must have an ID to be updated");
    }
  }
}
