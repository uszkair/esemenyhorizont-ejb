package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Intezet;
import java.util.List;
import javax.ejb.Local;

@Local
public interface IntezetFacadeLocal {
   void create(Intezet var1);

   void edit(Intezet var1);

   void remove(Intezet var1);

   Intezet find(Object var1);

   List<Intezet> findAll();

   List<Intezet> findAllNotDeleted();

   List<Intezet> findRange(int[] var1);

   int count();
}
