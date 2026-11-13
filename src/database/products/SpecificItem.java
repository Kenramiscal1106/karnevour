package database.products;

public enum SpecificItem {
    //CHIKININI
    CHICKEN_BREAST(GeneralItem.CHICKEN),
    CHICKEN_THIGHS(GeneralItem.CHICKEN),
    CHICKEN_LEG_QUARTER(GeneralItem.CHICKEN),
    CHICKEN_WINGS(GeneralItem.CHICKEN),
    CHICKEN_SKIN(GeneralItem.CHICKEN),
    CHICKEN_FEET(GeneralItem.CHICKEN),
    WHOLE_CHICKEN(GeneralItem.CHICKEN),

    //PIGGYNINI
    PORK_SHOULDER(GeneralItem.PIG),
    PORK_BELLY(GeneralItem.PIG),
    PORK_MASCARA(GeneralItem.PIG),
    PORK_LEG(GeneralItem.PIG),
    PORK_CHOP(GeneralItem.PIG),
    PORK_RIBS(GeneralItem.PIG),

    //COWNINI
    BEEF_CHUCK(GeneralItem.COW),
    BEEF_BRISKET(GeneralItem.COW),
    BEEF_RIBEYE(GeneralItem.COW),
    BEEF_LOIN(GeneralItem.COW),
    BEEF_PLATE(GeneralItem.COW),
    BEEF_FLANK(GeneralItem.COW),
    BEEF_SHORT_RIBS(GeneralItem.COW),
    BEEF_SIRLOIN(GeneralItem.COW),
    BEEF_TENDERLOIN(GeneralItem.COW),

    //OTHERNINI
    EGG_SMALL(GeneralItem.OTHER),
    EGG_MEDIUM(GeneralItem.OTHER),
    EGG_LARGE(GeneralItem.OTHER),
    MILK_COW(GeneralItem.OTHER),

    //DEFAULTNINI
    NOT_FOUND(GeneralItem.OTHER),

    //FEEDININI
    CHICKEN_FEED(GeneralItem.FARM_ITEM),
    COW_FEED(GeneralItem.FARM_ITEM),
    PIG_FEED(GeneralItem.FARM_ITEM);

    private final GeneralItem generalItem;

    private SpecificItem(GeneralItem generalItem){
        this.generalItem = generalItem;
    }

    public GeneralItem getGeneralProductType()   {  return this.generalItem;  }
}
