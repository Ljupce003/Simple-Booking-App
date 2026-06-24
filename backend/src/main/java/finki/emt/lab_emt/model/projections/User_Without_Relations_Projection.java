package finki.emt.lab_emt.model.projections;

import finki.emt.lab_emt.model.enums.UserRole;

public interface User_Without_Relations_Projection {
    String getUsername();
    UserRole getRole();
    String getName();
    String getSurname();

}
