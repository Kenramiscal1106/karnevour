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
    BEEF_CHUCK(GeneralProduct.CHICKEN),
    BEEF_BRISKET(GeneralProduct.CHICKEN),
    BEEF_RIBEYE(GeneralProduct.CHICKEN),
    BEEF_LOIN(GeneralProduct.CHICKEN),
    BEEF_PLATE(GeneralProduct.CHICKEN),
    BEEF_FLANK(GeneralProduct.CHICKEN),
    BEEF_SHORT_RIBS(GeneralProduct.CHICKEN),
    BEEF_SIRLOIN(GeneralProduct.CHICKEN),
    BEEF_TENDERLOIN(GeneralProduct.CHICKEN),

    //OTHERNINI
    EGG_SMALL(GeneralProduct.OTHER),
    EGG_MEDIUM(GeneralProduct.OTHER),
    EGG_LARGE(GeneralProduct.OTHER),
    MILK_COW(GeneralProduct.OTHER);


    private final GeneralProduct generalProduct;

    private SpecificProduct(GeneralProduct generalProduct){
        this.generalProduct = generalProduct;
    }

    public GeneralProduct getGeneralProductType()   {  return this.generalProduct;  }
}
