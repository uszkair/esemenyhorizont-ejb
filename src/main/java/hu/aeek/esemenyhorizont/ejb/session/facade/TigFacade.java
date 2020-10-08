package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Tig;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(
   mappedName = "TigFacade",
   name = "TigFacade"
)
@LocalBean
public class TigFacade extends AbstractFacade<Tig> implements TigFacadeLocal {
   @PersistenceContext(
      unitName = "hu.aeek.esemenyhorizont.ORACLE_PU"
   )
   private EntityManager em;

   protected EntityManager getEntityManager() {
      return this.em;
   }

   public TigFacade() {
      super(Tig.class);
   }
}
