import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.math.BigDecimal;
import java.sql.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
class CrosstabReport_4 {

    public CrosstabReport_4() throws SQLException, ClassNotFoundException {
        build();
    }


    private void build() throws SQLException, ClassNotFoundException {


        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("state", String.class)
                .setTotalHeader("Total");

        CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("item", String.class);

        CrosstabBuilder crosstab = ctab.crosstab()
                .headerCell(cmp.text("State / Item").setStyle(Templates.boldCenteredStyle))
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(
                        ctab.measure("quantity", Integer.class, Calculation.COUNT));

        try {
            report()
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                    .setTemplate(Templates.reportTemplate)
                    .title(Templates.createTitleComponent("Crosstab"))
                    .summary(crosstab)
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(createDataSource())
                    .show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSource() throws SQLException, ClassNotFoundException {
        //connect TRAINING
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest",
                "TRAINING",
                "TRAINING");
        String query = "SELECT ID, COUNTRY, TO_CHAR(DATA,'mm/yyyy') \n" +
                "FROM specialdate \n" +
                "where to_char(data, 'YYYY') = '2017'\n";
        Statement statement = connection.createStatement();
        ResultSet resSet = statement.executeQuery(query);


        DRDataSource dataSource = new DRDataSource("state", "item", "quantity");
        while (resSet.next()) {
            dataSource.add(resSet.getString("Country"), resSet.getString("TO_CHAR(DATA,'mm/yyyy')"), resSet.getInt("ID"));
        }

        return dataSource;
    }
}