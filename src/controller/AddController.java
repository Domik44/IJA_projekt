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
	
	// TODO -> pozice?
	public class AddGeneralization implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String ID;
		public String parent;
		public String child;
		public String type;
		public Position pos;
		
		public AddGeneralization(GUIMain view, ClassDiagram model, String parent, String child, String type) {
			this.view = view;
			this.model = model;
			this.parent = parent;
			this.child = child;
			this.type = type;
		}
		
		@Override
		public void run() {
			RelGeneralization newGeneralization =  this.model.createGeneralization(this.parent, this.child, this.type);
			this.ID =  newGeneralization.getName();
			//TODO -> dodelat predani + nastaveni pozic, na kterych ma realce být!!
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteGeneralization(ID);
			this.view.setupFromDiagram(model);
		}
	}
	
	//TODO -> pozice, label, kardinalita, asoc.trida
		public class AddAggregation implements UIAction{
			public GUIMain view;
			public ClassDiagram model;
			public String ID;
			public String parent;
			public String child;
			public String type;
			public String label;
			public String leftCard;
			public String rightCard;
			public Position pos;
			
			public AddAggregation(GUIMain view, ClassDiagram model, String parent, String child, String type) {
				this.view = view;
				this.model = model;
				this.parent = parent;
				this.child = child;
				this.type = type;
			}
			
			@Override
			public void run() {
				RelAggregation newAggregation = this.model.createAggregation(this.parent, this.child, this.type);
				this.ID =  newAggregation.getName();
				// TODO -> predat pozice a nastavit ji, predat label a nastavit ho + jeho pozice, predat kardinality a nastavit je
				this.view.setupFromDiagram(model);
			}
			
			@Override
			public void undo() {
				this.model.deleteAggregation(ID);
				this.view.setupFromDiagram(model);
			}
		}
	
	//TODO -> pozice, label, kardinalita, asoc.trida
	public class AddAssociation implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String ID;
		public String parent;
		public String child;
		public String type;
		public String label;
		public String leftCard;
		public String rightCard;
		public Position pos;
		
		public AddAssociation(GUIMain view, ClassDiagram model, String parent, String child, String type) {
			this.view = view;
			this.model = model;
			this.parent = parent;
			this.child = child;
			this.type = type;
		}
		
		@Override
		public void run() {
			RelAssociation newAssociation = this.model.createAssociation(this.parent, this.child, this.type);
			this.ID =  newAssociation.getName();
			// TODO -> predat pozice a nastavit ji, predat label a nastavit ho + jeho pozice, predat kardinality a nastavit je + asoci trida ? (jestli ji vubec budeme implementovat)
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
