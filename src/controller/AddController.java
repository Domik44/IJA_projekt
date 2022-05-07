package controller;

import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.pos.Position;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.SequenceDiagram;
import uml.sequenceDiagram.UMLParticipant;

public class AddController {
	
	public class AddInterface implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		
		public AddInterface(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
			this.model.createInterface(name);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteInterface(name);
			this.view.setupFromDiagram(model);
		}
	}
	
	public class AddClass implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		
		public AddClass(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
			this.model.createClass(name);
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteClass(name);
			this.view.setupFromDiagram(model);
		}
	}
	
	public class AddGeneralization implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String ID;
		
		public AddGeneralization(GUIMain view, ClassDiagram model) {
			this.view = view;
			this.model = model;
		}
		
		@Override
		public void run() {
			RelGeneralization newGeneralization =  this.model.createGeneralization(this.view.selectedGclass1.getName(), this.view.selectedGclass2.getName(), this.view.relationType);
			this.ID =  newGeneralization.getName();
			
			this.view.fixBorderPoints();
			for( var p : this.view.positionList) {
				newGeneralization.addPosition(p);
			}
			//TODO -> dodelat predani + nastaveni pozic, na kterych ma realce být!!
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteGeneralization(ID);
			this.view.setupFromDiagram(model);
		}
	}
	
		public class AddAggregation implements UIAction{
			public GUIMain view;
			public ClassDiagram model;
			public String ID;
			
			public AddAggregation(GUIMain view, ClassDiagram model) {
				this.view = view;
				this.model = model;
			}
			
			@Override
			public void run() {
				RelAggregation newAggregation = this.model.createAggregation(this.view.selectedGclass1.getName(), this.view.selectedGclass2.getName(), this.view.relationType);
				this.ID =  newAggregation.getName();
				
				this.view.fixBorderPoints();
				for(var p : this.view.positionList) {
					newAggregation.addPosition(p);
				}
				
				this.view.setupFromDiagram(model);
			}
			
			@Override
			public void undo() {
				this.model.deleteAggregation(ID);
				this.view.setupFromDiagram(model);
			}
		}
	
	public class AddAssociation implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String ID;
		
		public AddAssociation(GUIMain view, ClassDiagram model) {
			this.view = view;
			this.model = model;
		}
		
		@Override
		public void run() {
			RelAssociation newAssociation = this.model.createAssociation(this.view.selectedGclass1.getName(), this.view.selectedGclass2.getName(), this.view.relationType);
			this.ID =  newAssociation.getName();
			
			this.view.fixBorderPoints();
			for(var p : this.view.positionList) {
				newAssociation.addPosition(p);
			}
			
			newAssociation.setCardinality(this.view.LCardinality, this.view.RCardinality);
			newAssociation.setLabel(this.view.relationName);
	    	// TODO -> label pozice moc nefunguje
			newAssociation.setLabelPosition(50, 50);
			
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteAssociation(ID);
			this.view.setupFromDiagram(model);
		}
	}
	
	public class AddParticipant implements UIAction{
		
		public GUIMain view;
		public SequenceDiagram model;
		
		public AddParticipant(GUIMain view, SequenceDiagram model) {
			this.view = view;
			this.model = model;
		}
		
		@Override
		public void run() {

		}
		
		@Override
		public void undo() {

		}
	}
	
	public class AddMessage implements UIAction{
		
		public GUIMain view;
		public SequenceDiagram model;
		
		public AddMessage(GUIMain view, SequenceDiagram model) {
			this.view = view;
			this.model = model;
		}
		
		@Override
		public void run() {

		}
		
		@Override
		public void undo() {

		}
	}
	
	public class AddActionBox implements UIAction{
		
		public GUIMain view;
		public SequenceDiagram model;
		
		public AddActionBox(GUIMain view, SequenceDiagram model) {
			this.view = view;
			this.model = model;
		}
		
		@Override
		public void run() {

		}
		
		@Override
		public void undo() {

		}
	}
}
