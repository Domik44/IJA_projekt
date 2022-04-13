package gui;

import javafx.scene.layout.Pane;

import java.util.List;

/**
 * GGeneralization represents GUI Generalization
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GGeneralization {
    Gclass parent;
    Gclass child;
    GConnection connection;

    /**
     * Constructor for Glass. Allocate memory
     * @param parent parent GClass
     * @param child child GClass
     */
    public GGeneralization(Gclass parent, Gclass child) {
        this.parent = parent;
        this.child = child;
        connection = new GConnection();
    }

    /**
     * Setup Generalization from nodes (when reading from file)
     * @param start parent Anchor
     * @param end   child Anchor
     * @param list  list of connecting MyNodes
     */
    public void setFromList(List<MyNode> list, MyNodeAnchor start, MyNodeAnchor end){
        connection.setStart(start);
        connection.setEnd(end);
        if (list.size() > 0){
            connection.setBetween(list);
        }
        connection.setBind();
        connection.connect();

        parent.addAnchor(connection.start);
        child.addAnchor(connection.end);
    }

    /**
     * Add to pane every graphic object
     * @param pane pane
     */
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
