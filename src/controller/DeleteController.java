package controller;

import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.sequenceDiagram.SequenceDiagram;

public class DeleteController {
	
	// TODO -> po deleltu se z neznamych duvodu prestanou uchopovat vazby!!!
	public static void SetUpClassDiagram(GUIMain view, ClassDiagram model) {
		view.setupClasses(model.getClasses());
		view.setupAggregation(model.getAggregations());
		view.setupAssociation(model.getAssociations());
		view.setupGeneralization(model.getGeneralizations());
		view.setupInterfaces(model.getInterfaces());
	}
	
	public static void SetUpSequenceDiagram(GUIMain view, SequenceDiagram model) {
		// TODO
	}
	
	public static void DeleteInterface(String name, GUIMain view, ClassDiagram model) {
		view.clearView();
		model.deleteInterface(name);
		DeleteController.SetUpClassDiagram(view, model);
	}
	
	public static void DeleteClass(String name, GUIMain view, ClassDiagram model) {
		view.clearView();
		model.deleteClass(name);
		DeleteController.SetUpClassDiagram(view, model);
	}
	
	public static void DeleteGeneralization(String name, GUIMain view, ClassDiagram model) {
		view.clearView();
		model.deleteGeneralization(name);
		DeleteController.SetUpClassDiagram(view, model);
	}
	
	public static void DeleteAggregation(String name, GUIMain view, ClassDiagram model) {
		view.clearView();
		model.deleteAggregation(name);
		DeleteController.SetUpClassDiagram(view, model);
	}
	
	public static void DeleteAssociation(String name, GUIMain view, ClassDiagram model) {
		view.clearView();
		model.deleteAssociation(name);
		DeleteController.SetUpClassDiagram(view, model);
	}
	
	public static void DeleteParticipant(String name, GUIMain view, SequenceDiagram model) {
		view.clearView();
		model.deleteParticipant(name);
		DeleteController.SetUpSequenceDiagram(view, model);
	}
	
	public static void DeleteMessage(String ID, GUIMain view, SequenceDiagram model) {
		view.clearView();
		model.deleteMessage(ID);
		DeleteController.SetUpSequenceDiagram(view, model);
	}
	
	public static void DeleteActivationBox(String ID, GUIMain view, SequenceDiagram model) {
		view.clearView();
		model.deleteActivationBox(ID);
		DeleteController.SetUpSequenceDiagram(view, model);
	}
	
}
