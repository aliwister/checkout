package com.badals.checkout.domain;

import com.badals.checkout.aop.tenant.TenantSupport;
import com.badals.checkout.domain.enumeration.DiscountReductionType;
import com.badals.checkout.domain.pojo.PriceMap;
import com.badals.checkout.domain.pojo.RewardInfo;
import com.badals.checkout.domain.pojo.RewardRules;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "reward", catalog = "profileshop")
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class Reward extends Auditable<Long> implements Serializable, TenantSupport {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reward_type")
    private String rewardType;

    @Column(name = "points")
    private Integer points;

    @Type(type = "json")
    @Column(name = "info", columnDefinition = "string")
    private List<RewardInfo> rewardInfo;

    @Type(type = "json")
    @Column(name = "rules", columnDefinition = "string")
    private List<RewardRules> rewardRules;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_reduction_type")
    private DiscountReductionType discountReductionType;

    @Type(type = "json")
    @Column(name = "discount_reduction_value", columnDefinition = "string")
    private PriceMap discountReductionValue;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "times_exchanged")
    private Integer timesExchanged;

    @Column(name = "active")
    private Boolean active;

}