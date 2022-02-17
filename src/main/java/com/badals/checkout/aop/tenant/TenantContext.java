package com.badals.checkout.aop.tenant;


public class TenantContext {
   private static ThreadLocal<String> currentProfile = new InheritableThreadLocal<>();
   private static ThreadLocal<Long> currentProfileId = new InheritableThreadLocal<>();

   public static String getCurrentProfile() {
      return currentProfile.get();
   }

   public static void setCurrentProfile(String tenant) {
      currentProfile.set(tenant);
   }
   public static Long getCurrentProfileId() {
      return currentProfileId.get();
   }

   public static void setCurrentProfileId(Long tenant) {
      currentProfileId.set(tenant);
   }


   public static void clear() {
      currentProfile.set(null);
   }
}