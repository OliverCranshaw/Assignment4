package seng202.team5.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng202.team5.database.DBConnection;
import seng202.team5.database.DBInitializer;
import seng202.team5.database.DBTableInitializer;
import seng202.team5.service.AirlineService;
import seng202.team5.service.AirportService;
import seng202.team5.service.RouteService;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AirportGraphChartTest {

    private AirportGraphChart instance;
    private RouteService routeService;
    private AirportService airportService;
    private AirlineService airlineService;
    private ArrayList<ArrayList<Object>> data;
    private static final String dbFile = "test.db";

    @Before
    public void setUp() throws SQLException {
        DBInitializer.createNewDatabase(dbFile);
        DBTableInitializer.initializeTables(dbFile);

        DBConnection.setDatabaseFile(new File(dbFile));

        // Initialising the services and saving airports, airlines, routes
        routeService = new RouteService();
        airportService = new AirportService();
        airlineService = new AirlineService();

        airportService.save("airport1", "city1", "country1", "ia1", "ica1", 33.3, 44.4, 4344, 6, "dst1", "tz1");
        airportService.save("airport2", "city2", "country2", "ia2", "ica2", 33.2, 44.2, 4342, 6, "dst2", "tz2");
        airportService.save("airport3", "city1", "country1", "ia3", "ica3", 33.1, 44.1, 4341, 6, "dst3", "tz3");

        airlineService.save("airline1", "AIR1", "A1", "AR1", "AIRLINE1", "country1", "Y");

        routeService.save("A1", "ia1", "ia2", "Y", 6, "GPS");
        routeService.save("A1", "ia2", "ia3", "Y", 6, "GPS");
        routeService.save("A1", "ia2", "ia1", "Y", 6, "GPS");

        // Creating and setting the data ArrayList
        ArrayList<Object> testDataFinal1 = new ArrayList<>(Arrays.asList(1, "airport1", "city1", "country1", "ia1", "ica1", 33.3, 44.4, 4344, "time1", "dst1", "tz1"));
        ArrayList<Object> testDataFinal2 = new ArrayList<>(Arrays.asList(2, "airport2", "city2", "country2", "ia2", "ica2", 33.2, 44.2, 4342, "time2", "dst2", "tz2"));
        ArrayList<Object> testDataFinal3 = new ArrayList<>(Arrays.asList(3, "airport3", "city1", "country1", "ia3", "ica3", 33.1, 44.1, 4341, "time3", "dst3", "tz3"));

        data = new ArrayList<>();
        data.add(testDataFinal1);
        data.add(testDataFinal2);
        data.add(testDataFinal3);

        instance = new AirportGraphChart(data);
    }

    @After
    public void tearDown() throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }


    @Test
    public void testBuildChart() throws SQLException {

        // Creating expected results 1
        ObservableList<PieChart.Data> resultExpected1 = FXCollections.observableArrayList();
        resultExpected1.add(new PieChart.Data("airport2", 3));
        resultExpected1.add(new PieChart.Data("airport1", 2));
        resultExpected1.add(new PieChart.Data("airport3", 1));

        instance.setSelection("AirportRoute");
        ObservableList<PieChart.Data> result1 = instance.buildChart();

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected1.size(); i++) {
            Assert.assertEquals(result1.get(i).getPieValue(), resultExpected1.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(result1.get(i).getName(), resultExpected1.get(i).getName());
        }

        // Creating expected results 2
        ObservableList<PieChart.Data> resultExpected2 = FXCollections.observableArrayList();
        resultExpected2.add(new PieChart.Data("country1", 2));
        resultExpected2.add(new PieChart.Data("country2", 1));

        instance.setSelection("AirportCountry");
        ObservableList<PieChart.Data> result2 = instance.buildChart();

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected2.size(); i++) {
            Assert.assertEquals(result2.get(i).getPieValue(), resultExpected2.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(result2.get(i).getName(), resultExpected2.get(i).getName());
        }

        // Testing invalid selection
        instance.setSelection("n/a");
        ObservableList<PieChart.Data> result3 = instance.buildChart();

        Assert.assertEquals(0, result3.size());
    }


    @Test
    public void testAirportRouteChart() throws SQLException {

        // Creating expected results
        ObservableList<PieChart.Data> resultExpected = FXCollections.observableArrayList();
        resultExpected.add(new PieChart.Data("airport2", 3));
        resultExpected.add(new PieChart.Data("airport1", 2));
        resultExpected.add(new PieChart.Data("airport3", 1));

        ObservableList<PieChart.Data> result = instance.airportRouteChart();

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected.size(); i++) {
            Assert.assertEquals(result.get(i).getPieValue(), resultExpected.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(result.get(i).getName(), resultExpected.get(i).getName());
        }
    }


    @Test
    public void testAirportCountryChart() {

        // Creating expected results
        ObservableList<PieChart.Data> resultExpected = FXCollections.observableArrayList();
        resultExpected.add(new PieChart.Data("country1", 2));
        resultExpected.add(new PieChart.Data("country2", 1));

        ObservableList<PieChart.Data> result = instance.airportCountryChart();

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
