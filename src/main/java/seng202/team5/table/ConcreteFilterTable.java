package seng202.team5.table;

import java.util.ArrayList;

public class ConcreteFilterTable implements FilterTable {

    public ArrayList<ArrayList> elements;
    public ArrayList<ArrayList> finalElements;
    public int currentPos;

    public ConcreteFilterTable(ArrayList data) {
        elements = data;
        finalElements = new ArrayList();
    }

    public void FilterTable() {}

    public ArrayList getNext() {
        if (hasMore()) {
            currentPos++;
            return elements.get(currentPos);
        }
        return null;
    }

    public boolean hasMore() {
        return currentPos != elements.size();
    }
}
