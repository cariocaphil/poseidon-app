package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<Bid, Integer> {

}
