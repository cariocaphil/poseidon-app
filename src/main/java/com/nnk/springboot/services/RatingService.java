package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

  private final RatingRepository ratingRepository;

  @Autowired
  public RatingService(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
    Logger.info("RatingService initialized.");
  }

  public List<Rating> findAll() {
    List<Rating> ratings = ratingRepository.findAll();
    Logger.info("Retrieved all ratings, count: {}", ratings.size());
    return ratings;
  }

  public Optional<Rating> findById(Long id) {
    Optional<Rating> rating = ratingRepository.findById(Math.toIntExact(id));
    if (rating.isPresent()) {
      Logger.info("Found rating with ID: {}", id);
    } else {
      Logger.warn("No rating found with ID: {}", id);
    }
    return rating;
  }

  public Rating save(Rating rating) {
    Logger.info("Saving rating: {}", rating);
    Rating savedRating = ratingRepository.save(rating);
    Logger.info("Rating saved successfully with ID: {}", savedRating.getId());
    return savedRating;
  }

  public void delete(Long id) {
    if (ratingRepository.existsById(Math.toIntExact(id))) {
      ratingRepository.deleteById(Math.toIntExact(id));
      Logger.info("Deleted rating with ID: {}", id);
    } else {
      Logger.warn("Attempted to delete non-existing rating with ID: {}", id);
    }
  }

  public Rating update(Rating rating) {
    if (rating.getId() != null && ratingRepository.existsById(Math.toIntExact(rating.getId()))) {
      Logger.info("Updating rating with ID: {}", rating.getId());
      return ratingRepository.save(rating);
    } else {
      Logger.error("Attempted to update non-existing rating with ID: {}", rating.getId());
      throw new IllegalArgumentException("Rating must have an ID to be updated");
    }
  }
}
