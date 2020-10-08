package hu.aeek.esemenyhorizont.ejb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({@NamedQuery(name = "Felhasznalo.findAll", query = "SELECT f FROM Felhasznalo f"), @NamedQuery(name = "Felhasznalo.findById", query = "SELECT f FROM Felhasznalo f WHERE f.id = :id"), @NamedQuery(name = "Felhasznalo.findByKey", query = "SELECT f FROM Felhasznalo f WHERE f.key = :key"), @NamedQuery(name = "Felhasznalo.findByName", query = "SELECT f FROM Felhasznalo f WHERE f.name = :name"), @NamedQuery(name = "Felhasznalo.findByLogin", query = "SELECT f FROM Felhasznalo f WHERE f.login = :login"), @NamedQuery(name = "Felhasznalo.findByEmail", query = "SELECT f FROM Felhasznalo f WHERE f.email = :email"), @NamedQuery(name = "Felhasznalo.findByPassword", query = "SELECT f FROM Felhasznalo f WHERE f.password = :password"), @NamedQuery(name = "Felhasznalo.findByIsactive", query = "SELECT f FROM Felhasznalo f WHERE f.isactive = :isactive"), @NamedQuery(name = "Felhasznalo.findByIsdeleted", query = "SELECT f FROM Felhasznalo f WHERE f.isdeleted = :isdeleted")})
public class Felhasznalo implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @Basic(optional = false)
   @NotNull
   @Column(nullable = false, precision = 38, scale = 0)
   @SequenceGenerator(name = "FELHASZNALO_SEQ", sequenceName = "FELHASZNALO_SEQ", allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FELHASZNALO_SEQ")
   private Integer id;

   @Size(max = 50)
   @Column(length = 50)
   private String key;

   @Size(max = 250)
   @Column(length = 250)
   private String name;

   @Size(max = 250)
   @Column(length = 250)
   private String login;

   @Size(max = 50)
   @Column(length = 50)
   private String email;

   @Size(max = 1000)
   @Column(length = 1000)
   private String feedbackemails;

   @Size(max = 50)
   @Column(length = 50)
   private String password;

   @Size(max = 50)
   @Column(length = 50)
   private String salt;

   private Character isactive;

   private Character isdeleted;

   @JoinColumn(name = "INTEZET_ID", referencedColumnName = "ID", nullable = false)
   @ManyToOne(optional = false, fetch = FetchType.EAGER)
   private Intezet intezet;

   public Felhasznalo() {}

   public Felhasznalo(Integer id) {
      this.id = id;
   }

   public Integer getId() {
      return this.id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLogin() {
      return this.login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return this.password;
   }

   public String getSalt() {
      return this.salt;
   }

   public void setSalt(String salt) {
      this.salt = salt;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Character getIsactive() {
      return this.isactive;
   }

   public void setIsactive(Character isactive) {
      this.isactive = isactive;
   }

   public Boolean isActive() {
      return Boolean.valueOf((this.isactive.equals(Character.valueOf('Y')) || this.isactive.equals(Character.valueOf('y'))));
   }

   public Character getIsdeleted() {
      return this.isdeleted;
   }

   public Boolean isDeleted() {
      return Boolean.valueOf((this.isdeleted.equals(Character.valueOf('Y')) || this.isdeleted.equals(Character.valueOf('y'))));
   }

   public void setIsdeleted(Character isdeleted) {
      this.isdeleted = isdeleted;
   }

   public Intezet getIntezet() {
      return this.intezet;
   }

   public void setIntezet(Intezet intezetekId) {
      this.intezet = intezetekId;
   }

   public String getFeedbackemails() {
      return this.feedbackemails;
   }

   public void setFeedbackemails(String feedbackemails) {
      this.feedbackemails = feedbackemails;
   }

   public boolean isADAuthenticated() {
      return (this.password == null);
   }

   public boolean isAdmin() {
      return (this.intezet.getKey() != null && (this.intezet.getKey().equals("0") || this.intezet.getKey().equals("1")));
   }

   public boolean isTig() {
      return (this.intezet.getKey() != null && this.intezet.getKey().equals("0"));
   }

   public boolean isNunu() {
      return (this.intezet.getKey() != null && Integer.parseInt(this.intezet.getKey()) > 5);
   }

   public boolean isAEEK() {
      return (this.intezet.getKey() != null && this.intezet.getKey().equals("1"));
   }

   public boolean isMiniszterium() {
      return (this.intezet.getKey() != null && this.intezet.getKey().equals("2"));
   }

   public String getNiceStatus() {
      String result;
      if (this.isDeleted()) {
         result = "Törölt";
      } else {
         result = this.isActive() ? "Aktív" : "Inaktív";
      }

      return result;
   }

   public void setNiceStatus(String status) {
      switch (status) {
         case "Törölt":
            this.isdeleted = Character.valueOf('Y');
            this.isactive = Character.valueOf('N');
            break;
         case "Aktív":
            this.isdeleted = Character.valueOf('N');
            this.isactive = Character.valueOf('Y');
            break;
         case "Inaktív":
            this.isdeleted = Character.valueOf('N');
            this.isactive = Character.valueOf('N');
            break;
      }
   }

   public String getNiceRole() {

      String result;
      switch (this.intezet.getKey()) {
         case "0":
            result = "Tig vezető";
            return result;
         case "1":
            result = "ÁEEK vezető";
            return result;
         case "2":
            result = "minisztériumi dolgozó";
            return result;
      }
      result = "intézményi dolgozó";
      return result;
   }

   public int hashCode() {
      int hash = 0;
      hash += (this.id != null) ? this.id.hashCode() : 0;
      return hash;
   }

   public boolean equals(Object object) {
      if (!(object instanceof Felhasznalo))
         return false;
      Felhasznalo other = (Felhasznalo)object;
      if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "Felhasznalo{" +
              "id=" + id +
              ", key='" + key + '\'' +
              ", name='" + name + '\'' +
              ", login='" + login + '\'' +
              ", email='" + email + '\'' +
              ", feedbackemails='" + feedbackemails + '\'' +
              ", password='" + password + '\'' +
              ", salt='" + salt + '\'' +
              ", isactive=" + isactive +
              ", isdeleted=" + isdeleted +
              ", intezet=" + intezet +
              '}';
   }
}
