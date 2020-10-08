package hu.aeek.esemenyhorizont.ejb.session.facade;

import hu.aeek.esemenyhorizont.ejb.entity.Kategoria;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(
   mappedName = "KategoriaFacade",
   name = "KategoriaFacade"
)
@LocalBean
public class KategoriaFacade extends AbstractFacade<Kategoria> implements KategoriaFacadeLocal {
   @PersistenceContext(
      unitName = "hu.aeek.esemenyhorizont.ORACLE_PU"
   )
   private EntityManager em;

   protected EntityManager getEntityManager() {
      return this.em;
   }

   public KategoriaFacade() {
      super(Kategoria.class);
   }
}
