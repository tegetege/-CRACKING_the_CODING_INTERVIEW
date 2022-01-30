package Jigsaw;

public class Edge {
    private Shape shape;
    private String code; // どのピースが合うのかをモックする
    private Piece parentPiece;

    public Edge(Shape shape, String code) {
        this.shape = shape;
        this.code = code;
    }

    private String getCode() {
        return code;
    }

    public Edge _createMatchingEdge() {
        if (shape == Shape.FLAT) return null;
        return new Edge(shape.getOpposite(), getCode());
    }

    /* 他のピースと角が合うかをチェックする*/
    public boolean fitsWith(Edge edge) {
        return edge.getCode().equals(getCode());
    }

    public void setParentPiece(Piece parentPiece) {
        this.parentPiece = parentPiece;
    }

    public Piece getParentPiece() {
        return parentPiece;
    }

    public Shape getShape() {
        return shape;
    }

    public String toString() {
        retun code;
    }

}