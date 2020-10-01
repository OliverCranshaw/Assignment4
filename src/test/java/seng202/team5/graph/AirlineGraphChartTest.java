package seng202.team5.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AirlineGraphChartTest {

    private AirlineGraphChart instance;
    private ArrayList<ArrayList<Object>> data;

    @Before
    public void setUp() {

        // Creating and setting the data ArrayList
        ArrayList<Object> testDataFinal1 = new ArrayList<>(Arrays.asList(1, "airline1", "AIR1", "A1", "AR1", "AIRLINE1", "country1", "Y"));
        ArrayList<Object> testDataFinal2 = new ArrayList<>(Arrays.asList(2, "airline1", "AIR1", "A1", "AR1", "AIRLINE1", "country2", "Y"));
        ArrayList<Object> testDataFinal3 = new ArrayList<>(Arrays.asList(3, "airline1", "AIR1", "A1", "AR1", "AIRLINE1", "country1", "Y"));

        data = new ArrayList<>();
        data.add(testDataFinal1);
        data.add(testDataFinal2);
        data.add(testDataFinal3);

        instance = new AirlineGraphChart(data);
    }


    @Test
    public void testBuildChart() throws SQLException {

        // Creating expected results
        ObservableList<PieChart.Data> resultExpected2 = FXCollections.observableArrayList();
        resultExpected2.add(new PieChart.Data("country1", 2));
        resultExpected2.add(new PieChart.Data("country2", 1));

        instance.setSelection("AirlineCountry");
        ObservableList<PieChart.Data> result1 = instance.buildChart();

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected2.size(); i++) {
            Assert.assertEquals(result1.get(i).getPieValue(), resultExpected2.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(result1.get(i).getName(), resultExpected2.get(i).getName());
        }

        // Testing invalid selection
        instance.setSelection("n/a");
        ObservableList<PieChart.Data> result2 = instance.buildChart();

        Assert.assertEquals(0, result2.size());
    }


    @Test
    public void testAirlineCountryChart() {

        // Creating expected results
        ObservableList<PieChart.Data> resultExpected = FXCollections.observableArrayList();
        resultExpected.add(new PieChart.Data("country1", 2));
        resultExpected.add(new PieChart.Data("country2", 1));

        ObservableList<PieChart.Data> result = instance.airlineCountryChart();

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected.size(); i++) {
            Assert.assertEquals(result.get(i).getPieValue(), resultExpected.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(result.get(i).getName(), resultExpected.get(i).getName());
        }
    }


    @Test
    public void testSortChartList() {
        ObservableList<PieChart.Data> unSorted = FXCollections.observableArrayList();
        unSorted.add(new PieChart.Data("country1", 7));
        unSorted.add(new PieChart.Data("country2", 10));
        unSorted.add(new PieChart.Data("country3", 2));
        unSorted.add(new PieChart.Data("country4", 5));

        ObservableList<PieChart.Data> sorted = FXCollections.observableArrayList();
        sorted.add(new PieChart.Data("country2", 10));
        sorted.add(new PieChart.Data("country1", 7));
        sorted.add(new PieChart.Data("country4", 5));
        sorted.add(new PieChart.Data("country3", 2));

        ObservableList<PieChart.Data> result = instance.sortChartList(unSorted);

        // Comparing actual to expected results
        for (int i = 0; i < sorted.size(); i++) {
            Assert.assertEquals(result.get(i).getPieValue(), sorted.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(result.get(i).getName(), sorted.get(i).getName());
        }
    }
}
