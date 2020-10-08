package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Kategoria;
import java.util.List;
import javax.ejb.Local;

@Local
public interface KategoriaFacadeLocal {
   void create(Kategoria var1);

   void edit(Kategoria var1);

   void remove(Kategoria var1);

   Kategoria find(Object var1);

   List<Kategoria> findAll();

   List<Kategoria> findRange(int[] var1);

   int count();
}
