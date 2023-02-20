package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Edge;

public class EdgeDAO extends ListDAOBase<Edge> {
    public EdgeDAO(DAOFacade orm) {
        super(orm, Edge.class);
    }
}
