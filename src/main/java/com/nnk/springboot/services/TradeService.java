package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

  @Autowired
  private TradeRepository tradeRepository;

  public List<Trade> findAll() {
    List<Trade> trades = tradeRepository.findAll();
    Logger.info("Retrieved all trades, count: {}", trades.size());
    return trades;
  }

  public Optional<Trade> findById(Long id) {
    Optional<Trade> trade = tradeRepository.findById(Math.toIntExact(id));
    if (trade.isPresent()) {
      Logger.info("Found trade with ID: {}", id);
    } else {
      Logger.warn("No trade found with ID: {}", id);
    }
    return trade;
  }

  public Trade save(Trade trade) {
    Trade savedTrade = tradeRepository.save(trade);
    Logger.info("Trade saved successfully with ID: {}", savedTrade.getTradeId());
    return savedTrade;
  }

  public void delete(Trade trade) {
    if (trade != null && trade.getTradeId() != null && tradeRepository.existsById(Math.toIntExact(trade.getTradeId()))) {
      tradeRepository.delete(trade);
      Logger.info("Deleted trade with ID: {}", trade.getTradeId());
    } else {
      Logger.warn("Attempted to delete non-existing trade with ID: {}", trade != null ? trade.getTradeId() : "null");
    }
  }
}
