package seng202.team5.map;

public class MarkerIcon {
    /**
     * A simple plane marker icon
     */
    public static final MarkerIcon PLANE_ICON = new MarkerIcon("plane_icon.png", 16, 16);

    /**
     * Image to use for the given icon
     */
    public final String imageURL;

    /**
     * X coordinate to centre the icon on (pixels)
     */
    public final double anchorX;
    /**
     * Y coordinate to centre the icon on (pixels)
     */
    public final double anchorY;

    public MarkerIcon(String imageURL, double anchorX, double anchorY) {
        this.imageURL = imageURL;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }
}
