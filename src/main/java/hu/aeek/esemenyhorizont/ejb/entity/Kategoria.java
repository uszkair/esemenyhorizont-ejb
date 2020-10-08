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
   name = "Kategoria.findAll",
   query = "SELECT k FROM Kategoria k"
), @NamedQuery(
   name = "Kategoria.findById",
   query = "SELECT k FROM Kategoria k WHERE k.id = :id"
), @NamedQuery(
   name = "Kategoria.findByKey",
   query = "SELECT k FROM Kategoria k WHERE k.key = :key"
), @NamedQuery(
   name = "Kategoria.findByName",
   query = "SELECT k FROM Kategoria k WHERE k.name = :name"
), @NamedQuery(
   name = "Kategoria.findByWmin",
   query = "SELECT k FROM Kategoria k WHERE k.wmin = :wmin"
), @NamedQuery(
   name = "Kategoria.findByWmax",
   query = "SELECT k FROM Kategoria k WHERE k.wmax = :wmax"
), @NamedQuery(
   name = "Kategoria.findByIsdeleted",
   query = "SELECT k FROM Kategoria k WHERE k.isdeleted = :isdeleted"
)})
public class Kategoria implements Serializable {
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
      name = "KATEGORIA_SEQ",
      sequenceName = "KATEGORIA_SEQ",
      allocationSize = 1
   )
   @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "KATEGORIA_SEQ"
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
   private Integer wmin;
   private Integer wmax;
   private Character isdeleted;

   public Kategoria() {
   }

   public Kategoria(Integer id) {
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

   public Integer getWmin() {
      return this.wmin;
   }

   public void setWmin(Integer wmin) {
      this.wmin = wmin;
   }

   public Integer getWmax() {
      return this.wmax;
   }

   public void setWmax(Integer wmax) {
      this.wmax = wmax;
   }

   public Integer getNiceWmin() {
      return this.wmin < 0 ? -this.wmax : this.wmin;
   }

   public Integer getNiceWmax() {
      return this.wmin < 0 ? -this.wmin : this.wmax;
   }

   public Integer getNiceSign() {
      return this.wmin < 0 ? -1 : (this.wmin > 0 ? 1 : 0);
   }

   public String getNiceName() {
      String result;
      if (this.wmin < 0) {
         result = "(−) " + this.name;
      } else if (this.wmax > 0) {
         result = "(+) " + this.name;
      } else {
         result = " " + this.name;
      }

      return result;
   }

   public String getNiceNameForFilter() {
      String result;
      if (this.wmin < 0) {
         result = "(-) " + this.name;
      } else if (this.wmax > 0) {
         result = "(+) " + this.name;
      } else {
         result = " " + this.name;
      }

      return result;
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
      if (!(object instanceof Kategoria)) {
         return false;
      } else {
         Kategoria other = (Kategoria)object;
         return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
      }
   }

   public String toString() {
      return "hu.aeek.esemenyhorizont.ejb.entity.Kategoria[ id=" + this.id + " ]";
   }
}
