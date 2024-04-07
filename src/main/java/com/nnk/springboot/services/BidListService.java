package com.nnk.springboot.services;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.persistence.EntityManager;
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

  public Bid update(Bid bidList) {
    // It assumes that the BidList exists. You may want to add a check and handle it accordingly.
    return bidListRepository.save(bidList);
  }

  public void delete(Integer id) {
    bidListRepository.deleteById(id);
  }
}
