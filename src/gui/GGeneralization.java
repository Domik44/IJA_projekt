package gui;

import javafx.scene.layout.Pane;

import java.util.List;

public class GGeneralization {
    Gclass parent;
    Gclass child;
    GConnection connection;
    public GGeneralization(Gclass parent, Gclass child) {
        this.parent = parent;
        this.child = child;
        connection = new GConnection();
    }

    public void setFromList(List<MyNode> list, MyNodeAnchor start, MyNodeAnchor end){
        connection.setStart(start);
        connection.setEnd(end);
        if (list.size() > 2){
            connection.setBetween(list.subList(1, list.size() - 1));
        }
        connection.setBind();
        connection.connect();

        parent.addAnchor(connection.start);
        child.addAnchor(connection.end);
    }

    public void show(Pane pane){
        pane.getChildren().add(connection.path);
        pane.getChildren().add(connection.start.g);
        pane.getChildren().add(connection.end.g);
        for (MyNode node: connection.between) {
            pane.getChildren().add(node.g);
            node.g.toFront();
        }
    }
}
