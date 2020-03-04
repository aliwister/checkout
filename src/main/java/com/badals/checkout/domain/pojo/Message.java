package com.badals.checkout.domain.pojo;

import lombok.Data;

@Data
public class Message {
    String value;

    public Message(String value) {
        this.value = value;
    }
}
