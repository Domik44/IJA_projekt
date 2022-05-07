package controller;

import java.util.ArrayList;
import java.util.List;

import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLInterface;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.SequenceDiagram;
import uml.sequenceDiagram.UMLActivationBox;
import uml.sequenceDiagram.UMLMessage;
import uml.sequenceDiagram.UMLParticipant;
import uml.relations.RelAggregation;

public class DeleteController {
	
	public class DeleteInterface implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		public UMLInterface deletedInterface;
		public List<RelGeneralization> deletedGeneralizations = new ArrayList<>();
		public List<RelAssociation> deletedAssociations = new ArrayList<>();
		public List<RelAggregation> deletedAggregations = new ArrayList<>();
		
		public DeleteInterface(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
			this.deletedInterface = this.model.getInterface(name);
			
			for(RelGeneralization rel : this.model.getGeneralizations()) {
				if(rel.getLeftClass() == this.deletedInterface || rel.getRightClass() == this.deletedInterface) {
					this.deletedGeneralizations.add(rel);
				}
			}
			
			for(RelAssociation rel : this.model.getAssociations()) {
				if(rel.getLeftClass() == deletedInterface || rel.getRightClass() == deletedInterface) {
					this.deletedAssociations.add(rel);
				}
			}
			
			for(RelAggregation rel : this.model.getAggregations()) {
				if(rel.getLeftClass() == deletedInterface || rel.getRightClass() == deletedInterface) {
					this.deletedAggregations.add(rel);
				}
			}
			
			this.model.deleteInterface(name);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.addInterface(deletedInterface);
			for(RelGeneralization rel : this.deletedGeneralizations) {
				this.model.addGeneralization(rel);				
			}
			
			for(RelAssociation rel : this.deletedAssociations) {
				this.model.addAssociation(rel);				
			}
			
			for(RelAggregation rel : this.deletedAggregations) {
				this.model.addAggregation(rel);				
			}
			this.view.setupFromDiagram(model);
			// TODO -> dodelat vraceni vztahu, participantu i messagu!!!
		}
	}
	
	public class DeleteClass implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		public UMLClass deletedClass;
		public List<RelGeneralization> deletedGeneralizations = new ArrayList<>();
		public List<RelAssociation> deletedAssociations = new ArrayList<>();
		public List<RelAggregation> deletedAggregations = new ArrayList<>();
		
		public DeleteClass(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
			this.deletedClass = this.model.getClass(name);
			for(RelGeneralization rel : this.model.getGeneralizations()) {
				if(rel.getLeftClass() == this.deletedClass || rel.getRightClass() == this.deletedClass) {
					this.deletedGeneralizations.add(rel);
				}
			}
			
			for(RelAssociation rel : this.model.getAssociations()) {
				if(rel.getLeftClass() == deletedClass || rel.getRightClass() == deletedClass) {
					this.deletedAssociations.add(rel);
				}
			}
			
			for(RelAggregation rel : this.model.getAggregations()) {
				if(rel.getLeftClass() == deletedClass || rel.getRightClass() == deletedClass) {
					this.deletedAggregations.add(rel);
				}
			}
			
			this.model.deleteClass(name);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.addClass(deletedClass);
			for(RelGeneralization rel : this.deletedGeneralizations) {
				this.model.addGeneralization(rel);				
			}
			
			for(RelAssociation rel : this.deletedAssociations) {
				this.model.addAssociation(rel);				
			}
			
			for(RelAggregation rel : this.deletedAggregations) {
				this.model.addAggregation(rel);				
			}
			this.view.setupFromDiagram(model);
			// TODO -> dodelat vraceni vztahu, participantu i messagu!!!
		}
	}
	
	public class DeleteGeneralization implements UIAction {
		public GUIMain view;
		public ClassDiagram model;
		public String ID;
		public RelGeneralization deletedGeneralization;
		
		public DeleteGeneralization(GUIMain view, ClassDiagram model, String ID) {
			this.view = view;
			this.model = model;
			this.ID = ID;
		}
		
		@Override
		public void run() {
			this.deletedGeneralization = this.model.getGeneralization(this.ID);
			this.model.deleteGeneralization(this.ID);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.addGeneralization(this.deletedGeneralization);
			this.view.setupFromDiagram(model);
		}
	}
	
	public class DeleteAssociation implements UIAction {
		public GUIMain view;
		public ClassDiagram model;
		public String ID;
		public RelAssociation deletedAssociation;
		
		public DeleteAssociation(GUIMain view, ClassDiagram model, String ID) {
			this.view = view;
			this.model = model;
			this.ID = ID;
		}
		
		@Override
		public void run() {
			this.deletedAssociation = this.model.getAssociation(this.ID);
			this.model.deleteAssociation(this.ID);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.addAssociation(this.deletedAssociation);
			this.view.setupFromDiagram(model);
		}
	}
	
	public class DeleteAggregation implements UIAction {
		public GUIMain view;
		public ClassDiagram model;
		public String ID;
		public RelAggregation deletedAggregation;
		
		public DeleteAggregation(GUIMain view, ClassDiagram model, String ID) {
			this.view = view;
			this.model = model;
			this.ID = ID;
		}
		
		@Override
		public void run() {
			this.deletedAggregation = this.model.getAggregation(this.ID);
			this.model.deleteAggregation(this.ID);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.addAggregation(this.deletedAggregation);
			this.view.setupFromDiagram(model);
		}
	}
	
	public class DeleteParticipant implements UIAction{
		
		GUIMain view;
		SequenceDiagram model;
		List<UMLMessage> deletedMessages = new ArrayList<>();
		List<UMLActivationBox> deletedBoxes = new ArrayList<>();
		UMLParticipant deletedParticipant;
		String name;
		
		public DeleteParticipant(GUIMain view ,SequenceDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
			this.deletedParticipant = this.model.getParticipant(name);
//			this.view
		}
		
		@Override
		public void undo() {
//			this.view
		}
	}
	
	public class DeleteMessage implements UIAction{
		
		//TODO -> view pro sekvencni diagram
		GUIMain view;
		SequenceDiagram model;
		UMLMessage deletedMessage;
		String ID;
		
		public DeleteMessage(GUIMain view, SequenceDiagram model, String ID) {
			this.view = view;
			this.model = model;
			this.ID = ID;
		}
		
		@Override
		public void run() {
			this.deletedMessage = this.model.getMessage(this.ID);
			this.model.deleteMessage(this.ID);
			// TODO -> setup from diagram pro sekvencni
//			this.view
		}
		
		@Override
		public void undo() {
			this.model.addMessage(deletedMessage);
			// TODO -> setup from diagram pro sekvencni
//			this.view
		}
	}
	
	public class DeleteActivationBox implements UIAction{
		
		//TODO -> view pro sekvencni diagram
		SequenceDiagram model;
		GUIMain view;
		String ID;
		UMLActivationBox deletedActivationBox;
		
		public DeleteActivationBox(SequenceDiagram model) {
			this.model = model;
		}
		
		@Override
		public void run() {
			this.deletedActivationBox = this.model.getActivationBox(ID);
			this.model.deleteActivationBox(this.ID);
			// TODO -> setup from diagram pro sekvencni
//			this.view.se
		}
		
		@Override
		public void undo() {
			this.model.addActivationBox(deletedActivationBox);
			// TODO -> setup from diagram pro sekvencni
//			this.view
		}
	}
	
}
