package io.github.oliviercailloux.boardbusters.game.gui;

import io.github.oliviercailloux.boardbusters.main.FileOpener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static io.github.oliviercailloux.boardbusters.main.FileOpener.openImageFromSourceGame;

public class GameManagerFx extends Application {

    /**
     * The entry point for the JavaFX application.
     * Starts the game GUI by loading the initial FXML file and displaying the initial scene.
     *
     * @param stage the primary stage for this application, onto which the scene will be set
     * @throws IOException if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException{
        FileOpener.openFile();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ChooseNumberOfPlayers.fxml")));
        stage.setTitle("7 Wonders by BoardBusters");
        URL musicFile = getClass().getResource("menu_theme.mp3");
        MusicPlayer musicPlayer = MusicPlayer.getInstance();
        musicPlayer.setMusic(musicFile.toString());
        musicPlayer.playMusic();
        Image gameIcon = openImageFromSourceGame("7wonders_icon.jpg");
        stage.getIcons().add(gameIcon);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
