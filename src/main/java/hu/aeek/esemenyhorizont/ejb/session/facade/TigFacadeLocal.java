package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Tig;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TigFacadeLocal {
   void create(Tig var1);

   void edit(Tig var1);

   void remove(Tig var1);

   Tig find(Object var1);

   List<Tig> findAll();

   List<Tig> findRange(int[] var1);

   int count();
}
