package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Esemeny;
import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import hu.aeek.esemenyhorizont.ejb.entity.Intezet;
import hu.aeek.esemenyhorizont.ejb.session.email.EmailSender;
import hu.aeek.esemenyhorizont.security.authorization.Authorizator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.sasl.AuthenticationException;

@Stateless(
   mappedName = "EsemenyFacade",
   name = "EsemenyFacade"
)
@LocalBean
public class EsemenyFacade extends AbstractFacade<Esemeny> implements EsemenyFacadeLocal {
   @PersistenceContext(
      unitName = "hu.aeek.esemenyhorizont.ORACLE_PU"
   )
   private EntityManager em;
   @EJB(
      beanName = "IntezetFacade"
   )
   IntezetFacade intezetFacade;
   @EJB(
      beanName = "emailSender"
   )
   EmailSender email = new EmailSender();

   protected EntityManager getEntityManager() {
      return this.em;
   }

   public EsemenyFacade() {
      super(Esemeny.class);
   }

   public Esemeny findById(Integer id) {
      TypedQuery<Esemeny> query = this.em.createNamedQuery("Esemeny.findById", Esemeny.class);
      query.setParameter("id", id);
      List<Esemeny> results = query.getResultList();
      return !results.isEmpty() && results.size() <= 1 ? (Esemeny)results.get(0) : null;
   }

   public List<Esemeny> findAll() {
      List<Esemeny> result = super.findAll();
      return result;
   }

   public List<Esemeny> findAllByIntezetId(Integer id) {
      TypedQuery<Esemeny> query = this.em.createNamedQuery("Esemeny.findByIntezetId", Esemeny.class);
      query.setParameter("intezetId", id);
      List<Esemeny> results = query.getResultList();
      return results;
   }

   public Esemeny create(Esemeny esemeny, Felhasznalo user) throws AuthenticationException {
      Authorizator auth = new Authorizator();
      if (auth.createFilter(esemeny, user)) {
         Esemeny es = (Esemeny)this.em.merge(esemeny);
         return es;
      } else {
         throw new AuthenticationException();
      }
   }

   public Esemeny edit(Esemeny esemeny, Felhasznalo user) throws AuthenticationException {
      Authorizator auth = new Authorizator();
      if (auth.modifyFilter(esemeny, user)) {
         return (Esemeny)this.em.merge(esemeny);
      } else {
         throw new AuthenticationException();
      }
   }

   public void remove(Esemeny esemeny, Felhasznalo user) throws AuthenticationException {
      Authorizator auth = new Authorizator();
      if (auth.removeFilter(esemeny, user)) {
         this.em.remove(this.em.contains(esemeny) ? esemeny : this.em.merge(esemeny));
      } else {
         throw new AuthenticationException();
      }
   }

   public void delete(Esemeny esemeny, Felhasznalo user) throws AuthenticationException {
      Authorizator auth = new Authorizator();
      if (auth.deleteFilter(esemeny, user)) {
         esemeny = (Esemeny)this.em.merge(esemeny);
         this.em.remove(esemeny);
         this.em.flush();
      } else {
         throw new AuthenticationException();
      }
   }

   public List<Esemeny> findAllByTigId(Integer id) {
      List<Esemeny> results = new ArrayList();
      TypedQuery<Intezet> intezetQuery = this.em.createNamedQuery("Esemeny.findByIntezetId", Intezet.class);
      intezetQuery.setParameter("id", id);
      List<Intezet> intezetek = intezetQuery.getResultList();
      Iterator var5 = intezetek.iterator();

      while(var5.hasNext()) {
         Intezet i = (Intezet)var5.next();
         List<Esemeny> subResult = this.findAllByIntezetId(i.getId());
         results.addAll(subResult);
      }

      return results;
   }

   public List<Esemeny> findAllBetween(Date startDate, Date endDate) {
      TypedQuery<Esemeny> query = this.em.createNamedQuery("Esemeny.findByBetweenEventDate", Esemeny.class);
      query.setParameter("startDate", startDate);
      query.setParameter("endDate", endDate);
      List<Esemeny> results = query.getResultList();
      return results;
   }

   public List<Esemeny> findAllByTigBetween(int tigId, Date startDate, Date endDate) {
      List<Esemeny> results = new ArrayList();
      List<Esemeny> allBetween = this.findAllBetween(startDate, endDate);
      if (tigId > 0) {
         Iterator var6 = allBetween.iterator();

         while(var6.hasNext()) {
            Esemeny e = (Esemeny)var6.next();
            if (e.getIntezet().getTig().getId() == tigId) {
               ((List)results).add(e);
            }
         }
      } else {
         results = allBetween;
      }

      return (List)results;
   }

   public List<Esemeny> findAllByIntezetIdBetween(int id, Date startDate, Date endDate) {
      List<Esemeny> results = new ArrayList();
      List<Esemeny> allBetween = this.findAllBetween(startDate, endDate);
      if (id > 0) {
         Iterator var6 = allBetween.iterator();

         while(var6.hasNext()) {
            Esemeny e = (Esemeny)var6.next();
            if (e.getIntezet().getId() == id) {
               ((List)results).add(e);
            }
         }
      } else {
         results = allBetween;
      }

      return (List)results;
   }
}
