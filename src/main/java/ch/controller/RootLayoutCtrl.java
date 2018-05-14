package ch.controller;

import ch.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RootLayoutCtrl {

    @FXML
    private BorderPane rootLayout;
    @FXML
    private VBox sidePaneLeft;
    @FXML
    private VBox sidePaneRight;
    @FXML
    private VBox centerView;

    private MainApp mainAppRef;
    private VBox mostFrequentTable;

    /**
     * Constructor
     */
    public RootLayoutCtrl(MainApp inputMainAppRef)
    {this.mainAppRef = inputMainAppRef; }


    public void initRootLayout(Stage inputPrimaryStage) {
        FXMLLoader loader = new FXMLLoader();

        try {
            // Load root layout from fxml file.
            loader.setLocation(RootLayoutCtrl.class.getResource("/ch/view/RootLayout.fxml"));
            loader.setController(this);
            rootLayout = loader.<BorderPane>load();

            //VBox mySidepaneLeft = new VBox();
            sidePaneLeft.setSpacing(20); //laver mellemrum mellem objekterne i VBox'en.
            sidePaneLeft.setPadding(new Insets(5, 5, 10, 10)); //Sætter objekternes afstand fra kanterne
            //sidePaneLeft = mySidepaneLeft;
            rootLayout.setLeft(sidePaneLeft);
            BorderPane.setAlignment(sidePaneLeft, Pos.TOP_LEFT);

            //VBox mySidepaneRight = new VBox();
            sidePaneRight.setSpacing(20); //laver mellemrum mellem objekterne i VBox'en.
            sidePaneRight.setPadding(new Insets(5, 10, 10, 5)); //Sætter objekternes afstand fra kanterne
            //sidePaneRight = mySidepaneRight;
            rootLayout.setRight(sidePaneRight);
            BorderPane.setAlignment(sidePaneRight, Pos.TOP_RIGHT);

            //VBox myCenterView = new VBox();
            //VBox.setVgrow(centerView, Priority.ALWAYS);
            //myCenterView.setSpacing(20); //laver mellemrum mellem objekterne i VBox'en.
            centerView.setPadding(new Insets(5, 5, 5, 5)); //Sætter objekternes afstand fra kanterne
            //centerView = myCenterView;
            rootLayout.setCenter(centerView);
            centerView.setAlignment(Pos.CENTER);


            this.rootLayout = rootLayout;

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(rootLayout);
        inputPrimaryStage.setScene(scene);
        inputPrimaryStage.show();
        inputPrimaryStage.setFullScreen(false);
    }

    public void initBasicLayout(){


                 try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PractitionerCtrl.class.getResource("/ch/view/PractitionerView.fxml"));
                HBox practitionerView = (HBox) loader.load();

                sidePaneLeft.getChildren().add(practitionerView);

                PractitionerCtrl controller = loader.getController();
                controller.setMainApp(mainAppRef);
                controller.setPractitioner();


            } catch (IOException e) {
                e.printStackTrace();
            }


        PatientCtrl patientCtrl = new PatientCtrl();
        this.sidePaneLeft = patientCtrl.showPatient(sidePaneLeft);

        MedicationCtrl medicationCtrl = new MedicationCtrl();
        this.sidePaneLeft = medicationCtrl.showMedication(sidePaneLeft);

        ConditionCtrl conditionCtrl = new ConditionCtrl();
        this.sidePaneRight = conditionCtrl.showConditionView(sidePaneRight);

        AllergyIntoleranceCtrl allergyIntoleranceCtrl = new AllergyIntoleranceCtrl();
        this.sidePaneRight = allergyIntoleranceCtrl.showAllergyIntolerance(sidePaneRight);
    }

    public void showOverview() {
        OverviewCtrl overviewCtrl = new OverviewCtrl();
        try {
            FXMLLoader loader = new FXMLLoader();
            //Tabellen med hyppigst, der skal sættes i den højre sidebar.
            loader.setLocation(getClass().getResource("/ch/view/Overview.fxml"));
            loader.setController(overviewCtrl);
            AnchorPane overview = loader.load();

            centerView.getChildren().setAll(overview);

            OverviewCtrl controller = loader.getController();
            controller.setRootLayoutCtrlRef(this);

            //centerView.setFillWidth(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!sidePaneRight.getChildren().contains(mostFrequentTable)) {
            try {
                FXMLLoader loader = new FXMLLoader();
                //Tabellen med hyppigst, der skal sættes i den højre sidebar.
                loader.setLocation(getClass().getResource("/ch/view/MostFrequentTable.fxml"));
                loader.setController(overviewCtrl);
                mostFrequentTable = (VBox) loader.load();

                //Da man skifte frem og tilbage ml. weekly og overview sørger vi her for at mostFrequent-tabellen ikke tilføjes
                // igen hver gang så der kommer flere end én.

                //if(!sidePaneRight.getChildren().contains(mostFrequentTable)){
                sidePaneRight.getChildren().add(mostFrequentTable);
                //}


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showWeeklyOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ch/view/WeeklyOverviewView.fxml"));
            //loader.setController(WeeklyOverviewCtrl);
            AnchorPane weeklyOverview = loader.load();

            centerView.getChildren().setAll(weeklyOverview);

            WeeklyOverviewCtrl controller = loader.getController();
            controller.setRootLayoutCtrlRef(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCreateAsthmaAppUserView(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CreateAsthmaAppUserCtrl.class.getResource("/ch/view/CreateAsthmaAppUserView.fxml"));
            AnchorPane createAstmaAppUserView = (AnchorPane) loader.load();

            centerView.getChildren().add(createAstmaAppUserView);
            //this.centerView = centerView;

            CreateAsthmaAppUserCtrl controller = loader.getController();
            controller.setMainApp(mainAppRef);
            controller.setRootLayoutCtrlRef(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSummaryView(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SummaryCtrl.class.getResource("/ch/view/Summary.fxml"));
            AnchorPane summaryView = (AnchorPane) loader.load();

            //skal laves om da der istedet skal laves en bottompane.
            rootLayout.setBottom(summaryView);
            BorderPane.setAlignment(summaryView, Pos.BOTTOM_CENTER);

            SummaryCtrl controller = loader.getController();
            controller.setMainApp(mainAppRef);
            controller.setRootLayoutCtrlRef(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public VBox getSidepaneLeft(){
        return this.sidePaneLeft;
    }
    public VBox getSidepaneRight(){
        return this.sidePaneRight;
    }
    public VBox getCenterView(){
        return this.centerView;
    }

}
