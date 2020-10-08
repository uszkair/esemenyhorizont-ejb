package hu.aeek.esemenyhorizont.ejb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({@NamedQuery(
   name = "Tig.findAll",
   query = "SELECT t FROM Tig t"
), @NamedQuery(
   name = "Tig.findById",
   query = "SELECT t FROM Tig t WHERE t.id = :id"
), @NamedQuery(
   name = "Tig.findByKey",
   query = "SELECT t FROM Tig t WHERE t.key = :key"
), @NamedQuery(
   name = "Tig.findByName",
   query = "SELECT t FROM Tig t WHERE t.name = :name"
), @NamedQuery(
   name = "Tig.findByIsdeleted",
   query = "SELECT t FROM Tig t WHERE t.isdeleted = :isdeleted"
)})
public class Tig implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(
      optional = false
   )
   @NotNull
   @Column(
      nullable = false,
      precision = 38,
      scale = 0
   )
   @SequenceGenerator(
      name = "TIG_SEQ",
      sequenceName = "TIG_SEQ",
      allocationSize = 1
   )
   @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "TIG_SEQ"
   )
   private Integer id;
   @Size(
      max = 50
   )
   @Column(
      length = 50
   )
   private String key;
   @Size(
      max = 250
   )
   @Column(
      length = 250
   )
   private String name;
   private Character isdeleted;

   public Tig() {
   }

   public Tig(Integer id) {
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

   public Character getIsdeleted() {
      return this.isdeleted;
   }

   public void setIsdeleted(Character isdeleted) {
      this.isdeleted = isdeleted;
   }

   public int hashCode() {
      int hash = 0;
      hash = hash + (this.id != null ? this.id.hashCode() : 0);
      return hash;
   }

   public boolean equals(Object object) {
      if (!(object instanceof Tig)) {
         return false;
      } else {
         Tig other = (Tig)object;
         return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
      }
   }

   public String toString() {
      return "hu.aeek.esemenyhorizont.ejb.entity.Tig[ id=" + this.id + " ]";
   }
}
