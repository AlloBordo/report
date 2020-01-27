import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
        import net.sf.dynamicreports.report.builder.style.StyleBuilder;
        import net.sf.dynamicreports.report.exception.DRException;

        import java.awt.*;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;
        import java.util.Date;

        import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class   DynamicReport_3 {
    public DynamicReport_3() {
        build();
    }

    StyleBuilder bStyle = stl.style().bold();
    StyleBuilder titleSizeStyle = stl.style().setFontSize(40).setBackgroundColor(new Color(170, 170, 170));
    StyleBuilder SizeStyle = stl.style().setFontSize(12).italic();


    TextColumnBuilder<Date> dColumn = col.column("                                      " +
            "Data", "data", type.dateYearToHourType()).setTitleStyle(bStyle);
    TextColumnBuilder<String> nColumn = col.column("Name", "name", type.stringType()).setTitleStyle(bStyle);
    TextColumnBuilder<String> cColumn = col.column("Country", "country", type.stringType()).setTitleStyle(bStyle);

    private void build() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.238.205:1521:aquariustest",
                    "TRAINING",
                    "TRAINING");

            report()//create new report design
                    .columns(cColumn,nColumn, dColumn) //adds columns
                    .setTitleStyle(titleSizeStyle)
                    .setTitleStyle(bStyle)


                    //title containing
                    .title(cmp.horizontalList(
                            cmp.text("Holidays").setStyle(titleSizeStyle)
                            )
                                    .newRow().add(cmp.filler().setStyle(
                                    stl.style().setTopBorder(stl.pen2Point())
                                    ).setFixedHeight(11)
                                    )
                    )

                    .pageFooter(cmp.pageXofY())

                    //set datasource
                    .setDataSource("SELECT country, name, data\n" +
                            "FROM specialdate\n" +
                            "WHERE to_char(data, 'YYYY') = 2017\n", connection)
                  //  .groupBy(cColumn, nColumn, dColumn)

                    .show() //shows report
            ;
        } catch (DRException e) {
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    }
}