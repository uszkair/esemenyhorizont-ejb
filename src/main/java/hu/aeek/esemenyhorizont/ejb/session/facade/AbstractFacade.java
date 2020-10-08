package hu.aeek.esemenyhorizont.ejb.session.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractFacade<T> {
   private final Class<T> entityClass;

   public AbstractFacade(Class<T> entityClass) {
      this.entityClass = entityClass;
   }

   protected abstract EntityManager getEntityManager();

   public void create(T entity) {
      this.getEntityManager().persist(entity);
   }

   public void edit(T entity) {
      this.getEntityManager().merge(entity);
   }

   public void remove(T entity) {
      this.getEntityManager().remove(this.getEntityManager().merge(entity));
   }

   public T find(Object id) {
      return this.getEntityManager().find(this.entityClass, id);
   }

   public List<T> findAll() {
      CriteriaQuery cq = this.getEntityManager().getCriteriaBuilder().createQuery();
      cq.select(cq.from(this.entityClass));
      return this.getEntityManager().createQuery(cq).getResultList();
   }

   public List<T> findRange(int[] range) {
      CriteriaQuery cq = this.getEntityManager().getCriteriaBuilder().createQuery();
      cq.select(cq.from(this.entityClass));
      Query q = this.getEntityManager().createQuery(cq);
      q.setMaxResults(range[1] - range[0] + 1);
      q.setFirstResult(range[0]);
      return q.getResultList();
   }

   public int count() {
      CriteriaQuery cq = this.getEntityManager().getCriteriaBuilder().createQuery();
      Root<T> rt = cq.from(this.entityClass);
      cq.select(this.getEntityManager().getCriteriaBuilder().count(rt));
      Query q = this.getEntityManager().createQuery(cq);
      return ((Number)q.getSingleResult()).intValue();
   }
}
