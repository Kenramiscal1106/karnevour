package database.items;

public class FarmItem extends Item {
    public FarmItem(SpecificItem enumConst, String description, String altName) {
        super(enumConst, description, altName);
    }

    public FarmItem(SpecificItem enumConst, String description){
        super(enumConst,description);
    }
}
