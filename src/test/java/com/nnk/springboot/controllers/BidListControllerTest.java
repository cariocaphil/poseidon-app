package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {

  @Mock
  private BidListService bidListService;

  @Mock
  private Model model;

  @InjectMocks
  private BidListController controller;

  private Bid bid;
  private BindingResult bindingResult;

  @BeforeEach
  public void setup() {
    bid = new Bid();
    bid.setAccount("Test Account");
    bid.setType("Type");
    bindingResult = new BeanPropertyBindingResult(bid, "bid");
  }

  @Test
  public void testGetBidById_Found() {
    when(bidListService.findById(any())).thenReturn(Optional.of(bid));
    ResponseEntity<Bid> response = controller.getBidById(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(bid);
  }

  @Test
  public void testGetBidById_NotFound() {
    when(bidListService.findById(any())).thenReturn(Optional.empty());
    ResponseEntity<Bid> response = controller.getBidById(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

}
