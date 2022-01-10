import java.awt.Color;
import java.util.List;

public class ColorGroup {
    public Color color;
    public List<Coord> coords;

    public ColorGroup(Color color, List<Coord> coords) {
        this.color = color;
        this.coords = coords;
    }

    @Override
    public String toString() {
        return "ColorGroup{" +
                "color=" + color +
                ", coords=" + coords +
                '}';
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Coord> getCoords() {
        return coords;
    }

    public void setCoords(List<Coord> coords) {
        this.coords = coords;
    }

}
