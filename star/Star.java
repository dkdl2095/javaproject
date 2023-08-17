package project.star;

import java.awt.Color;

public class Star {
    public int radius;
    public String name;
    public Color color;

    public Star(int radius, String name, Color color) {
        this.radius = radius;
        this.name = name;
        this.color = color;
    }

    public String colorStr(Color color) {
        return Integer.toHexString(color.getRGB());
    }

    @Override
    public String toString() {
        return "행성이름:" + name + " 지름:" + Integer.toString(2 * radius) + " 색:" + colorStr(color);
    }
}
