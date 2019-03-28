package com.spx.session;

import org.apache.shiro.authc.UsernamePasswordToken;

public class HashedUserPasswordToken extends UsernamePasswordToken {

  private final String hash;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public HashedUserPasswordToken(String username, String password, String hash) {
    super(username, password);
    this.hash = hash;
  }

  public String getHash() {
    return hash;
  }

}
