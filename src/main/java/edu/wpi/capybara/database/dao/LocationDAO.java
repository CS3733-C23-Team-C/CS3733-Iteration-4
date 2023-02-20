package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Location;

public class LocationDAO extends MapDAOBase<String, Location> {
    public LocationDAO(DAOFacade orm) {
        super(orm, Location.class, Location::getLongName);
    }
}
