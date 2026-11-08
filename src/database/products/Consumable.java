package database.products;

public class Consumable extends Item{
    public Consumable(SpecificItem specificItemType, String unit, String description, String alternativeName) {
        super(specificItemType, unit, description, alternativeName);
    }

    public Consumable(SpecificItem specificItemType, String unit, String description) {
        super(specificItemType, unit, description);
    }
}
