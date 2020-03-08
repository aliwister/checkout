package com.badals.checkout.domain;

import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.LineItem;
import com.badals.checkout.domain.pojo.PaymentMethod;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

   /**
    * A Product.
    */
   @Entity
   @Data
   @Table(name = "city")
   public class City implements Serializable {

      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private String code;
      private String value;
      private String lang;

      @ManyToOne
      private State state;


      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         }
         if (!(o instanceof com.badals.checkout.domain.State)) {
            return false;
         }
         return id != null && id.equals(((com.badals.checkout.domain.State) o).getId());
      }

      @Override
      public int hashCode() {
         return 31;
      }

      @Override
      public String toString() {
         return "Cart{" +
                 "id=" + id +
                 ", name='" + value + '\'' +
                 ", phone='" + code + '\'' +

                 '}';
      }
   }
