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
			
			this.deletedInterface.setIsInconsistent(true);
			this.model.deleteInterface(name);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.deletedInterface.setIsInconsistent(false);
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
			
			this.deletedClass.setIsInconsistent(true);
			this.model.deleteClass(name);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.deletedClass.setIsInconsistent(false);
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
		
		public GUIMain view;
		public SequenceDiagram seqModel;
		public ClassDiagram clsModel;
		List<UMLMessage> deletedMessages = new ArrayList<>();
		List<UMLActivationBox> deletedBoxes = new ArrayList<>();
		UMLParticipant deletedParticipant;
		String name;
		
		public DeleteParticipant(GUIMain view, SequenceDiagram seqModel, ClassDiagram clsModel, String name) {
			this.view = view;
			this.seqModel = seqModel;
			this.clsModel = clsModel;
			this.name = name;
		}
		
		@Override
		public void run() {
			this.deletedParticipant = this.seqModel.getParticipant(name);
			for(UMLMessage mes : this.seqModel.getMessages()) {
				if(mes.getEndObject() == this.deletedParticipant || mes.getStartObject() == this.deletedParticipant) {
					this.deletedMessages.add(mes);
				}
			}
			
			for(UMLActivationBox box : this.seqModel.getActivationBoxes()) {
				if(box.getBelognsTo() == this.deletedParticipant) {
					this.deletedBoxes.add(box);
				}
			}
			
			this.seqModel.deleteParticipant(this.name);
			this.view.delete.setDisable(true);
			this.view.setupFromSEQDiagram(seqModel);
		}
		
		@Override
		public void undo() {
			this.seqModel.addParticipant(this.deletedParticipant);
			for(UMLMessage mes : this.deletedMessages) {
				this.seqModel.addMessage(mes);
			}
			
			for(UMLActivationBox box : this.deletedBoxes) {
				this.seqModel.addActivationBox(box);
			}
			
			this.view.setupFromSEQDiagram(seqModel);
		}
	}
	
	public class DeleteMessage implements UIAction{
		
		//TODO -> view pro sekvencni diagram
		public GUIMain view;
		public SequenceDiagram seqModel;
		public ClassDiagram clsModel;
		UMLMessage deletedMessage;
		String ID;
		
		public DeleteMessage(GUIMain view, SequenceDiagram seqModel, ClassDiagram clsModel, String ID) {
			this.view = view;
			this.seqModel = seqModel;
			this.clsModel = clsModel;
			this.ID = ID;
		}
		
		@Override
		public void run() {
			this.deletedMessage = this.seqModel.getMessage(this.ID);
			this.seqModel.deleteMessage(this.ID);

			this.view.delete.setDisable(true);
			this.view.setupFromSEQDiagram(seqModel);
		}
		
		@Override
		public void undo() {
			this.seqModel.addMessage(deletedMessage);

			this.view.delete.setDisable(true);
			this.view.setupFromSEQDiagram(seqModel);
		}
	}
	
	public class DeleteActivationBox implements UIAction{
		
		//TODO -> view pro sekvencni diagram
		public GUIMain view;
		public SequenceDiagram seqModel;
		public ClassDiagram clsModel;
		String ID;
		UMLActivationBox deletedActivationBox;
		UMLParticipant deletedFrom;
		
		public DeleteActivationBox(GUIMain view, SequenceDiagram seqModel, ClassDiagram clsModel, String ID) {
			this.view = view;
			this.seqModel = seqModel;
			this.clsModel = clsModel;
			this.ID = ID;
		}
		
		@Override
		public void run() {
			this.deletedActivationBox = this.seqModel.getActivationBox(ID);
			this.deletedFrom = this.deletedActivationBox.getBelognsTo();
			this.seqModel.deleteActivationBox(this.ID);
			
			this.view.delete.setDisable(true);
			this.view.setupFromSEQDiagram(seqModel);
		}
		
		@Override
		public void undo() {
			this.seqModel.addActivationBox(deletedActivationBox);
			this.deletedFrom.addBox(deletedActivationBox);
			
			this.view.setupFromSEQDiagram(seqModel);
		}
	}
	
}
