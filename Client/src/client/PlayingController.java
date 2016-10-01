/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class PlayingController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    public static ScreenController playingScreen;
    @FXML
    private Label state;
    @FXML
    private ImageView playerColor;
    @FXML
    TableView<Information> table;
    @FXML
    TableColumn label1Col;
    @FXML
    TableColumn label2Col;
    @FXML
    TableColumn label3Col;
    @FXML
    TableColumn label4Col;
    @FXML
    TableColumn label5Col;
    @FXML
    TableColumn label6Col;
    @FXML
    TableColumn label7Col;
    @FXML
    TableColumn label8Col;
    @FXML
    TableColumn label9Col;
    @FXML
    TableColumn label10Col;
    @FXML
    TableColumn label11Col;
    @FXML
    TableColumn label12Col;
    @FXML
    TableColumn label13Col;
    @FXML
    TableColumn label14Col;
    @FXML
    TableColumn label15Col;
    @FXML
    TableColumn label16Col;
    @FXML
    TableColumn label17Col;
    @FXML
    TableColumn label18Col;
    @FXML
    TableColumn label19Col;
    @FXML
    Label score;

    private Button trigger;
    private int row;
    private int col;
    private int randPoint;
    private int coloring;
    private int point = 0;
    private int counter;

    public static ObservableList<Information> data;
    public static TableView<Information> tempTable;

    public static List<Information> list = new ArrayList<>();
    private Button PlayAction = new Button();
    public static Label stateSatus;
    public static boolean turn;
    public static int maxRow = 22;
    public static int maxCol = 19;
    public static int colorCell=0;
    public int totalCell;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalCell=maxRow*maxCol;
        stateSatus = state;
        createGrid();
        tempTable = table;
    }

    public void tableSettings() {

        label1Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label1")
        );
        label2Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label2")
        );
        label3Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label3")
        );
        label4Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label4")
        );
        label5Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label5")
        );
        label6Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label6")
        );
        label7Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label7")
        );
        label8Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label8")
        );

        label9Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label9")
        );

        label10Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label10")
        );

        label11Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label11")
        );

        label12Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label12")
        );

        label13Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label13")
        );

        label14Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label14")
        );

        label15Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label15")
        );

        label16Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label16")
        );

        label17Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label17")
        );

        label18Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label18")
        );

        label19Col.setCellValueFactory(
                new PropertyValueFactory<Information, ImageView>("label19")
        );
        label1Col.setResizable(false);
        label2Col.setResizable(false);
        label3Col.setResizable(false);
        label4Col.setResizable(false);
        label5Col.setResizable(false);
        label6Col.setResizable(false);
        label7Col.setResizable(false);
        label8Col.setResizable(false);
        label9Col.setResizable(false);
        label10Col.setResizable(false);
        label11Col.setResizable(false);
        label12Col.setResizable(false);
        label13Col.setResizable(false);
        label14Col.setResizable(false);
        label15Col.setResizable(false);
        label16Col.setResizable(false);
        label17Col.setResizable(false);
        label18Col.setResizable(false);
        label19Col.setResizable(false);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-cell-size: 20px;");

        data = FXCollections.observableArrayList(list); // create the data
        table.setItems(data);

        table.getSelectionModel().setCellSelectionEnabled(true);

        final ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
        selectedCells.addListener(new ListChangeListener<TablePosition>() {
            @Override
            public void onChanged(Change change) {
                for (TablePosition pos : selectedCells) {
                    System.out.println("Cell selected in row " + pos.getRow() + " and column " + pos.getColumn());
                    row = pos.getRow();
                    col = pos.getColumn();
                    trigger.fire();
                }
            }
        });

//        data = FXCollections.observableArrayList(); // create the data
//        table.setItems(data);
    }

    public void createGrid() {

        BufferedImage im = null;
        try {
            im = ImageIO.read(getClass().getClassLoader().getResource(Client.imagefile[PlayerInfo.playerId]));
            Image image1 = SwingFXUtils.toFXImage(im, null);
            playerColor.setImage(image1);

        } catch (IOException ex) {

        }

        trigger = new Button();

        trigger.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(" row = " + row + " col " + col);
                operation();
            }
        });

        for (int i = 0; i < 22; i++) {
            //ImageView[] image = new ImageView[18];
            Image[] img = new Image[20];
            for (int j = 0; j < 19; j++) {
                if (HomeController.playingGrid[i][j] == 0 || HomeController.playingGrid[i][j] == 10) {
                    img[j] = null;
                }
            }
            Information info = new Information(img);
            list.add(info);
        }

        tableSettings();

//        System.out.println(list.size());
    }

    void operation() {

        if (HomeController.playingGrid[row][col] == 0 && turn == true) {
            // HomeController.playingGrid[row][col]=PlayerInfo.playerId;

            Random rand = new Random();
            randPoint = rand.nextInt((5 - 1) + 1) + 1;
            counter = randPoint;

            System.out.println(randPoint);
            coloring = 0;
            dfs(row, col);
            point = point + ((coloring > 0) ? (coloring * 2 + randPoint) : 0);
            score.setText("" + point);
            System.out.println(coloring);
            updateGrid();
        } 
        else if (HomeController.playingGrid[row][col] == 10 && turn == true) {
            HomeController.playingGrid[row][col] = 9;                //  for coloring boom 
            point = point - 5;

            if (point < 0) {
                point = 0;
            }
            score.setText("" + point);
            updateGrid();

        }

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        playingScreen = screenPage;
    }

    void dfs(int r, int c) {

        System.out.println(" r " + r + " c " + c + " grid " + HomeController.playingGrid[r][c] + " point " + point);

        if ((HomeController.playingGrid[r][c] == 0 || HomeController.playingGrid[r - 1][c] == 10) && counter != 0) {
            HomeController.playingGrid[r][c] = PlayerInfo.playerId;
            counter--;
            coloring++;

            if (r - 1 >= 0 && counter > 0 && (HomeController.playingGrid[r - 1][c] == 0 || HomeController.playingGrid[r - 1][c] == 10)) {
                dfs(r - 1, c);
            }
            if (c - 1 >= 0 && counter > 0 && (HomeController.playingGrid[r][c - 1] == 0 || HomeController.playingGrid[r][c - 1] == 10)) {
                dfs(r, c - 1);
            }
            if (r + 1 < 21 && counter > 0 && (HomeController.playingGrid[r + 1][c] == 0 || HomeController.playingGrid[r + 1][c] == 10)) {
                dfs(r + 1, c);
            }
            if (c + 1 < 19 && counter > 0 && (HomeController.playingGrid[r][c + 1] == 0 || HomeController.playingGrid[r][c + 1] == 10)) {
                dfs(r, c + 1);
            }
        }
    }

    void updateGrid() {

        sendingUpdateInfo();

        List<Information> temp = new ArrayList<Information>();
        colorCell=0;
        for (int i = 0; i < maxRow; i++) {

            Image[] image = new Image[20];
            for (int j = 0; j < maxCol; j++) {
                try {
                    if (HomeController.playingGrid[i][j] == 0 || HomeController.playingGrid[i][j] == 10) {
                        image[j] = null;
                        colorCell++;
                    } else if (HomeController.playingGrid[i][j] == 9) {
                        BufferedImage im = ImageIO.read(getClass().getClassLoader().getResource("ressources/blackcolor.png"));
                        image[j] = SwingFXUtils.toFXImage(im, null);

                    } else {
                        BufferedImage im = ImageIO.read(getClass().getClassLoader().getResource(Client.imagefile[HomeController.playingGrid[i][j]]));
                        image[j] = SwingFXUtils.toFXImage(im, null);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PlayingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Information info = new Information(image);
            temp.add(info);
        }

        list = temp;
        data = FXCollections.observableArrayList(list); // create the data
        table.setItems(data);
        
        if(totalCell-colorCell==0){
            playTerminate();
        }
        
    }

    void sendingUpdateInfo() {

        Object[] send = new Object[425];

        send[0] = 4;
        send[1] = PlayerInfo.playerId;
        send[2]=point;
        int k = 3;
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                send[k++] = HomeController.playingGrid[i][j];
            }
        }

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                ConnectionSetupController.dout.writeObject(send);
                ConnectionSetupController.dout.flush();
                PlayingController.turn = false;
                PlayingController.stateSatus.setStyle("-fx-background-color: red;");

                return null;

            }

        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }
    
    public void playTerminate(){
        
       Object[] send=new Object[3];
       send[0]=5;                       //operation id=5 for show all player point 
       
       Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                ConnectionSetupController.dout.writeObject(send);
                ConnectionSetupController.dout.flush();
               
                return null;

            }

        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        
    }

}
