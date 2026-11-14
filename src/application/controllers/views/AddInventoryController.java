package application.controllers.views;

public class AddInventoryController extends OperationInventoryController{
    @Override
    protected String getActionType(){
        return "restock";
    }
}