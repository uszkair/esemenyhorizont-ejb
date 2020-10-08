package hu.aeek.esemenyhorizont.ejb.session.email;

import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface EmailSenderLocal {
   Felhasznalo findUserEmailByUsername(String var1) throws Exception;

   boolean sendEmail(String var1, String var2, Map<String, String> var3);
}
