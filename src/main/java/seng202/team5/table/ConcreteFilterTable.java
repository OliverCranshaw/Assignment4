package seng202.team5.table;

import java.util.ArrayList;

public class ConcreteFilterTable implements FilterTable {

    public ArrayList<ArrayList> elements;
    public int currentPos;

    public ConcreteFilterTable(ArrayList data) {
        elements = data;
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
