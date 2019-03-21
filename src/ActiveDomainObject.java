package workoutDiary;

import java.sql.*;

public abstract class ActiveDomainObject {
    public abstract void save (Connection conn);
}
