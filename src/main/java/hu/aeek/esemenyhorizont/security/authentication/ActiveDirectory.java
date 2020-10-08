package hu.aeek.esemenyhorizont.security.authentication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.CommunicationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ActiveDirectory {
   private final String[] userAttributes = new String[]{"distinguishedName", "cn", "name", "uid", "sn", "givenname", "memberOf", "samaccountname", "userPrincipalName", "employeeID", "pwdLastSet"};

   public ActiveDirectory() {
   }

   public boolean login(String login, String password) throws NamingException {
      ResourceBundle props = ResourceBundle.getBundle("ldap");
      Logger.getLogger("user123AD1245propos").log(Level.INFO, "ad {0}.", new Object[] { props});

      String ldapDomain = props.getString("ldapDomain");
      String ldapServer = props.getString("ldapServer");
      String securityGroup = props.getString("securityGroup");
      Logger.getLogger("user123AD1245proposConn").log(Level.INFO, "ad {0}.",
              new Object[] { login+"; "+ password+"; "+ ldapDomain+"; "+ ldapServer});

      LdapContext ADconn = this.getConnection(login, password, ldapDomain, ldapServer);
      Logger.getLogger("ActiveDirectoryADconn").log(Level.INFO, "ad {0}.", new Object[] { ADconn});
      ActiveDirectory.User ADuser = this.getUser(login, ADconn);
      Logger.getLogger("ActiveDirectory123").log(Level.INFO, "ad {0}.", new Object[] { ADuser});
      Logger.getLogger("ActiveDirectory123ldapDomain").log(Level.INFO, "ad {0}.", new Object[] { ldapDomain});
      Logger.getLogger("ActiveDirectory123ldapServer").log(Level.INFO, "ad {0}.", new Object[] { ldapServer});

      return ADuser != null;
   }

   public LdapContext getConnection(String username, String password) throws NamingException {
      return this.getConnection(username, password, (String)null, (String)null);
   }

   public LdapContext getConnection(String username, String password, String domainName) throws NamingException {
      return this.getConnection(username, password, domainName, (String)null);
   }

   public LdapContext getConnection(String username, String password, String domainName, String serverName) throws NamingException {
      if (domainName == null) {
         try {
            String fqdn = InetAddress.getLocalHost().getCanonicalHostName();
            if (fqdn.split("\\.").length > 1) {
               domainName = fqdn.substring(fqdn.indexOf(".") + 1);
            }
         } catch (UnknownHostException var9) {
         }
      }

      if (password != null) {
         password = password.trim();
         if (password.length() == 0) {
            password = null;
         }
      }

      Hashtable props = new Hashtable();
      String principalName = username + "@" + domainName;
      Logger.getLogger("ldapURL123userPrincipalName33PAss").log(Level.INFO, "ad {0}.", new Object[] { principalName});

      props.put("java.naming.security.principal", principalName);
      if (password != null) {
         props.put("java.naming.security.credentials", password);
      }

      String ldapURL = "ldap://" + (serverName == null ? domainName : serverName + "." + domainName) + '/'+"CN=karterites";
      props.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
      props.put("java.naming.provider.url", ldapURL);
      Logger.getLogger("ldapURL123").log(Level.INFO, "ad {0}.", new Object[] { ldapURL});

      try {
         return new InitialLdapContext(props, (Control[])null);
      } catch (Exception e) {
         Logger.getLogger("ldapURL123555").log(Level.INFO, "ad {0}.", new Object[] { e});
      }
      Logger.getLogger("ldapURL12355544").log(Level.INFO, "ad {0}.", new Object[] { ldapURL});
      return new InitialLdapContext(props, (Control[])null);
   }

   public ActiveDirectory.User getUser(String username, LdapContext context) {
      try {
         String domainName = null;
         String authenticatedUser;
         Logger.getLogger("ldapURL123username").log(Level.INFO, "ad {0}.", new Object[] { username});

         if (username.contains("@")) {
            username = username.substring(0, username.indexOf("@"));
            domainName = username.substring(username.indexOf("@") + 1);
            Logger.getLogger("ldapURL123username12").log(Level.INFO, "ad {0}.", new Object[] { domainName});

         } else if (username.contains("\\")) {
            username = username.substring(0, username.indexOf("\\"));
            domainName = username.substring(username.indexOf("\\") + 1);
            Logger.getLogger("ldapURL123username123").log(Level.INFO, "ad {0}.", new Object[] { username+" "+domainName});

         } else {
            authenticatedUser = (String)context.getEnvironment().get("java.naming.security.principal");
            Logger.getLogger("ldapURL123authenticatedUser").log(Level.INFO, "ad {0}.", new Object[] { authenticatedUser});
            if (authenticatedUser.contains("@")) {
               domainName = authenticatedUser.substring(authenticatedUser.indexOf("@") + 1);
               Logger.getLogger("ldapURL123authenticatedUser1").log(Level.INFO, "ad {0}.", new Object[] { authenticatedUser});

            }
         }

         if (domainName != null) {
            authenticatedUser = username + "@" + domainName;
            SearchControls controls = new SearchControls();
            controls.setSearchScope(2);
            controls.setReturningAttributes(this.userAttributes);
            Logger.getLogger("ldapURL123userAttributes").log(Level.INFO, "ad {0}.", new Object[] { this.userAttributes});

            NamingEnumeration<SearchResult> answer = context.search(this.toDC(domainName), "(& (userPrincipalName=" + authenticatedUser + ")(objectClass=user))", controls);
            Logger.getLogger("ldapURL123authenticatedUser12").log(Level.INFO, "ad {0}.", new Object[] { answer});

            if (answer.hasMore()) {
               Attributes attr = ((SearchResult)answer.next()).getAttributes();
               Attribute user = attr.get("userPrincipalName");
               Logger.getLogger("ldapURL123userPrincipalName").log(Level.INFO, "ad {0}.", new Object[] { user});

               if (user != null) {
                  Logger.getLogger("ldapURL123userPrincipalName23").log(Level.INFO, "ad {0}.", new Object[] { user});
                  return new ActiveDirectory.User(attr);
               }
            }
         }
      } catch (NamingException var9) {
         Logger.getLogger("ldapExceptions").log(Level.INFO, "ad {0}.", new Object[] { var9});
      }

      return null;
   }

   private String toDC(String domainName) {
      StringBuilder buf = new StringBuilder();
      String[] var3 = domainName.split("\\.");
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String token = var3[var5];
         if (token.length() != 0) {
            if (buf.length() > 0) {
               buf.append(",");
            }

            buf.append("DC=").append(token);
         }
      }

      return buf.toString();
   }

   public class User {
      private final String distinguishedName;
      private final String userPrincipal;
      private final String commonName;
      private final String employeeID;
      private final String groups;
      private final long pwdLastSet;
      private final Attributes attributes;
      private final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
         public boolean verify(String hostname, SSLSession session) {
            return true;
         }
      };
      private TrustManager[] TRUST_ALL_CERTS = new TrustManager[]{new X509TrustManager() {
         public X509Certificate[] getAcceptedIssuers() {
            return null;
         }

         public void checkClientTrusted(X509Certificate[] certs, String authType) {
         }

         public void checkServerTrusted(X509Certificate[] certs, String authType) {
         }
      }};

      public User(Attributes attr) throws NamingException {
         this.attributes = attr;
         this.userPrincipal = (String)attr.get("userPrincipalName").get();
         this.commonName = (String)attr.get("cn").get();
         this.distinguishedName = (String)attr.get("distinguishedName").get();
         this.employeeID = attr.get("employeeID").get().toString();
         this.pwdLastSet = Long.parseLong(attr.get("pwdLastSet").get().toString());
         this.groups = (String)attr.get("memberOf").get();
      }

      public String getGroups() {
         return this.groups;
      }

      public boolean hasGroup(String group) {
         return this.attributes.toString().contains(group);
      }

      public String getUserPrincipal() {
         return this.userPrincipal;
      }

      public String getCommonName() {
         return this.commonName;
      }

      public String getDistinguishedName() {
         return this.distinguishedName;
      }

      public String getEmployeeID() {
         return this.employeeID;
      }

      public long getPwdLastSet() {
         return this.pwdLastSet;
      }

      public String toString() {
         return this.getDistinguishedName();
      }

      public void changePassword(String oldPass, String newPass, boolean trustAllCerts, LdapContext context) throws IOException, NamingException {
         String dn = this.getDistinguishedName();
         StartTlsResponse tls = null;

         try {
            tls = (StartTlsResponse)context.extendedOperation(new StartTlsRequest());
         } catch (Exception var11) {
            throw new IOException("Failed to establish SSL connection to the Domain Controller. Is LDAPS enabled?");
         }

         if (trustAllCerts) {
            tls.setHostnameVerifier(this.DO_NOT_VERIFY);
            SSLSocketFactory sf = null;

            try {
               SSLContext sc = SSLContext.getInstance("TLS");
               sc.init((KeyManager[])null, this.TRUST_ALL_CERTS, (SecureRandom)null);
               sf = sc.getSocketFactory();
            } catch (NoSuchAlgorithmException var9) {
            } catch (KeyManagementException var10) {
            }

            tls.negotiate(sf);
         } else {
            tls.negotiate();
         }

         try {
            ModificationItem[] modificationItems = new ModificationItem[]{new ModificationItem(3, new BasicAttribute("unicodePwd", this.getPassword(oldPass))), new ModificationItem(1, new BasicAttribute("unicodePwd", this.getPassword(newPass)))};
            context.modifyAttributes(dn, modificationItems);
         } catch (InvalidAttributeValueException var12) {
            String error = var12.getMessage().trim();
            if (error.startsWith("[") && error.endsWith("]")) {
               error = error.substring(1, error.length() - 1);
            }

            System.err.println(error);
            tls.close();
            throw new NamingException("Az új nem felel meg a tartomány hosszúsági, összetettségi vagy előzmény követelményének!");
         } catch (NamingException var13) {
            tls.close();
            throw var13;
         }

         tls.close();
      }

      private byte[] getPassword(String newPass) {
         String quotedPassword = "\"" + newPass + "\"";
         char[] unicodePwd = quotedPassword.toCharArray();
         byte[] pwdArray = new byte[unicodePwd.length * 2];

         for(int i = 0; i < unicodePwd.length; ++i) {
            pwdArray[i * 2 + 1] = (byte)(unicodePwd[i] >>> 8);
            pwdArray[i * 2 + 0] = (byte)(unicodePwd[i] & 255);
         }

         return pwdArray;
      }
   }
}
