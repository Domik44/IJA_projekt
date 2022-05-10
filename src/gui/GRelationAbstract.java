package gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import uml.classDiagram.UMLRelation;
import uml.pos.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * GRelationAbstract abstract class for all Class Diagram Relations
 *
 * @author  Adam Hos
 * @version 1.0
 */
 public class GRelationAbstract implements Observer {
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
            for (var mynode : list){
                mynode.addObserver((Observable o, Object arg) -> {
                    UMLRelation relation;
                    if (this instanceof GAssociation)
                        relation = GUIMain.diagram.getAssociation(name);
                    else if (this instanceof GAggregation)
                        relation = GUIMain.diagram.getAggregation(name);
                    else if (this instanceof GGeneralization)
                        relation = GUIMain.diagram.getGeneralization(name);
                    else
                        return;

                    List<Position> copy = relation.getPoints();
                    List<Position> tmpList = new ArrayList<>();
                    tmpList.add(copy.get(0));
                    for (var node : connection.between){
                        tmpList.add(convertMyNodeToPosition(node));
                    }
                    tmpList.add(copy.get(copy.size() - 1));
                    relation.changeList(tmpList);
                });

            }
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

    public void setColorToSelected(){
        connection.path.setStroke(Color.RED);
    }

    public void setColorToUNSelected(){
        connection.path.setStroke(Color.BLACK);
    }

    /**
     * Observer method
     * @param  o observable object
     * @param arg argument that was sent
     */
    @Override
    public void update(java.util.Observable o, Object arg) {
        System.out.println("Update called with Arguments: " + arg);
    }

    public Position convertMyNodeToPosition(MyNode myNode){
        return new Position( (int)myNode.g.getTranslateX(), (int)myNode.g.getTranslateY());
    }
}
