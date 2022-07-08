package com.badals.checkout.domain.pojo;

import com.badals.checkout.xtra.PaymentType;
import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaymentProfile implements Serializable {
   List<PaymentDef> payments = new ArrayList<>();

   public PaymentDef getType(PaymentType type) {
      return payments.stream().filter(i -> i.name.equals(type.ref)).findFirst().orElse(null);
   }

   public void addPayment(PaymentDef def) {
      payments.add(def);
   }

   public static String findSkByName(PaymentProfile profile, String name) {
      return profile.getPayments().stream().filter(x -> x.name.equals(name)).findFirst().get().getSk();
   }
/*
   private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
   {
      payments = (ArrayList) aInputStream.readObject();
   }

   private void writeObject(ObjectOutputStream aOutputStream) throws IOException
   {
      aOutputStream.writeObject(payments);
   }*/
}

