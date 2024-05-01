package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

  private final CurvePointRepository curvePointRepository;

  @Autowired
  public CurvePointService(CurvePointRepository curvePointRepository) {
    this.curvePointRepository = curvePointRepository;
  }

  @Transactional(readOnly = true)
  public List<CurvePoint> findAll() {
    return curvePointRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<CurvePoint> findById(Integer id) {
    return curvePointRepository.findById(id);
  }

  @Transactional
  public CurvePoint save(CurvePoint curvePoint) {
    return curvePointRepository.save(curvePoint);
  }

  @Transactional
  public void delete(Integer id) {
    curvePointRepository.deleteById(id);
  }

  @Transactional
  public CurvePoint update(CurvePoint curvePoint) {
    if (curvePoint.getId() == null) {
      throw new IllegalArgumentException("CurvePoint must have an ID to be updated");
    }
    return curvePointRepository.save(curvePoint);
  }

}