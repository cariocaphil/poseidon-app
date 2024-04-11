package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidListService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static java.util.EnumSet.allOf;
import static javax.swing.UIManager.get;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    bid = new Bid();
    bid.setAccount("Test Account");
    bid.setType("Type");
    bindingResult = new BeanPropertyBindingResult(bid, "bid");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

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

  @Test
  public void testBidListFindAll() throws Exception {
    // Given
    Bid mockBid = new Bid();
    mockBid.setId(1);
    mockBid.setAccount("Account Test");
    mockBid.setType("Type Test");
    mockBid.setBidQuantity(10.0);

    List<Bid> bids = new ArrayList<>();
    bids.add(mockBid);

    when(bidListService.findAll()).thenReturn(bids);

    // When & Then
    mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("bidLists"))
        .andExpect(model().attribute("bidLists", hasSize(1)))
        .andExpect(view().name("bidList/list"));
  }

}
