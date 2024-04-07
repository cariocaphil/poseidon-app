package com.nnk.springboot;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@SpringBootTest
public class BidTests {

  @Autowired
  private BidListRepository bidListRepository;


  @Autowired
  private BidListService bidListService;

  @Test
  public void bidListTest() {
    Bid bid = new Bid();
    bid.setAccount("Account Test");
    bid.setType("Type Test");
    bid.setBidQuantity(10.0);

    // Save
    bid = bidListRepository.save(bid);
    Assertions.assertNotNull(bid.getId());
    Assertions.assertEquals(10.0, bid.getBidQuantity());

    // Find
    Bid foundBid = bidListRepository.findById(bid.getId()).orElse(null);
    Assertions.assertNotNull(foundBid);

/*
    // Delete
    bidListRepository.delete(bid);

    // Verify
    Optional<Bid> deletedBid = bidListRepository.findById(bid.getId());
    Assertions.assertFalse(deletedBid.isPresent());*/
  }

  @Test
  public void saveNullBidTest() {
    Bid bid = null;
    Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> bidListService.save(bid));
  }


  @Test
  public void findByIdInvalidIdTest() {
    Integer invalidId = -1;
    Optional<Bid> foundBid = bidListService.findById(invalidId);
    Assertions.assertFalse(foundBid.isPresent());
  }
}
