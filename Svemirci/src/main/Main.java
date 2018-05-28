package main;

import cameras.Camera;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprites.Background;
import sprites.Coin;
import sprites.Enemy;
import sprites.EnemyShot;
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

    public static final int ENEMY_FIRE_RATE = 2;

    private Background background;
    private Player player;
    private List<Enemy> enemies;
    private List<Shot> shots;
    private List<EnemyShot> enemyShots;
    private List<Coin> coins;

    private long start;
    private long nextFire;

    private Random random;

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
        random = new Random();
        coins = new ArrayList<>();
        root = new Group();
        camera = new Camera();
        enemies = new ArrayList<>();
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
        root.getChildren().addAll(background, starField);

        player = new Player(camera);
        player.setTranslateX(WINDOW_WIDTH / 2);
        player.setTranslateY(WINDOW_HEIGHT * 0.95);

        camera.getChildren().add(player);

        for (int i = 0; i < ENEMIES_IN_A_COLUMN; i++) {
            for (int j = 0; j < ENEMIES_IN_A_ROW; j++) {
                Enemy enemy = new Enemy(i + j, coins);
                enemy.setTranslateX((j + 1) * WINDOW_WIDTH / (ENEMIES_IN_A_ROW + 1));
                enemy.setTranslateY((i + 1) * 100);
                camera.getChildren().add(enemy);
                enemies.add(enemy);
            }
        }

        root.getChildren().addAll(camera, timeText, scoreText, gameOverText);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);

        primaryStage.setTitle("Svemirci");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        start = System.nanoTime();
        nextFire = start + (long) (random.nextDouble() * (ENEMY_FIRE_RATE + 1) * 1000000000);

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (!theEnd) {
                    if (currentNanoTime >= nextFire) {
                        RandomEnemyFire();
                        nextFire = currentNanoTime + (long) (random.nextDouble() * (ENEMY_FIRE_RATE + 1) * 1000000000);
                    }
                    timeText.setText("Time: " + (currentNanoTime - start) / 1000000000);
                    scoreText.setText("Score: " + score);
                }
                update();
            }

            private void RandomEnemyFire() {
                if (enemies.size() > 0) {
                    int enemyIndex = random.nextInt(enemies.size());
                    Enemy enemy = enemies.get(enemyIndex);
                    EnemyShot shot = new EnemyShot();
                    shot.setTranslateX(enemy.getTranslateX() + 30);
                    shot.setTranslateY(enemy.getTranslateY() + 15);
                    enemyShots.add(shot);
                }
            }
        }.start();
    }

    public void update() {
        if (theEnd == false) {
            starField.update();
            shots = player.getShots();
            enemyShots = Enemy.getShots();
            for (int i = 0; i < shots.size(); i++) {
                Shot currentShot = shots.get(i);

                if (currentShot.getTranslateY() < 50) {
                    shots.remove(currentShot);
                    continue;
                }

                for (int j = 0; j < enemies.size(); j++) {
                    Enemy currentEnemy = enemies.get(j);

                    if (currentEnemy.IsDead()) {
                        continue;
                    }
                    if (currentShot.getBoundsInParent().intersects(currentEnemy.getBoundsInParent())) {
                        shots.remove(currentShot);
                        currentEnemy.DeathAnimation(enemies);
                        score += POINTS_PER_ENEMY;
                        break;
                    }

                }
            }

            for (int i = 0; i < enemyShots.size(); i++) {
                EnemyShot currentShot = enemyShots.get(i);

                if (currentShot.getTranslateY() > WINDOW_HEIGHT) {
                    enemyShots.remove(currentShot);
                    continue;
                }

                if (currentShot.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    theEnd = true;
                    gameOverText.setText("Game Over!");
                    enemyShots.remove(currentShot);
                    player.DeathAnimation();
                    break;
                }
            }

            for (Enemy enemy : enemies) {
                if (enemy.IsDead()) {
                    continue;
                }
                if (enemy.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    theEnd = true;

                    gameOverText.setText("Game Over!");
                    player.DeathAnimation();
                    break;
                }
            }

            for (int i = 0; i < coins.size(); i++) {
                Coin coin = coins.get(i);
                if (!coin.IsVisible()) {
                    coins.remove(coin);
                }
                if (coin.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    score += POINTS_PER_COIN;
                    coins.remove(coin);
                    break;
                }
            }

            camera.getChildren().clear();

            if (!player.IsDead()) {
                camera.getChildren().add(player);
            }

            if (enemies.isEmpty() && coins.isEmpty() && shots.isEmpty() && enemyShots.isEmpty()) {
                theEnd = true;
                gameOverText.setText("You Won!");
            } else {
                shots.forEach(e -> e.update());
                coins.forEach(e -> e.update());
                enemyShots.forEach(e -> e.update());
                enemies.forEach(e -> e.updateDir());
                if (Enemy.changeDir) {
                    Enemy.changeDir = false;
                    Enemy.speed = -Enemy.speed;
                }
                enemies.forEach(e -> e.update());

                camera.getChildren().addAll(enemies);
                camera.getChildren().addAll(shots);
                camera.getChildren().addAll(coins);
                camera.getChildren().addAll(enemyShots);
            }

            player.setShots(shots);
            player.update();
            camera.update(player);

            time += 1.0 / 60;
        } else {

            if (player.IsDead()) {
                camera.getChildren().remove(player);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
