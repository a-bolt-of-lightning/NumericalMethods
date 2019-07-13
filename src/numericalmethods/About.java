package numericalmethods;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class About extends BorderPane {

    public  About() {

        setPrefSize(600, 200);

        ImageView kntuImg = new ImageView(
                new Image(getClass().getResourceAsStream("/img/kntu.png")));

        Label developerInfo = new Label("Developed by F Mahvari\nUnder the supervision of Dr. Aliakbarian");
        BorderPane.setAlignment(developerInfo, Pos.CENTER);
        setLeft(developerInfo);
        setRight(kntuImg);

    }
}
