package numericalmethods;

import javafx.application.Application;
import javafx.scene.Scene;
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

        NumericalVisualization graph = new NumericalVisualization(0, 3);

        Scene scene =  new Scene(graph, 1000, 650);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Numerical Method");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
