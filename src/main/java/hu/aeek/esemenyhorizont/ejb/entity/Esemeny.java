package hu.aeek.esemenyhorizont.ejb.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@XmlRootElement
@NamedQueries({@NamedQuery(
   name = "Esemeny.findAll",
   query = "SELECT e FROM Esemeny e"
), @NamedQuery(
   name = "Esemeny.findById",
   query = "SELECT e FROM Esemeny e WHERE e.id = :id"
), @NamedQuery(
   name = "Esemeny.findByEventDate",
   query = "SELECT e FROM Esemeny e WHERE e.eventDate = :eventDate"
), @NamedQuery(
   name = "Esemeny.findByBetweenEventDate",
   query = "SELECT e FROM Esemeny e WHERE e.eventDate BETWEEN :startDate AND :endDate"
), @NamedQuery(
   name = "Esemeny.findByReportDate",
   query = "SELECT e FROM Esemeny e WHERE e.reportDate = :reportDate"
), @NamedQuery(
   name = "Esemeny.findByWeight",
   query = "SELECT e FROM Esemeny e WHERE e.weight = :weight"
), @NamedQuery(
   name = "Esemeny.findByCaption",
   query = "SELECT e FROM Esemeny e WHERE e.caption = :caption"
), @NamedQuery(
   name = "Esemeny.findByIntezetId",
   query = "SELECT e FROM Esemeny e WHERE e.intezet.id = :intezetId"
), @NamedQuery(
   name = "Esemeny.findByDescription",
   query = "SELECT e FROM Esemeny e WHERE e.description = :description"
), @NamedQuery(
   name = "Esemeny.findByAction",
   query = "SELECT e FROM Esemeny e WHERE e.action = :action"
), @NamedQuery(
   name = "Esemeny.findByAdditionalAction",
   query = "SELECT e FROM Esemeny e WHERE e.additionalAction = :additionalAction"
), @NamedQuery(
   name = "Esemeny.findByAeekComment",
   query = "SELECT e FROM Esemeny e WHERE e.aeekComment = :aeekComment"
), @NamedQuery(
   name = "Esemeny.findByTigComment",
   query = "SELECT e FROM Esemeny e WHERE e.tigComment = :tigComment"
)})
public class Esemeny implements Serializable {
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
      name = "ESEMENY_SEQ",
      sequenceName = "ESEMENY_SEQ",
      allocationSize = 1
   )
   @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "ESEMENY_SEQ"
   )
   private Integer id;
   @Column(
      name = "EVENT_DATE"
   )
   @Temporal(TemporalType.TIMESTAMP)
   private Date eventDate;
   @Column(
      name = "REPORT_DATE"
   )
   @Temporal(TemporalType.TIMESTAMP)
   private Date reportDate;
   private Integer weight;
   @Size(
      max = 250
   )
   @Column(
      length = 250
   )
   private String caption;
   @Size(
      max = 2000
   )
   @Column(
      length = 2000
   )
   private String description;
   @Column(
      length = 2000
   )
   private String action;
   @Size(
      max = 2000
   )
   @Column(
      name = "ADDITIONAL_ACTION",
      length = 2000
   )
   private String additionalAction;
   @Size(
      max = 2000
   )
   @Column(
      name = "AEEK_COMMENT",
      length = 2000
   )
   private String aeekComment;
   @Size(
      max = 2000
   )
   @Column(
      name = "TIG_COMMENT",
      length = 2000
   )
   private String tigComment;
   @JoinColumn(
      name = "CORRECTOR_ID",
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
   private Felhasznalo corrector;
   @JoinColumn(
      name = "FELHASZNALO_ID",
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
   private Felhasznalo felhasznalo;
   @JoinColumn(
      name = "KATEGORIA_ID",
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
   private Kategoria kategoria;
   @JoinColumn(
      name = "INTEZET_ID",
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
   private Intezet intezet;
   @Column(
      name = "ISTIGHIDDEN"
   )
   private Character isTIGHidden;
   @Column(
      name = "ISAEEKHIDDEN"
   )
   private Character isAEEKHidden;
   @Column(
      name = "WARNING_LEVEL"
   )
   private Integer warningLevel;
   @Size(
      max = 250
   )
   @Column(
      length = 250,
      name = "FILE_NAME"
   )
   private String fileName;
   public static final String[] MONTHS = new String[]{"jan.", "febr.", "márc.", "ápr.", "máj.", "jún.", "júl.", "aug.", "szept.", "okt.", "nov.", "dec."};
   public static final String[] DAYS = new String[]{"hétfő", "kedd", "szerda", "csütörtök", "péntek", "szombat", "vasárnap"};
   public static final String[] WARNINGS = new String[]{"", "", "&middot;", "!", "!!", "!!!"};
   public static final String[] ANEN = new String[]{"", "én", "án", "án", "én", "én", "án", "én", "án", "én", "én", "én", "én", "án", "én", "én", "án", "én", "án", "én", "án", "én", "én", "án", "én", "én", "án", "én", "án", "én", "án", "én"};
   public static final double CLOSEHOURS = 8.5D;

   public Esemeny() {
   }

   public Esemeny(Integer id) {
      this.id = id;
   }

   public Date getEventDate() {
      return this.eventDate;
   }

   public String getNiceEventDate() {
      LocalDate day = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
      return MONTHS[day.getMonth().getValue() - 1] + "&nbsp;" + day.getDayOfMonth() + "<br />" + DAYS[day.getDayOfWeek().getValue() - 1];
   }

   public String getNiceEventDateEn() {
      LocalDate day = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
      return MONTHS[day.getMonth().getValue() - 1] + " " + day.getDayOfMonth() + ".-" + ANEN[day.getDayOfMonth()];
   }

   public String getNiceShortEventDate() {
      LocalDate day = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
      return day.getYear() + ". " + MONTHS[day.getMonth().getValue() - 1] + " " + day.getDayOfMonth() + ".";
   }

   public String getEventEpochDays() {
      LocalDate day = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
      return String.valueOf(day.toEpochDay());
   }

   public String getNiceSign() {
      String sign = "neutral";
      if (this.weight == null) {
         sign = "null";
      } else if (this.weight > 0) {
         sign = "positive";
      } else if (this.weight < 0) {
         sign = "negative";
      }

      if (this.kategoria.getWmin() < 0 && this.kategoria.getWmax() == 0) {
         sign = "lila";
      }

      return sign;
   }

   public void setEventDate(Date eventDate) {
      this.eventDate = eventDate;
   }

   public Date getReportDate() {
      return this.reportDate;
   }

   public String getNiceReportDate() {
      LocalDateTime date = LocalDateTime.ofInstant(this.reportDate.toInstant(), ZoneId.systemDefault());
      return MONTHS[date.getMonth().getValue() - 1] + " " + date.getDayOfMonth() + ". " + String.format("%02d", date.getHour()) + ":" + String.format("%02d", date.getMinute());
   }

   public String getReportDateEpochDays() {
      LocalDateTime ldt = LocalDateTime.ofInstant(this.reportDate.toInstant(), ZoneId.systemDefault());
      return ldt.toLocalDate().toEpochDay() + "z" + String.format("%02d", ldt.getHour()) + String.format("%02d", ldt.getMinute()) + String.format("%02d", ldt.getSecond());
   }

   public void setReportDate(Date reportDate) {
      this.reportDate = reportDate;
   }

   public Integer getWeight() {
      return this.weight;
   }

   public String getNiceWeight() {
      return this.weight != null && this.weight != 0 ? "" + Math.abs(this.weight) : "";
   }

   public int getNiceIntWeight() {
      return this.weight != null && this.weight != 0 ? Math.abs(this.weight) : 0;
   }

   public void setNiceWeight(String w) {
      if (!"".equals(w)) {
         this.weight = Math.min(10, Math.max(1, Integer.parseInt(w))) * (int)Math.signum((float)this.kategoria.getWmin());
      } else {
         this.weight = 0;
      }

   }

   public void setWeight(Integer weight) {
      this.weight = weight;
   }

   public String getCaption() {
      return this.caption;
   }

   public void setCaption(String caption) {
      this.caption = caption;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Integer getId() {
      return this.id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Kategoria getKategoria() {
      return this.kategoria;
   }

   public String getNiceKategoria() {
      return this.kategoria == null ? null : this.kategoria.getName();
   }

   public void setKategoria(Kategoria kategoriak) {
      this.kategoria = kategoriak;
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public boolean isFileExist() {
      return this.fileName != null && !this.fileName.equals("");
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   public String getAdditionalAction() {
      return this.additionalAction;
   }

   public void setAdditionalAction(String additionalAction) {
      this.additionalAction = additionalAction;
   }

   public String getAeekComment() {
      return this.aeekComment;
   }

   public void setAeekComment(String aeekComment) {
      this.aeekComment = aeekComment;
   }

   public String getTigComment() {
      return this.tigComment;
   }

   public Character getIsTIGHidden() {
      return this.isTIGHidden;
   }

   public void setIsTIGHidden(Character isTIGHidden) {
      this.isTIGHidden = isTIGHidden;
   }

   public boolean isTigHiddenBoolean() {
      return this.isTIGHidden == null || this.isTIGHidden == 'Y';
   }

   public void setTigHiddenBoolean(boolean b) {
      this.isTIGHidden = Character.valueOf((char)(b ? 'Y' : 'N'));
   }

   public Character getIsAEEKHidden() {
      return this.isAEEKHidden;
   }

   public void setIsAEEKHidden(Character isAEEKHidden) {
      this.isAEEKHidden = isAEEKHidden;
   }

   public boolean isAEEKHiddenBoolean() {
      return this.isAEEKHidden == null || this.isAEEKHidden == 'Y';
   }

   public void setAEEKHiddenBoolean(boolean b) {
      this.isAEEKHidden = Character.valueOf((char)(b ? 'Y' : 'N'));
   }

   public String getRejto() {
      String result = "";
      if (this.isTigHiddenBoolean()) {
         result = "TIG";
      } else if (this.isAEEKHiddenBoolean()) {
         result = "ÁEEK";
      }

      return result;
   }

   public Integer getWarningLevel() {
      return this.warningLevel;
   }

   public void setWarningLevel(Integer warningLevel) {
      this.warningLevel = Math.min(5, Math.max(1, warningLevel));
   }

   public String getNiceWarningLevel() {
      return this.warningLevel == null ? "" : WARNINGS[this.warningLevel];
   }

   public String getNiceComments() {
      String result = "";
      if (this.tigComment != null && !this.tigComment.equals("")) {
         result = "TIG: " + this.tigComment;
      }

      if (this.aeekComment != null && !this.aeekComment.equals("")) {
         result = result + "\nÁEEK: " + this.aeekComment;
      }

      return result;
   }

   public void setTigComment(String tigComment) {
      this.tigComment = tigComment;
   }

   public Felhasznalo getCorrector() {
      return this.corrector;
   }

   public void setCorrector(Felhasznalo corrector) {
      this.corrector = corrector;
   }

   public Felhasznalo getFelhasznalo() {
      return this.felhasznalo;
   }

   public void setFelhasznalo(Felhasznalo felhasznalo) {
      this.felhasznalo = felhasznalo;
   }

   public Intezet getIntezet() {
      return this.intezet;
   }

   public void setIntezet(Intezet intezet) {
      this.intezet = intezet;
   }

   public boolean isOpen() {
      LocalDate firstOpenDay = LocalDateTime.now().minusMinutes(510L).toLocalDate();
      LocalDate day = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
      return !day.isBefore(firstOpenDay);
   }

   public boolean isNothingSpecialReportAllowed() {
      LocalDate firstOpenDay = LocalDateTime.now().minusMinutes(510L).toLocalDate();
      LocalDate day = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
      LocalDate prevDay = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate().minusDays(1L);
      return !day.isBefore(firstOpenDay) && prevDay.isBefore(firstOpenDay);
   }

   public boolean isConfirmationForNothingSpecialRequired() {
      LocalDate allowedToReportNothingSpecialDay = LocalDateTime.now().minusHours(23L).toLocalDate();
      LocalDate eventDay = LocalDateTime.ofInstant(this.eventDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
      return allowedToReportNothingSpecialDay.isBefore(eventDay);
   }

   public String getSampleToCheckExistence() {
      return this.intezet.getId() + "@" + this.eventDate.getTime();
   }

   public String getSampleToOrder() {
      return this.intezet.getId() + "@" + this.eventDate.getTime() + "@" + this.reportDate.getTime();
   }

   public int hashCode() {
      int hash = 0;
      hash = hash + (this.id != null ? this.id.hashCode() : 0);
      return hash;
   }

   public boolean equals(Object object) {
      if (!(object instanceof Esemeny)) {
         return false;
      } else {
         Esemeny other = (Esemeny)object;
         return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
      }
   }

   public String toString() {
      return "Esemeny{id=" + this.id + ", eventDate=" + this.eventDate + ", reportDate=" + this.reportDate + ", weight=" + this.weight + ", caption=" + this.caption + ", description=" + this.description + ", action=" + this.action + ", additionalAction=" + this.additionalAction + ", aeekComment=" + this.aeekComment + ", tigComment=" + this.tigComment + ", correctorId=" + this.corrector + ", felhasznalo=" + this.felhasznalo + ", kategoria=" + this.kategoria + ", intezet=" + this.intezet + '}';
   }

   private String nvl(String s) {
      return s != null && !s.equals("") ? s : "-";
   }

   public String getEmailString() {
      return "Intézmény: " + this.intezet.getName() + ", " + this.getNiceShortEventDate() + "\nEsemény: " + this.caption + "\nKategória: " + this.getNiceKategoria() + ", súly: " + this.getNiceWeight() + ", kommunikációs intenzitás: " + this.warningLevel + ".\n\nHosszabb leírás: " + this.nvl(this.description) + "\n\nFeltöltött file: " + this.nvl(this.fileName) + "\n\nMegtett intézkedés: " + this.nvl(this.action) + "\n\nTovábbi szükséges intézkedés: " + this.nvl(this.additionalAction) + "\n\nMegjegyzés: " + this.nvl(this.getNiceComments()) + "\n\nTechnikai adatok: \n    rögzítő: " + this.felhasznalo.getName() + " (" + this.felhasznalo.getLogin() + ")\n    dátum: " + this.getNiceReportDate();
   }
}
