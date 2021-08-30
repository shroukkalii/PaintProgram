package paintprogram;

import java.awt.image.RenderedImage;
import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class PaintProgram extends Application {

    int HEIGHT = 500;
    int WIDTH = 500;

    enum flagtype {
        FreeDesign, Rect, Circle, Square, Rubber, USquare, URect, UCircle
    }
    flagtype flag = flagtype.FreeDesign;
    String color = "000000";

    @Override
    public void start(Stage stg) {
        ImageView imageview = new ImageView();

        Label lbl_name = new Label("File name:");
        Label lbl_psize = new Label("File size:");
        lbl_name.setFont(Font.font("Britannic Bold", 10));
        lbl_psize.setFont(Font.font("Britannic Bold", 10));
        lbl_name.setTextFill(Color.BLUE);
        lbl_psize.setTextFill(Color.BLUE);

        Button btn_open = new Button("Open Image");
        btn_open.setFont(Font.font("Britannic Bold", 20));
        btn_open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("PNG FILES(*.png)", "*.png");
                fc.getExtensionFilters().add(ext);
                File file = fc.showOpenDialog(stg);
                if (file != null) {
                    try {
                        lbl_name.setText("File Name:" + file.getName());
                        lbl_psize.setText("File Size:" + file.length() + " Bytes");
                        Image img = new Image(file.toURI().toString());

                        imageview.setImage(img);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });

        Button btn_opaint = new Button("Open Project");
        btn_opaint.setFont(Font.font("Britannic Bold", 20));
        btn_opaint.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("PAINT FILES(*.paint)", "*.paint");
                fc.getExtensionFilters().add(ext);
                File file = fc.showOpenDialog(stg);
                if (file != null) {
                    try {
                        lbl_name.setText("File Name:" + file.getName());
                        lbl_psize.setText("File Size:" + file.length() + " Bytes");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });

        Canvas board = new Canvas(WIDTH, HEIGHT);

        GraphicsContext gc = board.getGraphicsContext2D();

        /*    gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, board.getWidth(), board.getHeight());
         */
        gc.setLineWidth(10);
        gc.setStroke(Color.valueOf(color));

        Label currentcolor = new Label("Current color:");
        currentcolor.setFont(Font.font("Britannic Bold", 10));
        currentcolor.setTextFill(Color.BLUE);
        
        TextField hexacode = new TextField();
        hexacode.setMaxWidth(200);
        Button btn_changecolor = new Button("Change Color");
        btn_changecolor.setFont(Font.font("Britannic Bold", 15));
        btn_changecolor.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                try {

                    color = hexacode.getText();
                    gc.setStroke(Color.valueOf(color));
                    gc.setFill(Color.valueOf(color));
                } catch (IllegalArgumentException ex) {
                    System.out.println("INVALID COLOR, please enter 6 letters from 1-9 || A-F");
                }

            }
        });

        Label canvassize = new Label("Canvas size:");
        canvassize.setTextFill(Color.BLUE);
        canvassize.setFont(Font.font("Britannic Bold", 10));
        TextField width = new TextField();
        TextField height = new TextField();
        width.setMaxWidth(200);
        height.setMaxWidth(200);
        Button btn_csize = new Button("Change canvas size");
        btn_csize.setFont(Font.font("Britannic Bold", 15));
        btn_csize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board.setHeight(Double.parseDouble(height.getText()));
                board.setWidth(Double.parseDouble(width.getText()));

            }

        });

        Button btn_rect = new Button("Rectangle");
        btn_rect.setFont(Font.font("Britannic Bold", 15));
        Button btn_oval = new Button("Circle");
        btn_oval.setFont(Font.font("Britannic Bold", 15));
        Button btn_sq = new Button("Square");
        btn_sq.setFont(Font.font("Britannic Bold", 15));
        Button btn_free = new Button("Free Design");
        btn_free.setFont(Font.font("Britannic Bold", 15));
        Button btn_rub = new Button("Rubber");
        btn_rub.setFont(Font.font("Britannic Bold", 15));

        Button btn_urect = new Button("Stroke Rectangle");
        btn_urect.setFont(Font.font("Britannic Bold", 15));
        Button btn_usq = new Button("Stroke Square");
        btn_usq.setFont(Font.font("Britannic Bold", 15));
        Button btn_uoval = new Button("Stroke Circle");
        btn_uoval.setFont(Font.font("Britannic Bold", 15));

        btn_oval.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.Circle;
            }
        });
        btn_rect.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.Rect;
            }
        });
        btn_free.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.FreeDesign;
            }
        });
        btn_sq.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.Square;
            }
        });
        btn_rub.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.Rubber;
            }
        });

        btn_urect.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.URect;
            }
        });
        btn_usq.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.USquare;
            }
        });
        btn_uoval.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                flag = flagtype.UCircle;
            }
        });

        board.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                gc.beginPath();
            }
        });

        board.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (flag == flagtype.FreeDesign) {
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                }
                if (flag == flagtype.Rubber) {

                    gc.clearRect(event.getX(), event.getY(), 50, 50);
                }
            }
        });

        board.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (flag == flagtype.Circle) {
                    int diameter = 50;
                    gc.fillOval(event.getX() - diameter / 2, event.getY() - diameter / 2, diameter, diameter);

                }
                if (flag == flagtype.Square) {
                    gc.fillRect(event.getX(), event.getY(), 70, 70);
                    //     gc.strokeRect(event.getX(), event.getY(), 70, 70);
                }
                if (flag == flagtype.Rect) {
                    gc.fillRect(event.getX(), event.getY(), 100, 50);
                }

                if (flag == flagtype.UCircle) {
                    int diameter = 50;
                    gc.strokeOval(event.getX() - diameter / 2, event.getY() - diameter / 2, diameter, diameter);
                }
                if (flag == flagtype.USquare) {
                    gc.strokeRect(event.getX(), event.getY(), 70, 70);

                }
                if (flag == flagtype.URect) {

                    gc.strokeRect(event.getX(), event.getY(), 100, 50);

                }

            }
        });

        Button btn_clear = new Button("Clear");
        btn_clear.setFont(Font.font("Britannic Bold", 20));
        btn_clear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);
            }
        });

        Button btn_savepng = new Button("Save Image");
        btn_savepng.setFont(Font.font("Britannic Bold", 20));
        btn_savepng.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("PNG FILES(*.png)", "*.png");
                fc.getExtensionFilters().add(ext);
                File file = fc.showSaveDialog(stg);
                if (file != null) {
                    try {
                        WritableImage wi = new WritableImage(WIDTH, HEIGHT);
                        board.snapshot(null, wi);
                        RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
                        ImageIO.write(ri, "png", file);
                    } catch (Exception ex) {
                        System.out.println("Sorry, an error occurred while trying to save the image");
                    }
                }
            }
        });

        Button btn_savepaint = new Button("Save Project");
        btn_savepaint.setFont(Font.font("Britannic Bold", 20));
        btn_savepaint.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("PAINT FILES(*.paint)", "*.paint");
                fc.getExtensionFilters().add(ext);
                File file = fc.showSaveDialog(stg);
                if (file != null) {
                    try {
                        WritableImage wi = new WritableImage(WIDTH, HEIGHT);
                        board.snapshot(null, wi);
                        RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
                        ImageIO.write(ri, "paint", file);
                    } catch (Exception ex) {
                        System.out.println("Sorry, an error occurred while trying to save the image");
                    }
                }
            }
        });

        Label toolspanel = new Label("Tools Panel");
        toolspanel.setFont(Font.font("Bauhaus 93", 20));
        toolspanel.setTextFill(Color.RED);
        Label toolspanel2 = new Label(" ");

        Label colorspanel = new Label("Colors Panel");
        colorspanel.setFont(Font.font("Bauhaus 93", 20));
        colorspanel.setTextFill(Color.RED);

        Label filepanel = new Label("File Panel");
        filepanel.setFont(Font.font("Bauhaus 93", 20));
        filepanel.setTextFill(Color.RED);

        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        VBox vbox3 = new VBox();
        VBox vbox4 = new VBox();
        VBox vbox5 = new VBox();

        HBox hbox = new HBox();
        StackPane sp = new StackPane();

        vbox.getChildren().addAll(filepanel, btn_open, btn_opaint, btn_savepng, btn_savepaint);
        vbox2.getChildren().addAll(toolspanel, btn_free, btn_oval, btn_rect, btn_sq);
        vbox3.getChildren().addAll(toolspanel2, btn_uoval, btn_urect, btn_usq, btn_rub, btn_clear);
        vbox4.getChildren().addAll(colorspanel, currentcolor, hexacode, btn_changecolor, canvassize, width, height, btn_csize);
        vbox5.getChildren().addAll(sp, lbl_name, lbl_psize);

        sp.getChildren().addAll(imageview, board);

        hbox.getChildren().addAll(vbox, vbox2, vbox3, vbox4, vbox5);

        hbox.setSpacing(10);
        vbox.setSpacing(10);
        vbox2.setSpacing(10);
        vbox3.setSpacing(10);
        vbox4.setSpacing(10);
        vbox5.setSpacing(10);

        Scene scene = new Scene(hbox);

        stg.setTitle("Paint program");
        stg.setScene(scene);
        stg.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
