package application.controllers.components;

import application.stores.NavigationStore;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;

public class NavigationRailControllerBase {
    private static final String ACTIVE = "navigation-item--active";
    List<Button> buttons;
    List<ImageView> iconList;
    final NavigationStore navigationStore = NavigationStore.getInstance();

    void setActive(Button active, boolean filled) {
        buttons.forEach(b -> {
            b.getStyleClass().remove(ACTIVE);
        });
        iconList.forEach(i -> {
            i.setImage(
                    new Image(i
                            .getImage()
                            .getUrl()
                            .replace("-filled", "")
                    )
            );
        });
        active.getStyleClass().add(ACTIVE);
        active.getGraphic();
        if (!filled) return;

        var graphic = (VBox)active.getGraphic();

        if (graphic.getChildren().getFirst() instanceof ImageView imageView) {
            var image = imageView.getImage();
            imageView.setImage(new Image(image.getUrl().replace(".png", "") + "-filled.png"));
        }
    }
}
