package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationRequest {
  private String username;
  private String password;
  private String fullname;
  private String role;

  // Constructor for all fields
  public UserRegistrationRequest(String username, String password, String fullname, String role) {
    this.username = username;
    this.password = password;
    this.fullname = fullname;
    this.role = role;
  }

}
