package com.badals.checkout.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
    * A Product.
    */
   @Entity
   @Data
   @Table(catalog="profileshop", name = "zone")
   public class Zone implements Serializable {

      private static final long serialVersionUID = 1L;

      public enum ZoneLevel {
         WORLD(0), CONTINENT(1), COUNTRY(2), STATE(3), LOCALITY(4), NEIGHBORHOOD(5);
         private Integer code;
         ZoneLevel(Integer code) {
            this.code = code;
         }
      }

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private String code;
      private String name;

      @Column(name="part_of")
      private String partOf;

      @Enumerated(EnumType.ORDINAL)
      public ZoneLevel level;

      private Boolean active;



      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         }
         if (!(o instanceof State)) {
            return false;
         }
         return id != null && id.equals(((State) o).getId());
      }

      @Override
      public int hashCode() {
         return 31;
      }

      @Override
      public String toString() {
         return "Zone{" +
                 "id=" + id +
                 ", name='" + name + '\'' +
                 ", code='" + code + '\'' +

                 '}';
      }
   }
