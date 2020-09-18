package seng202.team5.table;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FlightComparatorTest {



    @Test
    public void testComparator() {
        // Creating lists to be sorted
        ArrayList<Object> list1 = new ArrayList<>();
        ArrayList<Object> list2 = new ArrayList<>();
        ArrayList<Object> list3 = new ArrayList<>();
        ArrayList<Object> list4 = new ArrayList<>();
        ArrayList<Object> list5 = new ArrayList<>();
        ArrayList<Object> list6 = new ArrayList<>();
        ArrayList<Object> list7 = new ArrayList<>();
        ArrayList<Object> list8 = new ArrayList<>();
        ArrayList<ArrayList<Object>> mainList = new ArrayList<>();
        mainList.add(list1);
        mainList.add(list2);
        mainList.add(list3);
        mainList.add(list4);
        mainList.add(list5);
        mainList.add(list6);
        mainList.add(list7);
        mainList.add(list8);

        // Populating lists with test data
        list1.add(4);
        list1.add(5);
        list2.add(3);
        list2.add(6);
        list3.add(5);
        list3.add(5);
        list4.add(7);
        list4.add(0);
        list5.add(2);
        list5.add(0);
        list6.add(8);
        list6.add(0);
        list7.add(5);
        list7.add(5);
        list8.add(9);
        list8.add(5);


        // Arranging lists in expected order
        ArrayList<ArrayList<Object>> expectedResult = new ArrayList<>();
        expectedResult.add(list5);
        expectedResult.add(list2);
        expectedResult.add(list1);
        expectedResult.add(list3);
        expectedResult.add(list7);
        expectedResult.add(list4);
        expectedResult.add(list6);
        expectedResult.add(list8);

        // Sorting list using flightComparator
        mainList.sort(new FlightComparator());

        // Checking equality
        assertEquals(expectedResult, mainList);
    }


}
