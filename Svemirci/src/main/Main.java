package main;

import cameras.Camera;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sprites.Background;
import sprites.Enemy;
import sprites.Player;
import sprites.Shot;
import sprites.StarField;

public class Main extends Application {

    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;

    public static final int ENEMIES_IN_A_ROW = 6;
    public static final int ENEMIES_IN_A_COLUMN = 3;

    public static final int POINTS_PER_ENEMY = 2;
    public static final int POINTS_PER_COIN = 1;

    private Background background;
    private Player player;
    private List<Enemy> enemies;
    private List<Shot> shots;
    private long start;

    public Camera camera;

    private Group root;

    private StarField starField;
    private double time = 0;
    private boolean theEnd = false;

    private int score = 0;

    Text timeText = new Text("Test");
    Text scoreText = new Text("Score: 0");
    Text gameOverText = new Text();

    @Override
    public void start(Stage primaryStage) {
        enemies = new LinkedList<>();
        root = new Group();
        camera = new Camera();
        timeText.setFill(Color.RED);
        timeText.setTextAlignment(TextAlignment.CENTER);
        timeText.setTranslateX(WINDOW_WIDTH / 2);
        timeText.setTranslateY(WINDOW_HEIGHT / 11);
        timeText.setTextAlignment(TextAlignment.CENTER);
        timeText.setScaleX(2);
        timeText.setScaleY(2);

        scoreText.setFill(Color.RED);
        scoreText.setTextAlignment(TextAlignment.RIGHT);
        scoreText.setTranslateX(WINDOW_WIDTH - 60);
        scoreText.setTranslateY(20);
        scoreText.setTextAlignment(TextAlignment.CENTER);
        scoreText.setScaleX(1.2);
        scoreText.setScaleY(1.2);

        gameOverText.setFill(Color.RED);
        gameOverText.setTextAlignment(TextAlignment.RIGHT);
        gameOverText.setTranslateX(WINDOW_WIDTH / 2);
        gameOverText.setTranslateY(WINDOW_HEIGHT / 2);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setScaleX(3);
        gameOverText.setScaleY(3);
        

        background = new Background(WINDOW_WIDTH, WINDOW_HEIGHT);
        starField = new StarField();
        starField.setTranslateX(WINDOW_WIDTH / 2);
        starField.setTranslateY(WINDOW_HEIGHT / 2);
        root.getChildren().addAll(background, starField, timeText, scoreText,gameOverText);

        player = new Player(camera);
        player.setTranslateX(WINDOW_WIDTH / 2);
        player.setTranslateY(WINDOW_HEIGHT * 0.95);

        camera.getChildren().add(player);

        for (int i = 0; i < ENEMIES_IN_A_COLUMN; i++) {
            for (int j = 0; j < ENEMIES_IN_A_ROW; j++) {
                Enemy enemy = new Enemy(i + j);
                enemy.setTranslateX((j + 1) * WINDOW_WIDTH / (ENEMIES_IN_A_ROW + 1));
                enemy.setTranslateY((i + 1) * 100);
                camera.getChildren().add(enemy);
                enemies.add(enemy);
            }
        }

        root.getChildren().add(camera);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);

        primaryStage.setTitle("Svemirci");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        start = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (!theEnd) {
                    timeText.setText("Time: " + (currentNanoTime - start) / 1000000000);
                }
                update();
            }
        }.start();
    }

    public void update() {
        if (theEnd == false) {
            starField.update();
            shots = player.getShots();

            for (int i = 0; i < shots.size(); i++) {
                Shot currentShot = shots.get(i);

                if (currentShot.getTranslateY() < 50) {
                    shots.remove(currentShot);
                    continue;
                }

                for (int j = 0; j < enemies.size(); j++) {
                    Enemy currentEnemy = enemies.get(j);
                    if (currentShot.getBoundsInParent().intersects(currentEnemy.getBoundsInParent())) {
                        shots.remove(currentShot);
                        enemies.remove(currentEnemy);
                        score += POINTS_PER_ENEMY;
                        scoreText.setText("Score: " + score);
                        break;
                    }

                }
            }
            for (Enemy enemy : enemies) {

                if (enemy.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    theEnd = true;
                    break;
                }
            }

            camera.getChildren().clear();
            camera.getChildren().add(player);

            if (enemies.isEmpty()) {
                theEnd = true;
            } else {
                camera.getChildren().addAll(shots);
                shots.forEach(e -> e.update());
                camera.getChildren().addAll(enemies);
            }

            player.setShots(shots);
            player.update();

            time += 1.0 / 60;
        } else {
            gameOverText.setText("Game Over!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
