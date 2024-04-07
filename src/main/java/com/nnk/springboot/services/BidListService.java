package com.nnk.springboot.services;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

  private final BidListRepository bidListRepository;

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

  public Bid save(Bid bidList) {
    return bidListRepository.save(bidList);
  }

  public Bid update(Bid bidList) {
    // It assumes that the BidList exists. You may want to add a check and handle it accordingly.
    return bidListRepository.save(bidList);
  }

  public void delete(Integer id) {
    bidListRepository.deleteById(id);
  }
}
