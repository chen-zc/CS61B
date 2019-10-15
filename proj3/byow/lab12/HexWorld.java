package byow.lab12;
        import org.junit.Test;
        import static org.junit.Assert.*;

        import byow.TileEngine.TERenderer;
        import byow.TileEngine.TETile;
        import byow.TileEngine.Tileset;


        import javax.swing.text.Position;
        import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2*s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /**
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        for (int y = 0; y < 2*s; y += 1) {
            int thisRowY = p.y + y;
            int xRowStart = p.x + hexRowOffset(s, y);
            Position RowStart = new Position(xRowStart, thisRowY);
            int rowWidth = hexRowWidth(s, y);

            for (int x = 0; x < rowWidth; x += 1) {
                int xi = RowStart.x + x;
                int yi = RowStart.y;
                world[xi][yi] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
            }
        }
    }*/
}
