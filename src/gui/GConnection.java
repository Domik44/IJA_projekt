package gui;

import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Gclass represents GUI relation between GClasses (or interfaces)
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GConnection {
    MyNodeAnchor start;
    MyNodeAnchor end;
    List<MyNode> between;
    Path path;

    MoveTo pStart;
    List<LineTo> plines;

    /**
     * Constructor for Glass. Allocate memory
     */
    public GConnection() {
        pStart = new MoveTo();
        between = new ArrayList<>();

        path = new Path();
        plines = new ArrayList<>();

    }

    /**
     * Setter start anchor
     * @param start MyNodeAnchor that is start
     */
    public void setStart(MyNodeAnchor start) {
        this.start = start;
    }

    /**
     * Setter end anchor
     * @param end MyNodeAnchor that is end
     */
    public void setEnd(MyNodeAnchor end) {
        this.end = end;
    }

    /**
     * Setter between list
     * @param between list of MyNodes
     */
    public void setBetween(List<MyNode> between) {
        this.between = between;
    }

    /**
     * Create and bind MoveTo,LineTo to coresponding MyNodes properties
     */
    public void setBind( ){
        pStart.xProperty().bind(start.getXprop());
        pStart.yProperty().bind(start.getYprop());

        if (between.size() != 0) {
            for (MyNode node : between) {
                LineTo l = new LineTo();
                l.xProperty().bind(node.getXprop());
                l.yProperty().bind(node.getYprop());
                plines.add(l);
            }
        }

        LineTo l = new LineTo();
        l.xProperty().bind(end.getXprop());
        l.yProperty().bind(end.getYprop());
        plines.add(l);
    }

    /**
     * Create path
     */
    public void connect(){
        path.getElements().add(pStart);
        path.getElements().addAll(plines);
        }

}
