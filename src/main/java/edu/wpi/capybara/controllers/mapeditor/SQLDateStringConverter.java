package edu.wpi.capybara.controllers.mapeditor;

import javafx.util.StringConverter;

import java.sql.Date;

public class SQLDateStringConverter extends StringConverter<Date> {
    @Override
    public String toString(Date object) {
        return object.toString();
    }

    @Override
    public Date fromString(String string) {
        return Date.valueOf(string);
    }
}
