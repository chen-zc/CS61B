package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import edu.princeton.cs.algs4.TrieST;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Zhichao
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Point> points;
    private Map<Point, Node> pointToNode;
    private TrieST trie;
    private Map<String, List<Node>> nameToNodeList;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        pointToNode = new HashMap<>();
        points = new ArrayList<>();
        trie = new TrieST();
        nameToNodeList = new HashMap<>();
        for (Node n: nodes) {
            if (neighbors(n.id()).size() > 0) {
                Point temp = new Point(n.lon(), n.lat());
                pointToNode.put(temp, n);
                points.add(temp);
            }

            if (n.name() != null) {
                String cleanName = cleanString(n.name());
                trie.put(cleanName, null);
                if (! nameToNodeList.containsKey(cleanName)) {
                    nameToNodeList.put(cleanName, new ArrayList<>());
                }
                nameToNodeList.get(cleanName).add(n);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        WeirdPointSet set = new WeirdPointSet(points);
        Point target = set.nearest(lon, lat);
        long id = pointToNode.get(target).id();
        return id;
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        for (Object s: trie.keysWithPrefix(cleanString(prefix))) {
            for (Node n : nameToNodeList.get(s)) {
                if (! result.contains(n.name())) {
                    result.add(n.name());
                }
            }

        }
        return result;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> result = new LinkedList<>();
        if (nameToNodeList.containsKey(cleanString(locationName))) {
            for (Node n: nameToNodeList.get(cleanString(locationName))) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("lat", n.lat());
                temp.put("lon", n.lon());
                temp.put("name", n.name());
                temp.put("id", n.id());
                result.add(temp);
            }
        }
        return result;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
