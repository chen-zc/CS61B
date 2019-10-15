package byow.Core;


import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create a random world that meets the requirements below.
 * - The world must be a 2D grid, drawn using our tile engine.
 * - The world must be pseudorandomly generated.
 * - The generated world must include distinct rooms and hallways,
 *   though it may also include outdoor spaces.
 * - At least some rooms should be rectangular, though you may
 *   support other shapes as well.
 * - Your world generator must be capable of generating hallways
 *   that include turns (or equivalently, straight hallways that intersect).
 * - The world should contain a random number of rooms and hallways.
 * - The locations of the rooms and hallways should be random.
 * - The width and height of rooms should be random.
 * - The length of hallways should be random.
 * - Rooms and hallways must have walls that are visually distinct from floors.
 *   Walls and floors should be visually distinct from unused spaces.
 * - Rooms and hallways should be connected, i.e. there should not be gaps
 *   in the floor between adjacent rooms or hallways.
 * - The world should be substantially different each time, i.e. you should not
 *   have the same basic layout with easily predictable features
 */

public class WorldGenerator {

    public static class Position {
        int X, Y;

        public Position(int x, int y) {
            this.X = x;
            this.Y = y;
        }
    }

    public static class Room {
        int h, w;
        Position pos; //pos is left bottom position of the room
        public Room(int x, int y, Position p) {
            this.h = y;
            this.w = x;
            this.pos = p;
        }
    }

    public static void createWorld(TETile[][] world, Random random) {
        allWalls(world);
        List<Room> added_rooms = addRandomRooms(world, random);
        List<Room> list = added_rooms;
        connectRooms(world, list, random);
        cleanWalls(world);
        addAvatar(world, random);
    }

    private static void addAvatar(TETile[][] world, Random random) {
        int x = random.nextInt(world.length);
        int y = random.nextInt(world[0].length);
        while (! world[x][y].equals(Tileset.FLOOR)) {
            x = random.nextInt(world.length);
            y = random.nextInt(world[0].length);
        }
        Position Ava = new Position(x, y);
        world[x][y] = Tileset.AVATAR;
    }

    private static void cleanWalls(TETile[][] world) {
        int[][] neighbors = new int[][]{{0, 1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}};
        for (int i = 0; i < world.length; i ++) {
            for (int j = 0; j < world[0].length; j ++) {
                if (world[i][j].equals(Tileset.WALL)) {
                    int floorNum = 0;
                    for (int[] n: neighbors) {
                        int X = i + n[0];
                        int Y = j + n[1];
                        if (X >= 0 && X < world.length && Y >= 0 && Y < world[0].length) {
                            if (world[X][Y].equals(Tileset.FLOOR)) {
                                floorNum += 1;
                            }
                        }
                    }
                    if (floorNum == 0) {
                        world[i][j] = Tileset.NOTHING;
                    }
                }

            }
        }

    }

    private static void connectRooms(TETile[][] world, List<Room> list, Random random) {
        if (list.size() <= 1) {
            return;
        }
        int n = list.size();
        int first = random.nextInt(n);
        Room a = list.get(first);
        int next = random.nextInt(n);
        while (next == first) {
            next = random.nextInt(n);
        }
        Room b = list.get(next);
        creatHall(a, b, world);
        list.remove(first);
        connectRooms(world, list, random);
    }

    private static void creatHall(Room a, Room b, TETile[][] world) {
        int center_a_x = a.pos.X + a.w / 2;
        int center_a_y = a.pos.Y + a.h / 2;
        int center_b_x = b.pos.X + b.w / 2;
        int center_b_y = b.pos.Y + b.h / 2;

        int h_x = Math.min(center_a_x, center_b_x);
        int h_x_end = Math.max(center_a_x, center_b_x);
        int h_y = Math.min(center_a_y, center_b_y);
        for (int i = h_x; i < h_x_end; i ++) {
            world[i][h_y] = Tileset.FLOOR;
        }

        int v_y = Math.min(center_a_y, center_b_y);
        int v_y_end = Math.max(center_a_y, center_b_y);
        int v_x;
        if (center_a_y > center_b_y) {
            v_x = center_a_x;
        } else {
            v_x = center_b_x;
        }
        for (int j = v_y; j < v_y_end; j ++) {
            world[v_x][j] = Tileset.FLOOR;
        }
    }

    private static List<Room> addRandomRooms(TETile[][] world, Random random) {
        int height = world[0].length;
        int width = world.length;
        List<Room> rooms = new ArrayList<>();
        int num_of_rooms = 10 + random.nextInt(20); //between 10 to 30 rooms
        for (int i = 0; i < num_of_rooms; i ++) {
            int x = 2 + random.nextInt(width / 5);
            int y = 2 + random.nextInt(height / 5);
            int pos_x = random.nextInt(width - x - 1);
            int pos_y = random.nextInt(height - y - 1);
            Position p = new Position(pos_x, pos_y);
            Room temp = new Room(x, y, p);
            if (! checkRoomOverlap(temp, rooms)) {
                rooms.add(temp);
                creatRoom(world, temp);
            }
        }
        return rooms;
    }

    //paint tile with floors for the new room
    private static void creatRoom(TETile[][] world, Room room) {
        int x = room.w;
        int y = room.h;
        int start_x = room.pos.X;
        int start_y = room.pos.Y;
        for (int i = start_x; i < start_x + x ; i ++) {
            for (int j = start_y; j < start_y + y; j ++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    //check is the new room is overlapped with existed rooms
    private static boolean checkRoomOverlap(Room r, List<Room> rooms) {
        if (rooms.isEmpty()) {
            return false;
        }
        int right = r.pos.X + r.w;
        int left = r.pos.X;
        int top = r.pos.Y + r.h;
        int bottom = r.pos.Y;
        for (Room e : rooms) {
            if ((right <= e.pos.X + e.w && right >= e.pos.X) || (left >= e.pos.X && left <= e.pos.X + e.w) || (left <= e.pos.X && right >= e.pos.X + e.w)) {
                if ((top >= e.pos.Y + e.h && bottom <= e.pos.Y) || (top <= e.pos.Y + e.h && top >= e.pos.Y) || (bottom >= e.pos.Y && bottom <= e.pos.Y + e.h)) {
                    return true;
                }
            }
        }
        return false;
    }

    //paint all the world with wall as first step
    private static void allWalls(TETile[][] world) {
        int height = world[0].length;
        int width = world.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(60, 30);

        TETile[][] w = new TETile[60][30];
        createWorld(w, new Random());

        ter.renderFrame(w);
    }
}