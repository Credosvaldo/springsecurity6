package com.example.springsecurity6.enuns;

public enum Role {
  BASIC(1),
  ADMIN(2);

  private final int value;

  Role(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static Role fromValue(int value) {
    for (Role role : Role.values()) {
      if (role.getValue() == value) {
        return role;
      }
    }
    throw new IllegalArgumentException("Unknown role value: " + value);
  }

}
