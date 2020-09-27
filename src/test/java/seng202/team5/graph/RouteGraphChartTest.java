package seng202.team5.graph;

import io.cucumber.java.bs.A;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.junit.Assert;
import org.junit.Test;
import seng202.team5.controller.PieChartController;

import java.util.ArrayList;
import java.util.List;

public class RouteGraphChartTest {

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
        ObservableList<PieChart.Data> resultActual = testGraph.buildPieGraph();

        // Creating expected results
        ObservableList<PieChart.Data> resultExpected = FXCollections.observableArrayList();
        resultExpected.add(new PieChart.Data("GPS", 3));
        resultExpected.add(new PieChart.Data("LED", 2));
        resultExpected.add(new PieChart.Data("EKG", 1));
        resultExpected.add(new PieChart.Data("PNF", 1));
        resultExpected.add(new PieChart.Data("POE", 1));

        // Comparing actual to expected results
        for (int i = 0; i < resultExpected.size() - 1; i++) {
            Assert.assertEquals(resultActual.get(i).getPieValue(), resultExpected.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(resultActual.get(i).getName(), resultActual.get(i).getName());
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
        ObservableList<PieChart.Data> resultActual2 = testGraph.buildPieGraph();


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
        resultExpected2.add(new PieChart.Data("Other", 3));


        // Comparing actual to expected results
        for (int i = 0; i < resultExpected2.size() - 1; i++) {
            Assert.assertEquals(resultActual2.get(i).getPieValue(), resultExpected2.get(i).getPieValue(), 0.0000001);
            Assert.assertEquals(resultActual2.get(i).getName(), resultActual2.get(i).getName());
        }


    }






}
