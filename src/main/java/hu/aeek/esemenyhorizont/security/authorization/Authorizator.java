package hu.aeek.esemenyhorizont.security.authorization;

import hu.aeek.esemenyhorizont.ejb.entity.Esemeny;
import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Authorizator {
   public Authorizator() {
   }

   public boolean createFilter(Esemeny ugy, Felhasznalo users) {
      return true;
   }

   public boolean removeFilter(Esemeny ugy, Felhasznalo users) {
      return true;
   }

   public boolean deleteFilter(Esemeny ugy, Felhasznalo users) {
      return true;
   }

   public boolean modifyFilter(Esemeny ugy, Felhasznalo users) {
      return true;
   }

   public boolean readerFilter(Esemeny ugy, Felhasznalo users) {
      return true;
   }

   public List<Esemeny> readerFilter(List<Esemeny> esemenyek, Felhasznalo user) {
      List<Esemeny> result = new ArrayList();

      Esemeny var5;
      for(Iterator var4 = esemenyek.iterator(); var4.hasNext(); var5 = (Esemeny)var4.next()) {
      }

      return result;
   }
}
