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
            int k = 7;
            List<Color> colors = findRandomColorsInImage(bufferedImage, k);
            System.out.println(colors);

            //2.
            //Former les groupes
            GroupList groupList = getClosestColors(bufferedImage,colors);
//            System.out.println(groupList);
            //Faire une nouvelle liste de couleurs avec la moyenne dans chaque groupe
            List<Color> newColors = findMedianColors(bufferedImage,groupList, colors);
            System.out.println(newColors);

            for (int i = 0; i < groupList.getColorGroupList().size(); i++) {
                groupList.getColorGroupList().get(i).setColor(newColors.get(i));
            }

            //3.
            BufferedImage newBufferedImage = changeImageWithGroupList(bufferedImage, groupList);
            File newFile = new File("./out.png");
            ImageIO.write(newBufferedImage, "png", newFile);


            


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("yo");
    }

    private static BufferedImage changeImageWithGroupList(BufferedImage bufferedImage, GroupList groupList) {
        BufferedImage res = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < groupList.getColorGroupList().size(); i++) {
            List<Coord> coords = groupList.getColorGroupList().get(i).getCoords();
            for (int j = 0; j < coords.size(); j++) {
                res.setRGB(coords.get(j).getX(), coords.get(j).getY(),
                        groupList.getColorGroupList().get(i).getColor().getRGB());
            }
        }
        return res;
    }

    private static List<Color> findMedianColors(BufferedImage bufferedImage,GroupList groupList, List<Color> colors) {
        List<Color> res = new ArrayList<>();
        //pour chaque couleur
            //trouver la moyenne des couleurs sur l'image originalle à toutes les coords
            // ajouter la couleur à res

        for (Color color :
                colors) {
            res.add(findMedianColor(bufferedImage, groupList.getColorGroupFromColor(color), color));
        }
        return res;
    }

    private static Color findMedianColor(BufferedImage bufferedImage, ColorGroup colorGroupFromColor, Color color) {
        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        int size = colorGroupFromColor.getCoords().size();
        for (int i = 0; i < size; i++) {
            Coord coord = colorGroupFromColor.getCoords().get(i);
            Color currColor = new Color(bufferedImage.getRGB(coord.getX(), coord.getY()));
            totalR += currColor.getRed();
            totalG += currColor.getGreen();
            totalB += currColor.getBlue();
        }
        return new Color(totalR / size, totalG / size, totalB / size);
    }


    private static GroupList getClosestColors(BufferedImage bufferedImage, List<Color> colors) {

        GroupList res = new GroupList();

        //init colorgroups
        for (int i = 0; i < colors.size(); i++) {
            res.addGroup(new ColorGroup(colors.get(i), new ArrayList<Coord>()));
        }
//        float bestDistance = distanceBetween2Colors(new Color(bufferedImage.getRGB(0,0)), colors.get(0));

        System.out.println("height " + bufferedImage.getHeight());
        System.out.println("width " + bufferedImage.getWidth());

        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
//                System.out.println("i : " + i + " ; j : " + j);
                Color closestColor = closestColorForPixel(new Color(bufferedImage.getRGB(i,j)),colors);
                res.getColorGroupFromColor(closestColor).coords.add(new Coord(i,j));
            }
        }

        return res;
    }

    private static Color closestColorForPixel(Color color, List<Color> colors) {
        float bestDistance = distanceBetween2Colors(color, colors.get(0));
        int bestIndex = 0;
        for (int i = 1; i < colors.size(); i++) {
            float distance = distanceBetween2Colors(color, colors.get(i));
            if (distance < bestDistance){
                bestDistance = distance;
                bestIndex = i;
            }
        }
        return colors.get(bestIndex);
    }

    private static float distanceBetween2Colors(Color color1, Color color2) {
        return (color2.getRed() - color1.getRed()) * (color2.getRed() - color1.getRed())
                + (color2.getGreen() - color1.getGreen()) * (color2.getGreen() - color1.getGreen())
                + (color2.getBlue() - color1.getBlue()) * (color2.getBlue() - color1.getBlue());
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
