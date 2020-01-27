import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.*;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;


import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
        import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
        import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
        import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
        import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
        import net.sf.dynamicreports.report.constant.Calculation;
        import net.sf.dynamicreports.report.constant.HorizontalAlignment;
        import net.sf.dynamicreports.report.constant.PageOrientation;
        import net.sf.dynamicreports.report.constant.PageType;
        import net.sf.dynamicreports.report.datasource.DRDataSource;
        import net.sf.dynamicreports.report.definition.expression.DRIExpression;
        import net.sf.dynamicreports.report.exception.DRException;
        import net.sf.jasperreports.components.table.Cell;
        import net.sf.jasperreports.engine.JRDataSource;
        import net.sf.jasperreports.engine.JasperReport;
        import net.sf.jasperreports.engine.export.tabulator.Row;
        import org.jfree.chart.ChartFactory;
        import org.jfree.chart.ChartPanel;
        import org.jfree.chart.JFreeChart;
        import org.jfree.chart.plot.PlotOrientation;
        import org.jfree.data.category.CategoryDataset;
        import org.jfree.data.category.DefaultCategoryDataset;
        import net.sf.dynamicreports.report.builder.style.FontBuilder;
        import net.sf.dynamicreports.report.builder.style.StyleBuilder;
        import javax.swing.*;
        import java.lang.reflect.InvocationTargetException;
        import java.sql.*;

        import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class zd_4_5 extends JFrame {

    public zd_4_5(String appTitle) throws SQLException, ClassNotFoundException {
        super(appTitle);

        // Create Dataset
        CategoryDataset dataset = createDataset();

        //Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "HOLIDAYS", //Chart Title
                "MONTH", // Category axis
                "HOLIDAYS", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

        build();
    }


    private void build() throws SQLException, ClassNotFoundException {

        CategoryDataset dataset = createDataset();


        //Create chart

        JFreeChart chart = ChartFactory.createBarChart(
                "HOLIDAYS", //Chart Title
                "MONTH", // Category axis
                "HOLIDAYS", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("state", String.class)
                .setTotalHeader("Total");

        CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("item", String.class);

        CrosstabBuilder crosstab = ctab.crosstab()
                .headerCell(cmp.text("State / Item").setStyle(Templates.boldCenteredStyle))
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(
                        ctab.measure("quantity", Integer.class, Calculation.COUNT));

        TextColumnBuilder<String> monthColumn = col.column("Month", "month", type.stringType());
        TextColumnBuilder<Integer> countColumn = col.column("Count", "Count", type.integerType());
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


    private CategoryDataset createDataset() throws SQLException, ClassNotFoundException {


        //connect TRAINING
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest",
                "TRAINING",
                "TRAINING");
        String query = "SELECT COUNT(ID), COUNTRY, TO_CHAR(DATA,'mm') \n" +
                "FROM specialdate \n" +
                "where to_char(data, 'YYYY') = '2017'\n" +
                "group by COUNTRY, TO_CHAR(DATA,'mm') ";
        Statement statement = connection.createStatement();
        ResultSet resSet = statement.executeQuery(query);


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        while (resSet.next()) {
            dataset.addValue(resSet.getInt("COUNT(ID)"), (resSet.getString("TO_CHAR(DATA,'mm')")), resSet.getString("country"));
        }
        return dataset;
    }
}