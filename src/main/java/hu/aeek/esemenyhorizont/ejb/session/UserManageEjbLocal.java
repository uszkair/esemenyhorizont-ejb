package hu.aeek.esemenyhorizont.ejb.session;

import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserManageEjbLocal {
   Felhasznalo findUserByLoginAndPassword(String var1, String var2);

   boolean isUserExists(String var1) throws Exception;

   void registNewUser(Felhasznalo var1) throws Exception;

   void activateUser(Felhasznalo var1, String var2) throws Exception;

   Felhasznalo modifyUser(Felhasznalo var1) throws Exception;

   Felhasznalo findUserByUserName(String var1) throws Exception;

   List<Felhasznalo> getAllUsersByIntezmenyId(Integer var1) throws Exception;

   List<Felhasznalo> getAllUsers() throws Exception;

   Felhasznalo findUsersById(Integer var1);
}
