package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * GRelationAbstract abstract class for all Class Diagram Relations
 *
 * @author  Adam Hos
 * @version 1.0
 */
 public class GRelationAbstract {
    String name;
    Gclass parent;
    Gclass child;
    GConnection connection;

    /**
     * Constructor, Allocate memory
     * @param parent parent GClass
     * @param child child GClass
     */
    public GRelationAbstract(Gclass parent, Gclass child,String name) {
        this.name = name;
        this.parent = parent;
        this.child = child;
        connection = new GConnection();
    }

    /**
     * Setup relation from nodes (when reading from file)
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
     * Add every graphic object to pane
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
