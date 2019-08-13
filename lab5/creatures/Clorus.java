package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * An implementation of a new creature.
 *
 * @author Zhichao
 */

public class Clorus extends Creature{

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    public Color color() {
        g = 0;
        r = 34;
        b = 231;
        return color(r, g, b);
    }

    public void attack(Creature c) {
        this.energy += c.energy();
    }

    public void move() {
        this.energy -= 0.03;
    }

    public void stay() {
        this.energy -= 0.01;
    }

    public Clorus replicate() {
        Clorus replicate = new Clorus(this.energy/2);
        this.energy = this.energy/2;
        return replicate;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();
        boolean anyPlip = false;
        boolean isempty = false;
        for (Map.Entry<Direction, Occupant> entry: neighbors.entrySet()) {
            Direction key = entry.getKey();
            Occupant value = entry.getValue();
            if (value.name().equals("empty")) {
                isempty = true;
                emptyNeighbors.addLast(key);
            }
            if (value.name().equals("plip")) {
                anyPlip = true;
                plips.addLast(key);
            }
        }

        if (isempty == false) { // FIXME
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (anyPlip == true) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        }

        // Rule 3
        if (this.energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }

}
