package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless(
   mappedName = "FelhasznaloFacade",
   name = "FelhasznaloFacade"
)
@LocalBean
public class FelhasznaloFacade extends AbstractFacade<Felhasznalo> implements FelhasznaloFacadeLocal {
   @PersistenceContext(
      unitName = "hu.aeek.esemenyhorizont.ORACLE_PU"
   )
   private EntityManager em;

   protected EntityManager getEntityManager() {
      return this.em;
   }

   public FelhasznaloFacade() {
      super(Felhasznalo.class);
   }

   public List<Felhasznalo> findAllNotDeleted() {
      TypedQuery<Felhasznalo> query = this.em.createNamedQuery("Felhasznalo.findByIsdeleted", Felhasznalo.class);
      query.setParameter("isdeleted", 'N');
      List<Felhasznalo> results = query.getResultList();
      return results;
   }
}
