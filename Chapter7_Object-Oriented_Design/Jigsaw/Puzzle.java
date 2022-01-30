package Jigsaw;

import java.util.LinkedList;

public class Puzzle {
    private LinkedList<Piece> pieces; /* Remaining pieces left to put away. */
    private Piece[][] solution;
    private int size;

    public Puzzle(int size, LinkedList<Piece> pieces) {
        this.pieces = pieces;
        this.size = size;
    }

    /* ピースをグルーピングする */
    public void groupPieces(LinkedList<Piece> cornerPieces, LinkedList<Piece> borderPieces, LinkedList<Piece> insidePieces) {
        for (Piece p : pieces) {
            if(p.isCorner()) {
                cornerPieces.add(p);
            } else if (p.isBorder()) {
                borderPieces.add(p);
            } else {
                insidePieces.add(p);
            }
        }
    }

    /* コーナーピースの向きを、flatなedgeが上と左になるようにする */
    public void orientTopLeftCorner(Piece piece) {
        if(!piece.isCorner()) return;

        Orientation[] orientations = Orientation.values();
        for(int i = 0; i < orientations.length; i++) {
            Edge current = piece.getEdgeWithOrientation(orientations[i]);
            Edge next = piece.getEdgeWithOrientation(orientations[(i + 1) % orientations.length]);
            if (current.getShape() == Shape.FLAT && next.getShape() == Shape.FLAT) {
                piece.setEdgeAsOrientation(current, Orientation.LEFT);
                return;
            }
        }
    }

    public boolean isBorderIndex(int location) {
        return location == 0 || location == size - 1;
    }

    /* ピースのリストから、ピースに一致するedgeがあるかどうかを確認する */
    private Edge getMatchingEdge(Edge targetEdge, LinkedList<Piece> pieces) {
        for (Piece piece : pieces) {
            Edge matchingEdge = piece.getMatchingEdge(targetEdge);
            if (matchingEdge != null) {
                return matchingEdge;
            }
        }
        return null;
    }

    private void setEdgeInSolution(LinkedList<Piece> pieces, Edge edge, int row, int column, Orientation orientation) {
        Piece piece = edge.getParentPiece();
        piece.setEdgeAsOrientation(edge, orientation);
        pieces.remove(piece);
        solution[row][column] = piece;
    }

    /* 探すべきピースのグループリストを返す */
    private LinkedList<Piece> getPieceListToSearch(LinkedList<Piece> cornerPieces, LinkedList<Piece> borderPieces, LinkedList<Piece> insidePieces, int row, int column) {
        if (isBorderIndex(row) && isBorderIndex(column)) {
            return cornerPieces;
        } else if (isBorderIndex(row) || isBorderIndex(column)) {
            return borderPieces;
        } else {
            return insidePieces;
        }
    }

    /* piecesToSearch で一致するピースを見つけて、rowとcolumnに挿入する */
    private boolean fitNextEdge(LinkedList<Piece> piecesToSearch, int row, int column) {
        if(row == 0 && column == 0) {
            // 左上のコーナーピースを発見!!
            Piece p = piecesToSearch.remove();
            orientTopLeftCorner(p);
            solution[0][0] = p;
        } else {
            /* 右側のedgeと一致するリストを取得する */
            Piece pieceToMatch = column == 0 ? solution[row - 1][0] : solution[row][column - 1]; // 一番左の場合を考慮
            Orientation orientationToMatch = column == 0 ? Orientation.BOTTOM : Orientation.RIGHT;
            Edge edgeToMatch = pieceToMatch.getEdgeWithOrientation(orientationToMatch);

            // 探すべきピースのグループと、マッチするedgeを渡して、探す
            Edge edge = getMatchingEdge(edgeToMatch, piecesToSearch);
            if(edge == null) return false; // マッチするedgeが無い場合

            Orientation orientation = orientationToMatch.getOpposite();
            setEdgeInSolution(piecesToSearch, edge, row, column, orientation);
        }
        return true;
    }

    // mainから呼び出されるメソッド
    public boolean solve() {
        /* Group pieces. */
        LinkedList<Piece> cornerPieces = new LinkedList<Piece>();
        LinkedList<Piece> borderPieces = new LinkedList<Piece>();
        LinkedList<Piece> insidePieces = new LinkedList<Piece>();
        groupPieces(cornerPieces, borderPieces, insidePieces);

        solution = new Piece[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                // 探すべきピースのグループを取得する
                LinkedList<Piece> piecesToSearch = getPieceListToSearch(cornerPieces, borderPieces, insidePieces, row, column);

                // マッチするピースが見つからない場合はfalseを返す
                if (!fitNextEdge(piecesToSearch, row, column)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Piece[][] getCurrentSolution() {
        return solution;
    }
}