package gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import uml.pos.Position;

import java.util.List;

/**
 * GAssociation represents GUI Association
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GAssociation extends GRelationAbstractWithLabels {

    /**
     * Constructor for GAssociation. Allocate memory, set parent and child,
     * set text format
     * @param parent parent GClass
     * @param child child GClass
     */
    public GAssociation(Gclass parent, Gclass child, String name) {
        super(parent, child, name);
    }

}
