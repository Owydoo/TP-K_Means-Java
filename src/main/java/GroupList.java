import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroupList {
    public List<ColorGroup> colorGroupList;

    public GroupList() {
        this.colorGroupList = new ArrayList<ColorGroup>();
    }

    public GroupList(List<ColorGroup> colorGroupList) {
        this.colorGroupList = colorGroupList;
    }

    public List<ColorGroup> getColorGroupList() {
        return colorGroupList;
    }

    public void setColorGroupList(List<ColorGroup> colorGroupList) {
        this.colorGroupList = colorGroupList;
    }

    @Override
    public String toString() {
        return "GroupList{" +
                "colorGroupList=" + colorGroupList +
                '}';
    }

    public ColorGroup getColorGroupFromColor(Color color){
        for (int i = 0; i < colorGroupList.size(); i++) {
            if (colorGroupList.get(i).getColor() == color){
                return colorGroupList.get(i);
            }
        }
        return null;
    }

    public void addGroup(ColorGroup colorGroup){
        colorGroupList.add(colorGroup);
    }
}
