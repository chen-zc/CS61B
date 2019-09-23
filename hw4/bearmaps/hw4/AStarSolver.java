package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private DoubleMapPQ<Vertex> pq;
    private int total = 0;
    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, Vertex> edgeTo;

    //Constructor which finds the solution, computing everything necessary for all other
    // methods to return their results in constant time. Note that timeout passed in is in seconds.
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch time = new Stopwatch();
        pq = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new ArrayList<Vertex>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        edgeTo.put(start, start);
        while (pq.size() > 0 && !pq.getSmallest().equals(end)) {
            Vertex p = pq.removeSmallest();
            total += 1;
            solution.add(p);
            relax(p, input, end, pq);
            timeSpent = time.elapsedTime();
            if (timeSpent > timeout * 1000) {
                outcome = SolverOutcome.TIMEOUT;
            }
        }
        if (pq.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        }
        if (pq.getSmallest().equals(end)) {
            solution.add(pq.getSmallest());
            solutionWeight = distTo.get(pq.getSmallest());
            outcome = SolverOutcome.SOLVED;
        }
        timeSpent = time.elapsedTime();
    }

    private void relax(Vertex p, AStarGraph<Vertex> input, Vertex end, DoubleMapPQ<Vertex> pq) {
        List<WeightedEdge<Vertex>> neighbor = input.neighbors(p);
        for (WeightedEdge e: neighbor) {
            Vertex i = (Vertex) e.from();
            Vertex j = (Vertex) e.to();
            double w = e.weight();
            if (!distTo.containsKey(j) || distTo.get(i) + w < distTo.get(j)) {
                distTo.put(j, distTo.get(i) + w);
                edgeTo.put(j, i);
                if (pq.contains(j)) {
                    pq.changePriority(j, distTo.get(j) + input.estimatedDistanceToGoal(j, end));
                } else {
                    pq.add(j, distTo.get(j) + input.estimatedDistanceToGoal(j, end));
                }
            }
        }

    }

    //Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
    // Should be SOLVED if the AStarSolver was able to complete all work in the time given.
    // UNSOLVABLE if the priority queue became empty. TIMEOUT if the solver ran out of time.
    // You should check to see if you have run out of time every time you dequeue.
    public SolverOutcome outcome() {
        return outcome;
    }

    //A list of vertices corresponding to a solution. Should be empty if result was TIMEOUT or UNSOLVABLE.
    public List<Vertex> solution() {
        if (outcome().equals(SolverOutcome.SOLVED)) {
            return solution;
        }
        return null;
    }

    //The total weight of the given solution, taking into account edge weights.
    // Should be 0 if result was TIMEOUT or UNSOLVABLE.
    public double solutionWeight() {
        if (outcome().equals(SolverOutcome.SOLVED)) {
            return solutionWeight;
        }
        return 0;
    }

    //The total number of priority queue dequeue operations.
    public int numStatesExplored() {
        return total;
    }

    //The total time spent in seconds by the constructor.
    public double explorationTime() {
        return timeSpent;
    }
}
