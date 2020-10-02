package seng202.team5.graph;

import io.cucumber.java.bs.A;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteGraphChartTest {

    private RouteService routeService;
    private AirportService airportService;
    private AirlineService airlineService;
    private static final String dbFile = "test.db";



    @Before
    public void setUp() throws SQLException {
        System.out.println("Setup");
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
        airlineService.save("airline2", "AIR2", "A2", "AR2", "AIRLINE2", "country2", "Y");

        routeService.save("A1", "ia1", "ia2", "Y", 6, "GPS");
        routeService.save("A1", "ia2", "ia3", "Y", 6, "GPS");
        routeService.save("A2", "ia1", "ia2", "Y", 6, "GPS");

    }

    @After
    public void tearDown() throws SQLException {
        System.out.println("Tear down");
        Connection conn = DBConnection.getConnection();
        conn.close();

        new File(dbFile).delete();
    }


    @Test
    public void testBuildPieChart() {

        // Creating first test data
        ArrayList<ArrayList<Object>> exampleData = new ArrayList<>();
        List<String> route1Equip = List.of("GPS", "EKG");
        List<Object> route1 = List.of(1, "a1", 1, "s1", 1, "d1", 1, "Y", 3, new ArrayList<>(route1Equip));
        exampleData.add(new ArrayList<>(route1));
        List<String> route2Equip = List.of("GPS", "LED", "PNF");
        List<Object> route2 = List.of(2, "a2", 2, "s2", 2, "d1", 2, "Y", 3, new ArrayList<>(route2Equip));
        exampleData.add(new ArrayList<>(route2));
        List<String> route3Equip = List.of("GPS", "LED", "POE");
        List<Object> route3 = List.of(3, "a3", 3, "s3", 3, "d3", 3, "Y", 3, new ArrayList<>(route3Equip));
        exampleData.add(new ArrayList<>(route3));

        // Creating test graph
        RouteGraphChart testGraph = new RouteGraphChart(exampleData);
        testGraph.setSelection("RouteEquipment");
        ObservableList<PieChart.Data> resultActual = testGraph.buildChart();

        // Creating expected results
        ObservableList<PieChart.Data> resultExpected = FXCollections.observableArrayList();
        resultExpected.add(new PieChart.Data("GPS", 3));
        resultExpected.add(new PieChart.Data("LED", 2));
        resultExpected.add(new PieChart.Data("EKG", 1));
        resultExpected.add(new PieChart.Data("PNF", 1));
        resultExpected.add(new PieChart.Data("POE", 1));

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected.size(); i++) {
            Assert.assertEquals(resultActual.get(i).getPieValue(), resultExpected.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(resultActual.get(i).getName(), resultExpected.get(i).getName());
        }


        // Expanding initial data to cover > 15 number case
        List<String> route4Equip = List.of("GPS", "EKG", "NDL", "FND", "FNS", "NAD", "FNF", "GNE");
        List<Object> route4 = List.of(1, "a1", 1, "s1", 1, "d1", 1, "Y", 3, new ArrayList<>(route4Equip));
        exampleData.add(new ArrayList<>(route4));
        List<String> route5Equip = List.of("GPS", "LED", "PNF", "NDL", "FND", "FNS", "ENP", "ARW", "ENG");
        List<Object> route5 = List.of(2, "a2", 2, "s2", 2, "d1", 2, "Y", 3, new ArrayList<>(route5Equip));
        exampleData.add(new ArrayList<>(route5));
        List<String> route6Equip = List.of("GPS", "LED", "POE", "END", "QJN", "FND", "AWN", "IEU", "EYR");
        List<Object> route6 = List.of(3, "a3", 3, "s3", 3, "d3", 3, "Y", 3, new ArrayList<>(route6Equip));
        exampleData.add(new ArrayList<>(route6));

        // Creating test graph
        RouteGraphChart testGraph2 = new RouteGraphChart(exampleData);
        testGraph.setSelection("RouteEquipment");
        ObservableList<PieChart.Data> resultActual2 = testGraph.buildChart();


        // Creating expected results
        ObservableList<PieChart.Data> resultExpected2 = FXCollections.observableArrayList();
        resultExpected2.add(new PieChart.Data("GPS", 6));
        resultExpected2.add(new PieChart.Data("LED", 4));
        resultExpected2.add(new PieChart.Data("FND", 3));
        resultExpected2.add(new PieChart.Data("EKG", 2));
        resultExpected2.add(new PieChart.Data("PNF", 2));
        resultExpected2.add(new PieChart.Data("FNS", 2));
        resultExpected2.add(new PieChart.Data("NDL", 2));
        resultExpected2.add(new PieChart.Data("POE", 2));
        resultExpected2.add(new PieChart.Data("ARW", 1));
        resultExpected2.add(new PieChart.Data("ENP", 1));
        resultExpected2.add(new PieChart.Data("IEU", 1));
        resultExpected2.add(new PieChart.Data("ENG", 1));
        resultExpected2.add(new PieChart.Data("END", 1));
        resultExpected2.add(new PieChart.Data("QJN", 1));
        resultExpected2.add(new PieChart.Data("GNE", 1));
        resultExpected2.add(new PieChart.Data("Other (3)", 3));


        // Comparing actual to expected results
        for (int i = 0; i < resultExpected2.size(); i++) {
            Assert.assertEquals(resultActual2.get(i).getPieValue(), resultExpected2.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(resultActual2.get(i).getName(), resultExpected2.get(i).getName());
        }



        // Testing if the buildChart behaves correctly when given a wrong selection
        RouteGraphChart testGraph3 = new RouteGraphChart(exampleData);
        testGraph3.setSelection("EFSNF");
        ObservableList<PieChart.Data> resultActual3 = testGraph3.buildChart();
        Assert.assertEquals(0, resultActual3.size());


        // Testing if the buildChart behaves correctly when not given a selection
        RouteGraphChart testGraph4 = new RouteGraphChart(exampleData);
        ObservableList<PieChart.Data> resultActual4 = testGraph4.buildChart();
        Assert.assertEquals(0, resultActual4.size());


    }




    @Test
    public void testBuildRouteAirlineChartFirst() {

        // Creating and setting the data ArrayList
        ArrayList<Object> testDataFinal1 = new ArrayList<>(Arrays.asList(1, "A1", "ia1", "ia2", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal2 = new ArrayList<>(Arrays.asList(2, "A1", "ia2", "ia3", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal3 = new ArrayList<>(Arrays.asList(3, "A2", "ia1", "ia2", "Y", 6, "GPS"));

        ArrayList<ArrayList<Object>> data1 = new ArrayList<>();
        data1.add(testDataFinal1);
        data1.add(testDataFinal2);
        data1.add(testDataFinal3);

        // Creating the XYChart.Series that we are wanting to compare
        RouteGraphChart instance = new RouteGraphChart(data1);
        instance.setSelection("RouteAirline");
        XYChart.Series<String, Number> test = instance.buildBarChart();

        ArrayList<XYChart.Data<String, Number>> expectedResult = new ArrayList<>();
        expectedResult.add(new XYChart.Data<>("ia2 - ia3", 1));
        expectedResult.add(new XYChart.Data<>("ia1 - ia2", 2));

        // Comparing the expected results to the acutual results
        for (int i = 0; i < test.getData().size(); i++) {
            Assert.assertEquals(expectedResult.get(i).getXValue(), test.getData().get(i).getXValue());
            Assert.assertEquals(expectedResult.get(i).getYValue(), test.getData().get(i).getYValue());
        }


    }




    @Test
    public void testBuildRouteAirlineChartSecond() {

        // Creating and setting the data ArrayList
        ArrayList<Object> testDataFinal1 = new ArrayList<>(Arrays.asList(1, "A1", "ia1", "ia2", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal2 = new ArrayList<>(Arrays.asList(2, "A1", "ia2", "ia3", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal3 = new ArrayList<>(Arrays.asList(3, "A2", "ia1", "ia2", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal4 = new ArrayList<>(Arrays.asList(4, "A3", "ia1", "ia2", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal5 = new ArrayList<>(Arrays.asList(5, "A4", "ia2", "ia3", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal6 = new ArrayList<>(Arrays.asList(6, "A3", "ia2", "ia1", "Y", 6, "GPS"));

        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        data.add(testDataFinal1);
        data.add(testDataFinal2);
        data.add(testDataFinal3);
        data.add(testDataFinal4);
        data.add(testDataFinal5);
        data.add(testDataFinal6);

        RouteGraphChart instance2 = new RouteGraphChart(data);

        // Populating database with extra test data
        airlineService.save("airline3", "AIR3", "A3", "AR3", "AIRLINE3", "country3", "Y");
        airlineService.save("airline4", "AIR4", "A4", "AR4", "AIRLINE4", "country4", "Y");

        routeService.save("A3", "ia1", "ia2", "Y", 0, "GPS");
        routeService.save("A4", "ia2", "ia3", "Y", 0, "GPS");
        routeService.save("A3", "ia2", "ia1", "Y", 6, "GPS");

        // Preparing the XYChart that we are testing
        instance2.setSelection("RouteAirline");
        XYChart.Series<String, Number> test = instance2.buildBarChart();

        ArrayList<XYChart.Data<String, Number>> expectedResult = new ArrayList<>();
        expectedResult.add(new XYChart.Data<>("ia2 - ia1", 1));
        expectedResult.add(new XYChart.Data<>("ia2 - ia3", 2));
        expectedResult.add(new XYChart.Data<>("ia1 - ia2", 3));

        // Comparing the expected data to the actual data
        for (int i = 0; i < test.getData().size(); i++) {
            Assert.assertEquals(expectedResult.get(i).getXValue(), test.getData().get(i).getXValue());
            Assert.assertEquals(expectedResult.get(i).getYValue(), test.getData().get(i).getYValue());
        }

        // Checking case where an invalid selection is givenxc
        instance2.setSelection("EFN");
        XYChart.Series<String, Number> test2 = instance2.buildBarChart();

        Assert.assertEquals(0, test2.getData().size());

    }



    @Test
    public void testRouteAirlineChart() {

        // Creating and setting the data ArrayList
        ArrayList<Object> testDataFinal1 = new ArrayList<>(Arrays.asList(1, "A1", "ia1", "ia2", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal2 = new ArrayList<>(Arrays.asList(2, "A1", "ia2", "ia3", "Y", 6, "GPS"));
        ArrayList<Object> testDataFinal3 = new ArrayList<>(Arrays.asList(3, "A2", "ia1", "ia2", "Y", 6, "GPS"));


        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        data.add(testDataFinal1);
        data.add(testDataFinal2);
        data.add(testDataFinal3);

        RouteGraphChart instance = new RouteGraphChart(data);
        XYChart.Series<String, Number> test = instance.routeAirlineGraph();

        ArrayList<XYChart.Data<String, Number>> expectedResult = new ArrayList<>();
        expectedResult.add(new XYChart.Data<>("ia2 - ia3", 1));
        expectedResult.add(new XYChart.Data<>("ia1 - ia2", 2));

        for (int i = 0; i < test.getData().size(); i++) {
            Assert.assertEquals(expectedResult.get(i).getXValue(), test.getData().get(i).getXValue());
            Assert.assertEquals(expectedResult.get(i).getYValue(), test.getData().get(i).getYValue());
        }


    }



    @Test
    public void testSortAirlineChart() {
        // Creating the test data that will be sorted
        ArrayList<XYChart.Data<String, Integer>> testData = new ArrayList<>();
        testData.add(new XYChart.Data<String, Integer>("first", 5));
        testData.add(new XYChart.Data<String, Integer>("second", 3));
        testData.add(new XYChart.Data<String, Integer>("third", 8));
        testData.add(new XYChart.Data<String, Integer>("fourth", 2));
        testData.add(new XYChart.Data<String, Integer>("fifth", 1));


        RouteGraphChart routeGraphChart = new RouteGraphChart(null);

        // Calling the sort method
        XYChart.Series<String, Number> result = routeGraphChart.sortAirlineChart(testData);

        // Creating the expected result
        XYChart.Series<String, Number> expectedResult = new XYChart.Series<String, Number>();
        expectedResult.getData().add(new XYChart.Data<String, Number>("fifth", 1));
        expectedResult.getData().add(new XYChart.Data<String, Number>("fourth", 2));
        expectedResult.getData().add(new XYChart.Data<String, Number>("second", 3));
        expectedResult.getData().add(new XYChart.Data<String, Number>("first", 5));
        expectedResult.getData().add(new XYChart.Data<String, Number>("third", 8));

        // Comparing the expected result to the actual result
        for (int i = 0; i < result.getData().size(); i++) {
            Assert.assertEquals(expectedResult.getData().get(i).getXValue(), testData.get(i).getXValue());
            Assert.assertEquals(expectedResult.getData().get(i).getYValue(), testData.get(i).getYValue());
        }

    }



    @Test
    public void testRouteEquipmentGraph() {

        // Creating first test data
        ArrayList<ArrayList<Object>> exampleData = new ArrayList<>();
        List<String> route1Equip = List.of("GPS", "EKG");
        List<Object> route1 = List.of(1, "a1", 1, "s1", 1, "d1", 1, "Y", 3, new ArrayList<>(route1Equip));
        exampleData.add(new ArrayList<>(route1));
        List<String> route2Equip = List.of("GPS", "LED", "PNF");
        List<Object> route2 = List.of(2, "a2", 2, "s2", 2, "d1", 2, "Y", 3, new ArrayList<>(route2Equip));
        exampleData.add(new ArrayList<>(route2));
        List<String> route3Equip = List.of("GPS", "LED", "POE");
        List<Object> route3 = List.of(3, "a3", 3, "s3", 3, "d3", 3, "Y", 3, new ArrayList<>(route3Equip));
        exampleData.add(new ArrayList<>(route3));

        RouteGraphChart testGraph = new RouteGraphChart(exampleData);
        ObservableList<PieChart.Data> resultActual = testGraph.routeEquipmentGraph();

        // Creating expected results
        ObservableList<PieChart.Data> resultExpected = FXCollections.observableArrayList();
        resultExpected.add(new PieChart.Data("GPS", 3));
        resultExpected.add(new PieChart.Data("LED", 2));
        resultExpected.add(new PieChart.Data("EKG", 1));
        resultExpected.add(new PieChart.Data("PNF", 1));
        resultExpected.add(new PieChart.Data("POE", 1));

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected.size(); i++) {
            Assert.assertEquals(resultActual.get(i).getPieValue(), resultExpected.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(resultActual.get(i).getName(), resultExpected.get(i).getName());
        }



        // Expanding initial data to cover > 15 number case
        List<String> route4Equip = List.of("GPS", "EKG", "NDL", "FND", "FNS", "NAD", "FNF", "GNE");
        List<Object> route4 = List.of(1, "a1", 1, "s1", 1, "d1", 1, "Y", 3, new ArrayList<>(route4Equip));
        exampleData.add(new ArrayList<>(route4));
        List<String> route5Equip = List.of("GPS", "LED", "PNF", "NDL", "FND", "FNS", "ENP", "ARW", "ENG");
        List<Object> route5 = List.of(2, "a2", 2, "s2", 2, "d1", 2, "Y", 3, new ArrayList<>(route5Equip));
        exampleData.add(new ArrayList<>(route5));
        List<String> route6Equip = List.of("GPS", "LED", "POE", "END", "QJN", "FND", "AWN", "IEU", "EYR");
        List<Object> route6 = List.of(3, "a3", 3, "s3", 3, "d3", 3, "Y", 3, new ArrayList<>(route6Equip));
        exampleData.add(new ArrayList<>(route6));

        // Creating test graph
        RouteGraphChart testGraph2 = new RouteGraphChart(exampleData);
        ObservableList<PieChart.Data> resultActual2 = testGraph.routeEquipmentGraph();


        // Creating expected results
        ObservableList<PieChart.Data> resultExpected2 = FXCollections.observableArrayList();
        resultExpected2.add(new PieChart.Data("GPS", 6));
        resultExpected2.add(new PieChart.Data("LED", 4));
        resultExpected2.add(new PieChart.Data("FND", 3));
        resultExpected2.add(new PieChart.Data("EKG", 2));
        resultExpected2.add(new PieChart.Data("PNF", 2));
        resultExpected2.add(new PieChart.Data("FNS", 2));
        resultExpected2.add(new PieChart.Data("NDL", 2));
        resultExpected2.add(new PieChart.Data("POE", 2));
        resultExpected2.add(new PieChart.Data("ARW", 1));
        resultExpected2.add(new PieChart.Data("ENP", 1));
        resultExpected2.add(new PieChart.Data("IEU", 1));
        resultExpected2.add(new PieChart.Data("ENG", 1));
        resultExpected2.add(new PieChart.Data("END", 1));
        resultExpected2.add(new PieChart.Data("QJN", 1));
        resultExpected2.add(new PieChart.Data("GNE", 1));
        resultExpected2.add(new PieChart.Data("Other (3)", 3));


        // Comparing actual to expected results
        for (int i = 0; i < resultExpected2.size(); i++) {
            Assert.assertEquals(resultActual2.get(i).getPieValue(), resultExpected2.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(resultActual2.get(i).getName(), resultExpected2.get(i).getName());
        }


    }


    @Test
    public void testSortChartList() {

        // Creating first test data
        ArrayList<ArrayList<Object>> exampleData = new ArrayList<>();
        List<String> route1Equip = List.of("GPS", "EKG");
        List<Object> route1 = List.of(1, "a1", 1, "s1", 1, "d1", 1, "Y", 3, new ArrayList<>(route1Equip));
        exampleData.add(new ArrayList<>(route1));
        List<String> route2Equip = List.of("GPS", "LED", "PNF");
        List<Object> route2 = List.of(2, "a2", 2, "s2", 2, "d1", 2, "Y", 3, new ArrayList<>(route2Equip));
        exampleData.add(new ArrayList<>(route2));
        List<String> route3Equip = List.of("GPS", "LED", "POE");
        List<Object> route3 = List.of(3, "a3", 3, "s3", 3, "d3", 3, "Y", 3, new ArrayList<>(route3Equip));
        exampleData.add(new ArrayList<>(route3));

        RouteGraphChart testGraphChart = new RouteGraphChart(exampleData);

        // Creating unsorted and sorted observable arraylists
        ObservableList<PieChart.Data> unSorted = FXCollections.observableArrayList();
        unSorted.add(new PieChart.Data("equip1", 7));
        unSorted.add(new PieChart.Data("equip3", 10));
        unSorted.add(new PieChart.Data("equip4", 2));
        unSorted.add(new PieChart.Data("equip2", 5));

        ObservableList<PieChart.Data> sorted = FXCollections.observableArrayList();
        sorted.add(new PieChart.Data("equip3", 10));
        sorted.add(new PieChart.Data("equip1", 7));
        sorted.add(new PieChart.Data("equip2", 5));
        sorted.add(new PieChart.Data("equip4", 2));

        ObservableList<PieChart.Data> result = testGraphChart.sortChartList(unSorted);

        // Comparing actual to expected results
        for (int i = 0; i < sorted.size() - 1; i++) {
            Assert.assertEquals(result.get(i).getPieValue(), sorted.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(result.get(i).getName(), sorted.get(i).getName());
        }
    }




}
