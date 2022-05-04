package controller;

import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.pos.Position;
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
			this.view.SetupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteInterface(name);
			this.view.SetupFromDiagram(model);
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
			this.view.SetupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteClass(name);
			this.view.SetupFromDiagram(model);
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
			//TODO
			this.ID =  this.model.createGeneralization(this.parent, this.child, this.type).getName();
			this.view.SetupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteGeneralization(ID);
			this.view.SetupFromDiagram(model);
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
				//TODO
				this.ID =  this.model.createAggregation(this.parent, this.child, this.type).getName();
				this.view.SetupFromDiagram(model);
			}
			
			@Override
			public void undo() {
				this.model.deleteAggregation(ID);
				this.view.SetupFromDiagram(model);
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
			//TODO
			this.ID =  this.model.createAssociation(this.parent, this.child, this.type).getName();
			this.view.SetupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteAssociation(ID);
			this.view.SetupFromDiagram(model);
		}
	}
}
