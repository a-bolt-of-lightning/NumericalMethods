package numericalmethods;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Driver class used in the implementation of numerical methods
 * for visualizing first order differential equations
 *
 * @author F Mahvari
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //equation info:
        //y'(x)=-2y(x)+cos(4x) with y(0)=3
        //y = 0.1*cos(4*x)+0.2*sin(4*x)+2.9*exp(-2*x);

        VBox root = new VBox();

        MenuBar menuBar = new MenuBar();

        setMenuBar(menuBar);

        NumericalVisualization graph = new NumericalVisualization(0, 3);


        root.getChildren().addAll(menuBar, graph);

        Scene scene =  new Scene(root, 1000, 670);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Numerical Method");
        primaryStage.show();

    }

    private static void setMenuBar(MenuBar menuBar){


        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().add(helpMenu);

        MenuItem helpItem = new MenuItem("Help");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().addAll(helpItem, aboutItem);

        aboutItem.setOnAction(e->{
            About aboutPage = new About();
            setAboutPage(aboutPage);

            Stage secondStage =
                    new Stage();
            Scene secondScene = new Scene(aboutPage, 400, 200);
            secondStage.setScene(secondScene);
            secondStage.show();
        });
    }

    private static void setAboutPage(Pane aboutPage){


    }

    public static void main(String[] args) {
        launch(args);
    }
}
