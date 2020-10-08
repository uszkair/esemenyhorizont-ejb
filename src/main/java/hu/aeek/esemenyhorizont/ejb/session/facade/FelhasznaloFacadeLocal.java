package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FelhasznaloFacadeLocal {
   void create(Felhasznalo var1);

   void edit(Felhasznalo var1);

   void remove(Felhasznalo var1);

   Felhasznalo find(Object var1);

   List<Felhasznalo> findAll();

   List<Felhasznalo> findAllNotDeleted();

   List<Felhasznalo> findRange(int[] var1);

   int count();
}
