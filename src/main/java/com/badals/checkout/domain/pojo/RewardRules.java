package com.badals.checkout.domain.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RewardRules implements Serializable {
    private Integer minCartAmount;
}
