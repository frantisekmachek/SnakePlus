package Utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Loads images from files.
 */
public class DecalLoader {

    /**
     * Loads an image from a file.
     * @param fileName The file name
     * @return The loaded image
     */
    public static BufferedImage loadDecal(String fileName){
        File file = new File(fileName);
        BufferedImage image = null;
        if(file.exists()){
            try {
                image = ImageIO.read(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }
    


}
