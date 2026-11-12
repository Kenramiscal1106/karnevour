package database.products;

public class FarmItem extends Item {
    public FarmItem(SpecificProduct enumConst, String description, String altName) {
        super(enumConst, description, altName);
    }

    public FarmItem(SpecificProduct enumConst, String description){
        super(enumConst,description);
    }
}
