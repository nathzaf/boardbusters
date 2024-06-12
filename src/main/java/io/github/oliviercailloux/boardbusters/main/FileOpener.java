package io.github.oliviercailloux.boardbusters.main;

import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.game.gui.GameManagerFx;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * The FileOpener class provides utility methods for opening files and resources.
 * It is used to open text files, images, and other resources used in the game.
 */
public class FileOpener {

    /**
     * Opens and reads "welcome.txt", a text file from the resources' folder.
     * The file content is printed to the standard output.
     *
     * @throws IOException if the file cannot be found or read
     */
    public static void openFile() throws IOException{
        URL fileURL = MainFx.class.getResource("welcome.txt");
        if(fileURL == null) throw new IOException();
        try(InputStream inputStream = fileURL.openStream()){
            while(true){
                int read = inputStream.read();
                if(read == -1) break;
                System.out.print((char) read);
            }
            System.out.println();
        }
    }

    /**
     * Opens an image file from the game resources' folder.
     *
     * @param fileName the name of the image file
     * @return the Image object representing the opened image file, or null if the file cannot be found or read
     */
    public static Image openImageFromSourceGame(String fileName){
        Image image = null;
        try(InputStream imageStream = GameManagerFx.class.getResourceAsStream(fileName)){
            if(imageStream == null) throw new IOException("Can't find image");
            image = new Image(imageStream);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return image;
    }

    /**
     * Opens an image file from the card resources folder.
     *
     * @param fileName the name of the image file
     * @return the Image object representing the opened image file, or null if the file cannot be found or read
     */
    public static Image openImageFromSourceCard(String fileName){
        Image image = null;
        try(InputStream imageStream = Card.class.getResourceAsStream(fileName)){
            if(imageStream == null) throw new IOException("Can't find image");
            image = new Image(imageStream);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return image;
    }

    /**
     * Opens an image file from the resources' folder.
     *
     * @param fileName the name of the image file
     * @return the Image object representing the opened image file, or null if the file cannot be found or read
     */
    public static Image openImageFromSourceResources(String fileName){
        Image image = null;
        try(InputStream imageStream = Resources.class.getResourceAsStream(fileName)){
            if(imageStream == null) throw new IOException("Can't find image");
            image = new Image(imageStream);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return image;
    }
}

