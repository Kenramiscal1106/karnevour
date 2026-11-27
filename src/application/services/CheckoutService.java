package application.services;

import application.models.CartLine;
import database.CashRegister;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CheckoutService extends Service<Void> {
    private final List<CartLine> lines;
    private final CashRegister cashRegister = CashRegister.getInstance();
    private final String soldToName;

    public CheckoutService(List<CartLine> lines, String soldToName) {
        this.lines = List.copyOf(lines);
        this.soldToName = soldToName;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                var user = SessionHandler.getInstance().getCurrentUser();
                var sell = new FutureTask<Void>(() -> {
                    for (CartLine line : lines) {
                        cashRegister.sellItem(line.getItemHolder().getUid(), line.getQty(), line.getTotal(), user, soldToName);
                    }
                    return null;
                });
                Platform.runLater(sell);

                try {
                    sell.get();
                } catch (ExecutionException e) {
                    throw (Exception) e.getCause();
                }
                return null;
            }
        };
    }
}
