import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import net.sf.jasperreports.engine.JRResultSetDataSource;
        import net.sf.jasperreports.engine.JasperFillManager;
        import net.sf.jasperreports.engine.JasperPrint;
        import net.sf.jasperreports.engine.JasperReport;
        import net.sf.jasperreports.engine.util.JRLoader;
        import net.sf.jasperreports.view.JasperViewer;
        import org.apache.log4j.BasicConfigurator;
        import org.apache.log4j.Logger;

        import java.io.BufferedInputStream;
        import java.io.FileInputStream;
        import java.sql.*;
        import java.util.*;

class JRResultSetDataSource1 {

    private static  String  URL= "jdbc:oracle:thin:@192.168.238.205:1521:aquariustest";
    private static  String user="TRAINING";
    private static  String pas="TRAINING";
    private Logger logger = Logger.getLogger(JRResultSetDataSource.class);

    public JRResultSetDataSource1() {
    }

    void start() {
        BasicConfigurator.configure();
        try {
            // load report location
            FileInputStream fis = new FileInputStream("C:\\Users\\crm0191\\JaspersoftWorkspace\\MyReports\\H1.jasper");

            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

            // fill report

            Connection connection;
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.print("ERROR!");
            }


            connection = DriverManager.getConnection(URL,user,pas);
            String query = "select country, name, data\n" +
                    "from specialdate\n" +
                    "where to_char(data, 'YYYY') = 2017\n" +
                    "order by data";
            Statement statement = connection.createStatement();

            ResultSet resSet = statement.executeQuery(query);

            net.sf.jasperreports.engine.JRResultSetDataSource jrRS = new net.sf.jasperreports.engine.JRResultSetDataSource(resSet);



            // compile report
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrRS);

            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
            statement.close();
            connection.close();
        } catch (Exception e) {
            logger.error(e, e);
        }

    }

    private String getRandomString(){
        return UUID.randomUUID().toString();
    }


}