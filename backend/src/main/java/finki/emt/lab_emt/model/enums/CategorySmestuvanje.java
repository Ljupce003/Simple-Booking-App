package finki.emt.lab_emt.model.enums;

import java.util.List;

public enum CategorySmestuvanje {
    ROOM,
    HOUSE,
    FLAT,
    APARTMENT,
    HOTEL,
    MOTEL;


    public static List<CategorySmestuvanje> getValues(){
        return List.of(CategorySmestuvanje.values());
    }
}
