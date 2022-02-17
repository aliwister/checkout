package com.badals.checkout.controller;

import lombok.Data;

import java.util.List;

@Data
public class CreatePayment {
   List<Item> items;
}

@Data
class Item {
   String id;
}