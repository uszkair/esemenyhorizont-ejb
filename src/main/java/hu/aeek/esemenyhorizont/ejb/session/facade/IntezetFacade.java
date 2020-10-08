package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Intezet;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless(
   mappedName = "IntezetFacade",
   name = "IntezetFacade"
)
@LocalBean
public class IntezetFacade extends AbstractFacade<Intezet> implements IntezetFacadeLocal {
   @PersistenceContext(
      unitName = "hu.aeek.esemenyhorizont.ORACLE_PU"
   )
   private EntityManager em;

   protected EntityManager getEntityManager() {
      return this.em;
   }

   public IntezetFacade() {
      super(Intezet.class);
   }

   public List<Intezet> findAllNotDeleted() {
      TypedQuery<Intezet> query = this.em.createNamedQuery("Intezet.findByIsdeleted", Intezet.class);
      query.setParameter("isdeleted", 'N');
      List<Intezet> results = query.getResultList();
      return results;
   }
}
