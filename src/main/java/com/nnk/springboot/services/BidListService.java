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
  }

  public List<Bid> findAll() {
    return bidListRepository.findAll();
  }

  public Optional<Bid> findById(Integer id) {
    return bidListRepository.findById(id);
  }
  @Transactional
  public Bid save(Bid bidList) {
    Logger.info("Saving bid to the database: {}", bidList);
    Bid savedBid = bidListRepository.save(bidList);
    entityManager.flush();
    entityManager.clear();
    return savedBid;
  }

  @Transactional
  public Bid update(Bid bid) {
    if (bid != null && bid.getId() != null && bidListRepository.existsById(bid.getId())) {
      return bidListRepository.save(bid);
    } else {
      throw new EntityNotFoundException("Bid with id " + bid.getId() + " not found.");
    }
  }

  public void delete(Integer id) {
    bidListRepository.deleteById(id);
  }
}
