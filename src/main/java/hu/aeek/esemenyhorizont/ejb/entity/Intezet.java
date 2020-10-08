package hu.aeek.esemenyhorizont.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@XmlRootElement
@NamedQueries({@NamedQuery(
   name = "Intezet.findAll",
   query = "SELECT i FROM Intezet i"
), @NamedQuery(
   name = "Intezet.findById",
   query = "SELECT i FROM Intezet i WHERE i.id = :id"
), @NamedQuery(
   name = "Intezet.findByTigId",
   query = "SELECT i FROM Intezet i WHERE i.tig.id = :id"
), @NamedQuery(
   name = "Intezet.findByKey",
   query = "SELECT i FROM Intezet i WHERE i.key = :key"
), @NamedQuery(
   name = "Intezet.findByName",
   query = "SELECT i FROM Intezet i WHERE i.name = :name"
), @NamedQuery(
   name = "Intezet.findByIsdeleted",
   query = "SELECT i FROM Intezet i WHERE i.isdeleted = :isdeleted"
)})
public class Intezet implements Serializable {
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
      name = "INTEZET_SEQ",
      sequenceName = "INTEZET_SEQ",
      allocationSize = 1
   )
   @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "INTEZET_SEQ"
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
   @JoinColumn(
      name = "TIG_ID",
      referencedColumnName = "ID",
      nullable = false
   )
   @ManyToOne(
      optional = false,
      fetch = FetchType.EAGER
   )
   @Fetch(FetchMode.SELECT)
   @BatchSize(
      size = 2000
   )
   private Tig tig;
   @OneToMany(
      cascade = {CascadeType.ALL},
      mappedBy = "intezet",
      fetch = FetchType.EAGER
   )
   @Fetch(FetchMode.SUBSELECT)
   @BatchSize(
      size = 2000
   )
   private List<Felhasznalo> felhasznaloList;

   public Intezet() {
   }

   public Intezet(Integer id) {
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

   public Tig getTig() {
      return this.tig;
   }

   public void setTig(Tig tigekId) {
      this.tig = tigekId;
   }

   @XmlTransient
   public List<Felhasznalo> getFelhasznaloList() {
      return this.felhasznaloList;
   }

   public void setFelhasznaloList(List<Felhasznalo> felhasznaloList) {
      this.felhasznaloList = felhasznaloList;
   }

   public int hashCode() {
      int hash = 0;
      hash = hash + (this.id != null ? this.id.hashCode() : 0);
      return hash;
   }

   public boolean equals(Object object) {
      if (!(object instanceof Intezet)) {
         return false;
      } else {
         Intezet other = (Intezet)object;
         return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
      }
   }

   public String toString() {
      return "hu.aeek.esemenyhorizont.ejb.entity.Intezet[ id=" + this.id + " ]";
   }
}
