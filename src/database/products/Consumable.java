package database.products;

public class Consumable extends Item{
    public Consumable(String uid, SpecificItem specificItemType, String unit, String description, String alternativeName) {
        super(uid, specificItemType, unit, description, alternativeName);
    }

    public Consumable(String uid, SpecificItem specificItemType, String unit, String description) {
        super(uid, specificItemType, unit, description);
    }
}
