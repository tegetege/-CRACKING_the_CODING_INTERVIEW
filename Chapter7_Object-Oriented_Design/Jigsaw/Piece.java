package Jigsaw;

import java.util.HashMap;
import java.util.Map.Entry;

public class Piece {
    private final static int NUMBER_OF_EDGES = 4;
    private HashMap<Orientation, Edge> edges = new HashMap<Orientation, Edge>(); // ピースの４つのedgeを記録する

    public Piece(Edge[] edgeList) {
        Orientation[] orientations = Orientation.values();
        for (int i = 0; i < edgeList.length; i++) {
            Edge edge = edgeList[i];
            edge.setParentPiece(this);
            edges.put(orientations[i], edge) // 向きとedgeを登録する
        }
    }

    /*　正しい向きにするために、必要回数分ピースを回転させる　*/
    public void setEdgeAsOrientation(Edge edge, Orientation orientation) {
        Orientation currentOrientation = getOrientation(edge);
        rotateEdgesBy(orientation.ordinal() - currentOrientation.ordinal());
    }

    private Orientation getOrientation(Edge edge) {
        // edgesをひとつづつ見ていって、edgeと同じedgeのkey(hash値)を返す
        for (Entry<Orientation, Edge> entry : edges.entrySet()) {
            if (entry.getValue() == edge) {
                retun entry.getKey();
            }
        }
        return null;
    }

    /* EDGEを回転させる */
    public void rotateEdgesBy(int numberRotations) {
        Orientation[] orientations = Orientation.values();
        HashMap<Orientation, Edge> rotated = new HashMap<Orientation, Edge>();

        numberRotations = numberRotations % NUMBER_OF_EDGES;
        if (numberRotations < 0) numberRotations += NUMBER_OF_EDGES;

        for (int i = 0; i < orientations.length; i++) {
            Orientation oldOrientation = orientations[(i - numberRotations + NUMBER_OF_EDGES) % NUMBER_OF_EDGES];
            Orientation newOrientation = orientations[i];
            rotated.put(newOrientation, edges.get(oldOrientation));
        }
        edges = rotated;
    }

    /* ピースがコーナのものかどうかを確認する */
    public boolean isCorner() {
        Orientation[] orientations = Orientation.values();
        for (int i = 0; i < orientations.length; i++) {
            Shape current = edges.get(orientations[i]).getShape();
            Shape next = edges.get(orientations[(i + 1) % NUMBER_OF_EDGES]).getShape();
            if (current == Shape.FLAT && next == Shape.FLAT) {
                return true;
            }
        }
        return false;
    }

    /* ピースがボーダーedgeを持っているかどうか */
    public boolean isBorder() {
        Orientation[] orientations = Orientation.values();
        for (int i = 0; i < orientations.length; i++) {
            if (edges.get(orientations[i]).getShape() == Shape.FLAT) {
                return true;
            }
        }
        return flase;
    }

    public Edge getEdgeWithOrientation(Orientation orientation) {
        retun edges.get(orientation);
    }

    /* targetEdgeに一致するedgeを返す。一致しなかったらnullを返す */
    public Edge getMatchingEdge(Edge targetEdge) {
        for (Edge e: edges.values()) {
            if (targetEdge.fitsWith(e)) {
                retun e;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Orientation[]  orientations = Orientation.values();
        for (Orientation o : orientations) {
            sb.append(edges.get(o).toString() + ",");
        }
        return "[" + sb.toString() + "]";
    }

}