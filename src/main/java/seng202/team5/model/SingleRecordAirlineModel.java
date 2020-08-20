package seng202.team5.model;

import javafx.beans.property.SimpleStringProperty;
import seng202.team5.service.AirlineService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleRecordAirlineModel {
    private AirlineService service = new AirlineService();

    public final int ID;

    public final SimpleStringProperty nameProperty = new SimpleStringProperty();
    public final SimpleStringProperty aliasProperty = new SimpleStringProperty();
    public final SimpleStringProperty iataProperty = new SimpleStringProperty();
    public final SimpleStringProperty icaoProperty = new SimpleStringProperty();
    public final SimpleStringProperty callsignProperty = new SimpleStringProperty();
    public final SimpleStringProperty countryProperty = new SimpleStringProperty();
    public final SimpleStringProperty activeProperty = new SimpleStringProperty();

    public SingleRecordAirlineModel(int ID) {
        this.ID = ID;
        update();
    }

    private void update() {
        ResultSet resultSet = service.getAirline(ID);

        try {
            nameProperty.set(resultSet.getString(2));
            aliasProperty.set(resultSet.getString(3));
            iataProperty.set(resultSet.getString(4));
            icaoProperty.set(resultSet.getString(5));
            callsignProperty.set(resultSet.getString(6));
            countryProperty.set(resultSet.getString(7));
            activeProperty.set(resultSet.getString(8));
        } catch (SQLException e) {
            System.out.println(e);
            return;
        }
    }
}
