import java.io.IOException;
import java.sql.SQLException;


import org.apache.log4j.BasicConfigurator;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InvocationTargetException, InterruptedException {
        Scanner in = new Scanner(System.in);
        int choice ;
        do {
System.out.println("Выберите один из вариантов \n"+
        "1-JRMapCollectionDataSource\n"+
        "2-JRResultSetDataSource\n"+
        "3-JRBeanCollectionDataSource\n"+
        "4-DynamicReport\n"+
        "5-CrosstabReport\n"+
        "6-BarChart\n"+
        "7-Задание 4 и 5\n"+
        "0-Выход\n");
            choice = in.nextInt();


        switch (choice){
            case 1:
                BasicConfigurator.configure();
                JRMapCollectionDataSource main = new JRMapCollectionDataSource();
                main.start();
                break;
            case 2:

                BasicConfigurator.configure();
                JRResultSetDataSource1 JRResult = new JRResultSetDataSource1();
                JRResult.start();
                break;
            case 3:
                BasicConfigurator.configure();
                JRBeanCollectionDataSource1 JRBean = new JRBeanCollectionDataSource1();
                JRBean.start();
                break;
            case 4:
                BasicConfigurator.configure();
                new DynamicReport_3();

                break;
            case 5:
                BasicConfigurator.configure();
                new CrosstabReport_4();

                break;
            case 6:
           new BarChart();
                break;
            case 7:
                SwingUtilities.invokeAndWait(()->{
                    BasicConfigurator.configure();
                    CrosstabReport example= null;
                    try {
                        example = new CrosstabReport("Bar Chart ");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    example.setSize(800, 400);
                    example.setLocationRelativeTo(null);
                    example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    example.setVisible(true);
                });
                break;
        }  }while (choice!= '0');

    }
}
