package hu.aeek.esemenyhorizont.ejb.session;

import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import hu.aeek.esemenyhorizont.ejb.session.facade.FelhasznaloFacade;
import hu.aeek.esemenyhorizont.security.authentication.ActiveDirectory;
import hu.aeek.esemenyhorizont.security.authentication.PasswordService;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "userManageEjb")
@LocalBean
public class UserManageEjb implements Serializable {
   @PersistenceContext(unitName = "hu.aeek.esemenyhorizont.ORACLE_PU")
   EntityManager em;

   @EJB(beanName = "FelhasznaloFacade")
   FelhasznaloFacade felhasznaloFacade;

   public Felhasznalo findUserByLoginAndPassword(String login, String password) {
      Felhasznalo result = null;
      for (Felhasznalo d : this.felhasznaloFacade.findAll()) {
         if (d.getLogin().equals(login)) {
            Logger.getLogger("user123").log(Level.INFO, "user {0}.", new Object[] { d});
            if (d.isADAuthenticated()) {
               result = authenticateFromAD(d, login, password);
               continue;
            }
            result = authenticateFromLocalDB(d, login, password);
         }
      }
      return result;
   }

   public Felhasznalo findUserByUserName(String name) {
      Felhasznalo result = null;
      for (Felhasznalo d : this.felhasznaloFacade.findAll()) {
         if (d.getName().equals(name)) {
            result = d;
            break;
         }
      }
      return result;
   }

   public void registNewUser(Felhasznalo user) throws Exception {
      this.em.persist(user);
   }

   public boolean isUserExists(String username) throws Exception {
      boolean result = false;
      for (Felhasznalo d : this.felhasznaloFacade.findAll()) {
         if (d.getLogin().equals(username)) {
            result = true;
            break;
         }
      }
      return result;
   }

   public void activateUser(Felhasznalo user, String password) throws Exception {
      user.setPassword(password);
      user.setIsactive(Character.valueOf('Y'));
      this.em.merge(user);
   }

   public Felhasznalo modifyUser(Felhasznalo user) throws Exception {
      return (Felhasznalo)this.em.merge(user);
   }

   public Felhasznalo authenticateFromAD(Felhasznalo nominee, String login, String password) {
      Felhasznalo result = null;
      try {
         ActiveDirectory ad = new ActiveDirectory();
         Logger.getLogger("user123AD1245").log(Level.INFO, "ad {0}.", new Object[] { ad});
         if (ad.login(login, password) &&
                 nominee.getLogin().equals(login)) {
            Logger.getLogger("user123AD").log(Level.INFO, "ad {0}.", new Object[] { login});
            result = nominee;
         }
      } catch (NamingException namingException) {}
      return result;
   }

   public Felhasznalo authenticateFromLocalDB(Felhasznalo nominee, String login, String password) {
      Felhasznalo result = null;
      if (nominee.getLogin().equals(login))
         try {
            if (PasswordService.authenticate(password, nominee.getPassword(), nominee.getSalt()))
               result = nominee;
         } catch (NoSuchAlgorithmException|java.security.spec.InvalidKeySpecException ex) {
            Logger.getLogger(UserManageEjb.class.getName()).log(Level.SEVERE, (String)null, ex);
         }
      return result;
   }

   public List<Felhasznalo> getAllUsers() throws Exception {
      return this.felhasznaloFacade.findAll();
   }

   public Felhasznalo findUsersById(Integer userid) {
      Felhasznalo result = null;
      for (Felhasznalo d : this.felhasznaloFacade.findAll()) {
         if (Objects.equals(d.getId(), userid)) {
            result = d;
            break;
         }
      }
      return result;
   }
}
