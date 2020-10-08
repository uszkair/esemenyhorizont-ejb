package hu.aeek.esemenyhorizont.ejb.session.email;

import hu.aeek.esemenyhorizont.ejb.entity.Felhasznalo;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(
   mappedName = "emailSender",
   name = "emailSender"
)
@LocalBean
public class EmailSender implements EmailSenderLocal {
   @PersistenceContext(
      unitName = "hu.aeek.esemenyhorizont.ORACLE_PU"
   )
   EntityManager em;
   @Resource(
      lookup = "java:/emailSender"
   )
   Session emailSender;
   static ExecutorService emailExecutor;

   public EmailSender() {
   }

   private static ExecutorService getExecutorService() {
      if (emailExecutor == null) {
         emailExecutor = Executors.newCachedThreadPool();
      }

      return emailExecutor;
   }

   public boolean sendEmail(String address, String templateKey, Map<String, String> replaceMap) {
      ResourceBundle props = ResourceBundle.getBundle("email");
      String from_address = props.getString("from_address");
      String from_alias = props.getString("from_alias");
      String template = props.getString(templateKey);
      String subject = template.split("####")[0];
      String content = template.split("####")[1];

      String key;
      for(Iterator var10 = replaceMap.keySet().iterator(); var10.hasNext(); content = content.replaceAll(key, (String)replaceMap.get(key))) {
         key = (String)var10.next();
      }

      MimeMessage message = new MimeMessage(this.emailSender);

      try {
         message.setFrom(new InternetAddress(from_address, from_alias));
         message.addRecipient(RecipientType.TO, new InternetAddress(address));
         message.setSubject(subject);
         message.setText(content);
         getExecutorService().execute(() -> {
            try {
               Transport.send(message);
            } catch (MessagingException var2) {
               Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, (String)null, var2);
            }

         });
         return true;
      } catch (MessagingException | UnsupportedEncodingException var12) {
         return false;
      }
   }

   public Felhasznalo findUserEmailByUsername(String username) {
      Query query = this.em.createNamedQuery("Users.findByFelhasznalo").setParameter("felhasznalo", username);

      try {
         Felhasznalo result = (Felhasznalo)query.getSingleResult();
         return result;
      } catch (NonUniqueResultException | NoResultException var5) {
         return null;
      }
   }
}
