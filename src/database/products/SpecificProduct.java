package database.products;

public enum SpecificProduct {
    //CHIKININI
    CHICKEN_BREAST(GeneralProduct.CHICKEN),
    CHICKEN_THIGHS(GeneralProduct.CHICKEN),
    CHICKEN_LEG_QUARTER(GeneralProduct.CHICKEN),
    CHICKEN_WINGS(GeneralProduct.CHICKEN),
    CHICKEN_SKIN(GeneralProduct.CHICKEN),
    CHICKEN_FEET(GeneralProduct.CHICKEN),
    WHOLE_CHICKEN(GeneralProduct.CHICKEN),

    //PIGGYNINI
    PORK_SHOULDER(GeneralProduct.PIG),
    PORK_BELLY(GeneralProduct.PIG),
    PORK_MASCARA(GeneralProduct.PIG),
    PORK_LEG(GeneralProduct.PIG),
    PORK_CHOP(GeneralProduct.PIG),
    PORK_RIBS(GeneralProduct.PIG),

    //COWNINI
    BEEF_CHUCK(GeneralProduct.COW),
    BEEF_BRISKET(GeneralProduct.COW),
    BEEF_RIBEYE(GeneralProduct.COW),
    BEEF_LOIN(GeneralProduct.COW),
    BEEF_PLATE(GeneralProduct.COW),
    BEEF_FLANK(GeneralProduct.COW),
    BEEF_SHORT_RIBS(GeneralProduct.COW),
    BEEF_SIRLOIN(GeneralProduct.COW),
    BEEF_TENDERLOIN(GeneralProduct.COW),

    //OTHERNINI
    EGG_SMALL(GeneralProduct.OTHER),
    EGG_MEDIUM(GeneralProduct.OTHER),
    EGG_LARGE(GeneralProduct.OTHER),
    MILK_COW(GeneralProduct.OTHER);


    private final GeneralProduct generalItem;

    private SpecificProduct(GeneralProduct generalItem){
        this.generalItem = generalItem;
    }

    public GeneralProduct getGeneralProductType()   {  return this.generalItem;  }
}
