import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        File imageFile = new File("./src/main/resources/bauges.png");
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            //1.
            int k = 3;
            List<Color> colors = findRandomColorsInImage(bufferedImage, k);
            System.out.println(colors);

            //2.
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("yo");
    }

    private static List<Color> findRandomColorsInImage(BufferedImage bufferedImage, int k) {
        List<Color> colors = new ArrayList<>();
        List<Coord> randomCoord = createRandomCoord(k, bufferedImage.getHeight(), bufferedImage.getWidth());

        for (int i = 0; i < k; i++) {
            colors.add(new Color(bufferedImage.getRGB(randomCoord.get(i).getX(),randomCoord.get(i).getY())));
        }
        return colors;
    }

    private static List<Coord> createRandomCoord(int k, int height, int width) {
        List<Coord> res = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int x = (int) (Math.random() * width );
            int y = (int) (Math.random() * height);
            res.add(new Coord(x,y));
        }

        return res;
    }
}
