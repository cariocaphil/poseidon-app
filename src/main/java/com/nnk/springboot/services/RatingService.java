package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

  private final RatingRepository ratingRepository;

  @Autowired
  public RatingService(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
  }

  public List<Rating> findAll() {
    return ratingRepository.findAll();
  }

  public Optional<Rating> findById(Long id) {
    return ratingRepository.findById(Math.toIntExact(id));
  }

  public Rating save(Rating rating) {
    return ratingRepository.save(rating);
  }

  public void delete(Long id) {
    ratingRepository.deleteById(Math.toIntExact(id));
  }
}
