package numericalmethods;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;


/**
 * implementation of different Numerical methods for visualizing
 * first order differential equations
 *
 * the following code demonstrates different
 * approaches for solving the following
 * equation:
 * y'(x)=-2y(x)+cos(4x) with y(0)=3
 *
 * note: the exact solution for this equation
 * is:
 * y = 0.1*cos(4*x)+0.2*sin(4*x)+2.9*exp(-2*x);
 *
 * @author F Mahvari
 */


public class NumericalVisualization extends BorderPane {


    //VERY IMPORTANT
    private double stepSize =0.05;

    private Group group;

    private Pane infoPane;
    private Pane graphPane;

    private double initialX ;
    private double initialY ;

    private Slider stepSizeSlider;

    private Group curveChunksGroup = new Group();
    private Group exactCurveChunksGroup = new Group();
    private Group RK2CurveChunksGroup = new Group();
    private Group RK4CurveChunksGroup = new Group();


    public NumericalVisualization(double initialX, double initialY){

        setPadding(new Insets(10, 10, 10, 10));

        this.initialX = initialX;
        this.initialY = initialY;

        group = new Group();
        infoPane = new StackPane();
        graphPane = new Pane();


        infoPane.setPrefSize(350, 600);

        graphPane.setPrefSize(600, 600);

        //set the graph
        setGraph();

        //set the info
        setInfo();

    }

    private void setInfo(){

        BorderPane sliderContainer = new BorderPane();

        GridPane curvesInfoPane = new GridPane();
        setCurvesInfoPane(curvesInfoPane);

        HBox sliderHBox = new HBox();
        setSliderHBox(sliderHBox);


        VBox equationVBox = new VBox();
        setEquationVBox(equationVBox);


        sliderContainer.setTop(curvesInfoPane);
        BorderPane.setAlignment(curvesInfoPane, Pos.BOTTOM_CENTER);
        sliderContainer.setBottom(equationVBox);
        BorderPane.setAlignment(equationVBox, Pos.TOP_CENTER);
        sliderContainer.setCenter(sliderHBox);


        infoPane.getChildren().addAll(sliderContainer);

        stepSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                stepSize = newValue.doubleValue();
                Platform.runLater(()->{
                    drawCurves();
                });

            }
        });

        setLeft(infoPane);
    }

    private void setSliders(){

        stepSizeSlider = new Slider(0.05, 0.5, 0.05);
        stepSizeSlider.setBlockIncrement(0.05);
        stepSizeSlider.setMajorTickUnit(0.05);
        stepSizeSlider.setMinorTickCount(0);
        stepSizeSlider.setShowTickLabels(true);
        stepSizeSlider.setSnapToTicks(true);
        stepSizeSlider.setValue(0.05);
        stepSizeSlider.setPrefWidth(200);

    }

    private void setGraph(){

        //draw grid
        drawGrid();

        //draw curve
        drawCurves();

        setRight(graphPane);
    }


    private void drawGrid(){

        Group gridGroup = new Group();

        Line xAxis = new Line(0, 600, 600, 600);
        Line yAxis = new Line(0, 0, 0, 600);

        xAxis.setStroke(Color.RED);
        yAxis.setStroke(Color.RED);

        gridGroup.getChildren().addAll(xAxis, yAxis);
        graphPane.getChildren().add(gridGroup);

        for(int i = 0; i <= 20; i++) {
            Line gridLineY = new Line(0, 600 * i / 20, 600, 600 * i / 20);
            Line gridLineX = new Line(600 * i / 20, 0, 600 * i / 20, 600);

            gridLineX.setStroke(Color.color(0, 0, 0, 0.15));
            gridLineY.setStroke(Color.color(0, 0, 0, 0.15));

            graphPane.getChildren().addAll(gridLineY, gridLineX);
        }

        for(int i=0; i<=5; i++){

            int yOffset =15;

            String xLabel = i-2 + "";
            String yLabel = i-2 + "";

            Text xAxisGridScale = new Text(120*i, 600 + yOffset, xLabel);
            Text yAxisGridScale = new Text(0 - yOffset, 600 - 120*i, yLabel);

            graphPane.getChildren().addAll(xAxisGridScale, yAxisGridScale);
        }
    }


    private void drawCurves(){

        curveChunksGroup.getChildren().clear();
        exactCurveChunksGroup.getChildren().clear();
        RK2CurveChunksGroup.getChildren().clear();
        RK4CurveChunksGroup.getChildren().clear();



        double xStartEx = initialX;
        double xEndEx = 0;
        double yStartEx  = initialY;
        double yEndEx=0;


        double xStart = initialX;
        double yStart =initialY;
        double yStartRK2 = initialY;
        double yEndRK2;
        double yStartRK4 = initialY;
        double yEndRK4;
        double xEnd=0;
        double yEnd=0;


        while((xEndEx*120<600 && 600-yEndEx*120>0)){
            yEndEx = getExactEndPoint(0.05, xStartEx, yStartEx).getValue();
            xEndEx = getExactEndPoint(0.05, xStartEx, yStartEx).getKey();
            Line exactCurveChunk = new Line(xStartEx*120, (360-yStartEx*120), xEndEx*120, (360-yEndEx*120));
            exactCurveChunksGroup.getChildren().add(exactCurveChunk);

            xStartEx = xEndEx;
            yStartEx = yEndEx;

        }


        while((xEnd*120<600 && 600-yEnd*120>0)){



            xEnd = xStart + stepSize;

            yEnd = getEulersEndPoint(stepSize, xStart, yStart).getValue();

            yEndRK2 = getRKEndPoint(stepSize, xStart, yStartRK2, 2).getValue();

            yEndRK4 = getRKEndPoint(stepSize, xStart, yStartRK4, 4).getValue();


            Line eulersCurveChunk = new Line(xStart*120, (360-yStart*120), xEnd*120, (360-yEnd*120));
            eulersCurveChunk.setStroke(Color.BLUE);

            Line RK2CurveChunk = new Line(xStart*120, (360-yStartRK2*120), xEnd*120, (360-yEndRK2*120));
            RK2CurveChunk.setStroke(Color.RED);

            Line RK4CurveChunk = new Line(xStart*120, (360-yStartRK4*120), xEnd*120, (360-yEndRK4*120));
            RK4CurveChunk.setStroke(Color.GREEN);

            curveChunksGroup.getChildren().addAll(eulersCurveChunk);
            RK2CurveChunksGroup.getChildren().add(RK2CurveChunk);
            RK4CurveChunksGroup.getChildren().add(RK4CurveChunk);


            //System.out.printf("xs=%f  ys=%f  xe=%f  ye=%f  y'=%f %n", xStart, yStart, xEnd, yEnd, yPrime);

            xStart =xEnd;

            yStart = yEnd;

            yStartRK2 = yEndRK2;

            yStartRK4 = yEndRK4;

        }

        if(!graphPane.getChildren().contains(curveChunksGroup)){
            graphPane.getChildren().add(curveChunksGroup);
        }
        if(!graphPane.getChildren().contains(exactCurveChunksGroup)){
            graphPane.getChildren().add(exactCurveChunksGroup);
        }
        if(!graphPane.getChildren().contains(RK2CurveChunksGroup)){
            graphPane.getChildren().add(RK2CurveChunksGroup);
        }
        if(!graphPane.getChildren().contains(RK4CurveChunksGroup)){
            graphPane.getChildren().add(RK4CurveChunksGroup);
        }


    }


    private Pair<Double, Double> getRKEndPoint(double stepSize, double startX, double startY,int order ){


        //k1 = F(x, y)
        double k1 = getYPrime(startX, startY);


        double newX = startX + stepSize/2;
        double newY = startY + stepSize*k1/2;

        //k2 = h*F(x + h/2, y + h*k1/2)
        double k2 = getYPrime(newX, newY);

        double yEnd = startY + stepSize*k2;

        if(order == 2){
            return new Pair<>(startX+stepSize, yEnd);
        }
        else if(order==4){

            newY = startY + stepSize*k2/2;
            double k3 = getYPrime(newX, newY);
            newY = startY + stepSize*k3;
            newX = startX;
            double k4 = getYPrime(newX, newY);
            yEnd = startY + (k1+2*k2+2*k3+k4)*stepSize/6;

            return new Pair<>(startX+stepSize, yEnd);
        }
        return null;
    }


    private Pair<Double, Double> getEulersEndPoint(double stepSize, double startX, double startY){

        double yPrime = getYPrime(startX, startY);


        double xEnd = startX + stepSize;
        double yEnd = yPrime*stepSize + startY;

        return new Pair<>(xEnd, yEnd);
    }

    private Pair<Double, Double> getExactEndPoint(double stepSize, double startX, double startY){

        double endX = startX + stepSize;
        double endY = getExactY(startX, startY);

        return new Pair<>(endX, endY);
    }

    private double getYPrime(double x, double y){

        //y'(x)=-2y(x)+cos(4x) with y0=3
        return -2*y+Math.cos(4*x);

    }

    private double getExactY(double x, double y){

        //y = 0.1*cos(4*x)+0.2*sin(4*x)+2.9*exp(-2*x);
        return 0.1*Math.cos(4*x) + 0.2*Math.sin(4*x) + 2.9*Math.exp(-2*x);
    }

    private void setCurvesInfoPane(GridPane curvesInfoPane){

        curvesInfoPane.setHgap(10);
        curvesInfoPane.setVgap(10);

        curvesInfoPane.add(new Rectangle(20, 20, Color.BLACK), 0, 0);
        curvesInfoPane.add(new Rectangle(20, 20, Color.RED), 0, 1);
        curvesInfoPane.add(new Rectangle(20, 20, Color.GREEN), 0, 2);
        curvesInfoPane.add(new Rectangle(20, 20, Color.BLUE), 0, 3);
        curvesInfoPane.add(new Label("Exact"), 1, 0);
        curvesInfoPane.add(new Label("RK2"), 1, 1);
        curvesInfoPane.add(new Label("RK4"), 1, 2);
        curvesInfoPane.add(new Label("Euler's method"), 1, 3);

    }

    private void setSliderHBox(HBox sliderHBox){

        sliderHBox.setSpacing(20);
        sliderHBox.setPadding(new Insets(40, 10, 20, 10));

        ImageView stepSizeImg = new ImageView(
                new Image(getClass().getResourceAsStream("/img/stepSize.gif")));

        setSliders();

        sliderHBox.getChildren().addAll(stepSizeImg, stepSizeSlider);


    }

    private void setEquationVBox(VBox equationVBox){

        ImageView equationView = new ImageView(
                new Image(getClass().getResourceAsStream("/img/equation.gif")));
        ImageView initialValueView = new ImageView(
                new Image(getClass().getResourceAsStream("/img/initialValue.gif")));

        equationVBox.getChildren().addAll(equationView, initialValueView);
        equationVBox.setSpacing(20);
        equationVBox.setAlignment(Pos.CENTER);



    }
}
