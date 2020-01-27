import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;

import java.awt.*;
import java.sql.*;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;


import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
        import net.sf.dynamicreports.report.builder.style.FontBuilder;
        import net.sf.dynamicreports.report.builder.style.StyleBuilder;
        import net.sf.dynamicreports.report.constant.HorizontalAlignment;
        import net.sf.dynamicreports.report.exception.DRException;
        import net.sf.jasperreports.engine.JRResultSetDataSource;

        import java.awt.*;
        import java.sql.*;

        import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class BarChart {

    public BarChart() throws SQLException {
        build();
    }

    private void build() throws SQLException {
        FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
        StyleBuilder titleStyle = stl.style().setFontSize(30).bold();
        StyleBuilder style = stl.style().setBackgroundColor(new Color(0, 107, 179)).setForegroundColor(Color.white).bold()
                .setBorder(stl.pen1Point());
        StyleBuilder style1 = stl.style().setBackgroundColor(Color.red).setBackgroundColor(new Color(0, 153, 255)).setForegroundColor(Color.white).bold()
                .setBorder(stl.pen1Point());
        StyleBuilder style2 = stl.style().setBackgroundColor(Color.red).setBackgroundColor(new Color(77, 184, 255)).setForegroundColor(Color.white).bold()
                .setBorder(stl.pen1Point());

        TextColumnBuilder<String> countryColumn = col.column("Country", "country", type.stringType());
        TextColumnBuilder<String> monthColumn = col.column("Month", "month", type.stringType());
        TextColumnBuilder<Integer> countColumn = col.column("Count Holidays", "Count", type.integerType());

        try {
            report()
                    .columns(
                            countryColumn.setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(style),
                            monthColumn.setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(style1),
                            countColumn.setHorizontalAlignment(HorizontalAlignment.CENTER).setStyle(style2))
                    .title(cmp.text("Holidays").setStyle(titleStyle))
                    .summary(
                            cht.bar3DChart().addSeriesColor(new Color(0, 107, 179))
                                    .setTitle("Holidays")
                                    .setTitleFont(boldFont)
                                    .setCategory(monthColumn)
                                    .series(
                                            cht.serie(countColumn))
                    )
                    .setDataSource(createDataSource())
                    .show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private JRResultSetDataSource createDataSource() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@192.168.238.205:1521:aquariustest", "TRAINING", "TRAINING");
        Statement statement = connection.createStatement();
        String sql = "SELECT  COUNTRY, TO_CHAR(DATA,\'mm\') AS month, COUNT(*) as Count FROM specialdate  where to_char(data, \'YYYY\') = \'2017\' GROUP BY TO_CHAR(DATA,\'mm\'), COUNTRY ORDER BY month";

        ResultSet resultSet = statement.executeQuery(sql);

        return new JRResultSetDataSource(resultSet);
    }
}

