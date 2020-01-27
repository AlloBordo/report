import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


import net.sf.jasperreports.engine.JasperFillManager;
        import net.sf.jasperreports.engine.JasperPrint;
        import net.sf.jasperreports.engine.JasperReport;
        import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
        import net.sf.jasperreports.engine.util.JRLoader;
        import net.sf.jasperreports.view.JasperViewer;
        import org.apache.log4j.BasicConfigurator;
        import org.apache.log4j.Logger;

        import java.io.BufferedInputStream;
        import java.io.FileInputStream;
        import java.sql.*;
        import java.util.Date;
        import java.util.*;

class JRBeanCollectionDataSource1 {

    private static String URL = "jdbc:oracle:thin:@192.168.238.205:1521:aquariustest";
    private static String user = "TRAINING";
    private static String pas = "TRAINING";
    private Logger logger = Logger.getLogger(JRBeanCollectionDataSource1.class);

    public JRBeanCollectionDataSource1() {
    }

    void start() {
        try {
            // load report location
            FileInputStream fis = new FileInputStream("C:\\Users\\crm0191\\JaspersoftWorkspace\\MyReports\\H1.jasper");

            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

            // fill report

            List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();


            Connection connection;
            try {
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.print("ERROR!");
            }


            connection = DriverManager.getConnection(URL, user, pas);
            String query = "select country, name, data\n" +
                    "from specialdate\n" +
                    "where to_char(data, 'YYYY') = 2017\n";
            Statement statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery(query);
            while (resSet.next()) {
                String name;
                String country;
                Date date;
                date = resSet.getDate("data");
                name = resSet.getString("name");
                country = resSet.getString("country");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("NAME", name);
                map.put("COUNTRY", country);
                map.put("DATA", date);
                maps.add(map);
            }

            net.sf.jasperreports.engine.data.JRBeanCollectionDataSource dataSource = new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(maps);

            // compile report
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), dataSource);

            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
            statement.close();
            connection.close();
        } catch (Exception e) {
            logger.error(e, e);
        }

    }

    private String getRandomString() {
        return UUID.randomUUID().toString();
    }
}