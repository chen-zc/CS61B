package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Random;
import java.util.Scanner;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private TETile[][] world = new TETile[WIDTH][HEIGHT];
    private StringBuilder record = new StringBuilder();

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource source = new KeyboardInputSource();
        boolean gameOver = false;
        ter.initialize(WIDTH, HEIGHT);
        drawStart();
        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char action = source.getNextKey();
                takeAction(source, action);
            }
        }
    }

    private void takeAction(InputSource source, char c) {
        record.append(c);
        if (c == 'N') { // create new world
            long seed = findSeed(source);
            Random random = new Random(seed);
            WorldGenerator.createWorld(world, random);
            record.append(seed);
            record.append('S');
            if (source.getClass().equals(KeyboardInputSource.class)) {
                ter.renderFrame(world);
            }
        } else if (c == ':' && source.getNextKey() == 'Q') {
            record.deleteCharAt(record.length() - 1); // Remove ':' from record.
            save(record.toString()); // Save current world state to the file.
            if (source.getClass().equals(KeyboardInputSource.class)) {
                System.exit(0);
            }
        } else if (c == 'L') { //load the world
            record.deleteCharAt(record.length() - 1);
            String saved = load();
            if (saved.equals("")) {
                System.exit(0);
            }
            world = interactWithInputString(saved);
            if (source.getClass().equals(KeyboardInputSource.class)) {
                ter.renderFrame(world);
            }
        } else if (c == 'W') {
            int x = WorldGenerator.Ava.X;
            int y = WorldGenerator.Ava.Y + 1;
            if (world[x][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.AVATAR; // Update world.
                world[WorldGenerator.Ava.X][WorldGenerator.Ava.Y] = Tileset.FLOOR;
                WorldGenerator.Ava = new WorldGenerator.Position(x, y); // Update avatar's position.
                if (source.getClass().equals(KeyboardInputSource.class)) {
                    ter.renderFrame(world);
                }
            }
        } else if (c == 'S') {
            int x = WorldGenerator.Ava.X;
            int y = WorldGenerator.Ava.Y - 1;
            if (world[x][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.AVATAR; // Update world.
                world[WorldGenerator.Ava.X][WorldGenerator.Ava.Y] = Tileset.FLOOR;
                WorldGenerator.Ava = new WorldGenerator.Position(x, y); // Update avatar's position.
                if (source.getClass().equals(KeyboardInputSource.class)) {
                    ter.renderFrame(world);
                }
            }
        } else if (c == 'A') {
            int x = WorldGenerator.Ava.X - 1;
            int y = WorldGenerator.Ava.Y;
            if (world[x][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.AVATAR; // Update world.
                world[WorldGenerator.Ava.X][WorldGenerator.Ava.Y] = Tileset.FLOOR;
                WorldGenerator.Ava = new WorldGenerator.Position(x, y); // Update avatar's position.
                if (source.getClass().equals(KeyboardInputSource.class)) {
                    ter.renderFrame(world);
                }
            }
        } else if (c == 'D') {
            int x = WorldGenerator.Ava.X + 1;
            int y = WorldGenerator.Ava.Y;
            if (world[x][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.AVATAR; // Update world.
                world[WorldGenerator.Ava.X][WorldGenerator.Ava.Y] = Tileset.FLOOR;
                WorldGenerator.Ava = new WorldGenerator.Position(x, y); // Update avatar's position.
                if (source.getClass().equals(KeyboardInputSource.class)) {
                    ter.renderFrame(world);
                }
            }
        }
    }

    private String load() {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                Scanner in = new Scanner(new FileReader("save_data.txt"));
                StringBuilder sb = new StringBuilder();
                while(in.hasNext()) {
                    sb.append(in.next());
                }
                in.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private void save(String s) {
        File f = new File("./save_data.txt");
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileWriter writer = new FileWriter(f);
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private long findSeed(InputSource source) {
        if (source.getClass().equals(KeyboardInputSource.class)) {
            draw("");
        }
        StringBuilder seedString = new StringBuilder();
        long seed = 0;
        while (source.possibleNextInput()) {
            char next = source.getNextKey();
            if (next == 'S') {
                break;
            } else {
                seedString.append(next);
                seed = seed * 10 + Character.getNumericValue(next);
                if (source.getClass().equals(KeyboardInputSource.class)) {
                    draw(seedString.toString());
                }
            }
        }
        return seed;
    }

    private void draw(String s) {
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Please type a seed, press 'S' to confirm");
        StdDraw.text(WIDTH/2, HEIGHT/2, s);
        StdDraw.show();
    }

    private void drawStart() {
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT / 3 * 2, "press 'N' to start new game, press 'L' to load game");
        font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT / 3, "press 'WASD' to move, press ':Q' to save game");
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        StringInputDevice str = new StringInputDevice(input);
        while (str.possibleNextInput()) {
            char action = str.getNextKey();
            takeAction(str, action);
        }
        TETile[][] finalWorldFrame = world;
        return finalWorldFrame;
    }
}
