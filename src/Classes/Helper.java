package Classes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Helper {
    /**
     * ładuje zdjęcie z katalogu res/images
     */
    public static BufferedImage loadImage(String fileName) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(Helper.class.getResourceAsStream("/images/" + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return img;
    }
}
