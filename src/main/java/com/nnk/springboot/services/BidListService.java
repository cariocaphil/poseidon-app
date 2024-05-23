package com.nnk.springboot.services;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.tinylog.Logger;

@Service
public class BidListService {

  private final BidListRepository bidListRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public BidListService(BidListRepository bidListRepository) {
    this.bidListRepository = bidListRepository;
    Logger.info("BidListService initialized with BidListRepository.");
  }

  public List<Bid> findAll() {
    List<Bid> allBids = bidListRepository.findAll();
    Logger.info("Retrieved all bids from the database, count: {}", allBids.size());
    return allBids;
  }

  public Optional<Bid> findById(Integer id) {
    Optional<Bid> bid = bidListRepository.findById(id);
    if (bid.isPresent()) {
      Logger.info("Found bid with ID: {}", id);
    } else {
      Logger.warn("No bid found with ID: {}", id);
    }
    return bid;
  }

  @Transactional
  public Bid save(Bid bid) {
    Logger.info("Saving new bid with details: {}", bid);
    Bid savedBid = bidListRepository.save(bid);
    entityManager.flush();
    entityManager.clear();
    Logger.info("Saved bid successfully with ID: {}", savedBid.getId());
    return savedBid;
  }

  @Transactional
  public Bid update(Bid bid) {
    if (bid != null && bid.getId() != null && bidListRepository.existsById(bid.getId())) {
      Logger.info("Updating bid with ID: {}", bid.getId());
      return bidListRepository.save(bid);
    } else {
      if (bid != null) {
        Logger.error("Bid update failed, bid with ID {} not found.", bid.getId());
      }
      throw new EntityNotFoundException("Bid with id " + bid.getId() + " not found.");
    }
  }

  public void delete(Integer id) {
    if (bidListRepository.existsById(id)) {
      bidListRepository.deleteById(id);
      Logger.info("Deleted bid with ID: {}", id);
    } else {
      Logger.warn("Attempted to delete non-existing bid with ID: {}", id);
    }
  }
}
