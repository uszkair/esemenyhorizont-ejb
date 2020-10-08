package hu.aeek.esemenyhorizont.ejb.session.facade;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless(
   mappedName = "FileService",
   name = "FileService"
)
@LocalBean
public class FileService {
   private final String SELECT_FILE;
   private final String UPDATE_FILE;
   @Resource(
      mappedName = "java:/jdbc/EsemenyHorizontDS"
   )
   private DataSource datasource;

   public FileService() {
      ResourceBundle props = ResourceBundle.getBundle("sql");
      this.UPDATE_FILE = props.getString("updateFile");
      this.SELECT_FILE = props.getString("selectFile");
   }

   public void mergeFile(Integer esemenyId, InputStream inputStream) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      Statement stmt = null;

      try {
         if (esemenyId != null) {
            conn = this.datasource.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(this.SELECT_FILE + esemenyId);
            if (rs.next()) {
               pstmt = conn.prepareStatement(this.UPDATE_FILE);
               pstmt.setBinaryStream(1, inputStream);
               pstmt.setInt(2, esemenyId);
               pstmt.executeUpdate();
            }
         }
      } catch (SQLException var23) {
         Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, (String)null, var23);
      } finally {
         if (stmt != null) {
            try {
               stmt.close();
               stmt = null;
            } catch (SQLException var22) {
               Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, (String)null, var22);
            }
         }

         if (pstmt != null) {
            try {
               pstmt.close();
               pstmt = null;
            } catch (SQLException var21) {
               Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, (String)null, var21);
            }
         }

         if (conn != null) {
            try {
               conn.close();
               conn = null;
            } catch (SQLException var20) {
               Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, (String)null, var20);
            }
         }

      }

   }

   public InputStream getFileByEsemenyId(Integer esemenyId) {
      InputStream result = null;
      Connection conn = null;
      Statement stmt = null;

      try {
         if (esemenyId != null) {
            conn = this.datasource.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(this.SELECT_FILE + esemenyId);
            if (rs.next()) {
               Blob blob = rs.getBlob(1);
               result = blob.getBinaryStream();
            }
         }
      } catch (SQLException var19) {
         Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, (String)null, var19);
      } finally {
         if (stmt != null) {
            try {
               stmt.close();
               stmt = null;
            } catch (SQLException var18) {
               Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, (String)null, var18);
            }
         }

         if (conn != null) {
            try {
               conn.close();
               conn = null;
            } catch (SQLException var17) {
               Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, (String)null, var17);
            }
         }

      }

      return result;
   }
}
