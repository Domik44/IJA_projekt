package gui;

import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

public class GConnection {
    MyNodeAnchor start;
    MyNodeAnchor end;
    List<MyNode> between;
    Path path;

    MoveTo pStart;
    List<LineTo> plines;

    public GConnection() {
        pStart = new MoveTo();
        between = new ArrayList<>();

        path = new Path();
        plines = new ArrayList<>();

    }

    public void setStart(MyNodeAnchor start) {
        this.start = start;
    }

    public void setEnd(MyNodeAnchor end) {
        this.end = end;
    }

    public void setBetween(List<MyNode> between) {
        this.between = between;
    }

    public void setBind( ){
        pStart.xProperty().bind(start.getXprop());
        pStart.yProperty().bind(start.getYprop());

        for (MyNode node: between){
            LineTo l = new LineTo();
            l.xProperty().bind(node.getXprop());
            l.yProperty().bind(node.getYprop());
            plines.add(l);
        }

        LineTo l = new LineTo();
        l.xProperty().bind(end.getXprop());
        l.yProperty().bind(end.getYprop());
        plines.add(l);
    }

    public void connect(){
        path.getElements().add(pStart);
        path.getElements().addAll(plines);
        }

}
