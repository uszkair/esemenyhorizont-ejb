package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Esemeny;
import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.security.sasl.AuthenticationException;

@Local
public interface EsemenyFacadeLocal {
   void create(Esemeny var1);

   Esemeny create(Esemeny var1, Felhasznalo var2) throws AuthenticationException;

   void edit(Esemeny var1);

   Esemeny edit(Esemeny var1, Felhasznalo var2) throws AuthenticationException;

   void remove(Esemeny var1);

   void remove(Esemeny var1, Felhasznalo var2) throws AuthenticationException;

   void delete(Esemeny var1, Felhasznalo var2) throws AuthenticationException;

   Esemeny find(Object var1);

   List<Esemeny> findAll();

   List<Esemeny> findRange(int[] var1);

   List<Esemeny> findAllByIntezetId(Integer var1);

   List<Esemeny> findAllByTigId(Integer var1);

   List<Esemeny> findAllBetween(Date var1, Date var2);

   List<Esemeny> findAllByTigBetween(int var1, Date var2, Date var3);

   List<Esemeny> findAllByIntezetIdBetween(int var1, Date var2, Date var3);

   Esemeny findById(Integer var1);

   int count();
}
