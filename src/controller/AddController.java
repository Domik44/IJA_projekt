package controller;

import gui.GActivationBox;
import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLInterface;
import uml.pos.Position;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.SequenceDiagram;
import uml.sequenceDiagram.UMLActivationBox;
import uml.sequenceDiagram.UMLMessage;
import uml.sequenceDiagram.UMLParticipant;
import workers.Converter;

public class AddController {
	
	public class AddInterface implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		public UMLInterface inconsistent;
		public UMLInterface newInterface;
		
		public AddInterface(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
			newInterface = this.model.createInterface(name);
			inconsistent = this.model.getInconsistent(name);
			if(inconsistent != null) {
				for(SequenceDiagram seq : this.model.getSequenceDiagrams()) {
					for(UMLParticipant par : seq.getParticipants()) {
						if(par.getInstanceOf() == inconsistent) {
							par.setInstanceOf(newInterface);
						}
					}
				}
			}
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			if(inconsistent != null) {
				for(SequenceDiagram seq : this.model.getSequenceDiagrams()) {
					for(UMLParticipant par : seq.getParticipants()) {
						if(par.getInstanceOf() == newInterface) {
							par.setInstanceOf(inconsistent);
						}
					}
				}
			}
			this.model.deleteInterface(name);
			this.view.setupFromDiagram(model);
		}
	}
	
	public class AddClass implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		public UMLInterface inconsistent;
		public UMLClass newClass;
		
		public AddClass(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
			newClass = this.model.createClass(name);
			inconsistent = this.model.getInconsistent(name);
			if(inconsistent != null) {
				for(SequenceDiagram seq : this.model.getSequenceDiagrams()) {
					for(UMLParticipant par : seq.getParticipants()) {
						System.out.println(par.getName());
						if(par.getInstanceOf() == inconsistent) {
							par.setInstanceOf(newClass);
						}
					}
				}
			}
			this.view.setupFromDiagram(model);
		}
		
		@Override
		public void undo() {
			if(inconsistent != null) {
				for(SequenceDiagram seq : this.model.getSequenceDiagrams()) {
					for(UMLParticipant par : seq.getParticipants()) {
						if(par.getInstanceOf() == newClass) {
							par.setInstanceOf(inconsistent);
						}
					}
				}
			}
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

			int x =(int)(view.positionList.get(0).getX() + view.selectedGclass1.getRoot().getTranslateX()
					+ view.positionList.get(view.positionList.size() - 1).getX() + view.selectedGclass1.getRoot().getTranslateX()) / 2;
			int y =(int)(view.positionList.get(0).getY() + view.selectedGclass1.getRoot().getTranslateY()
					+ view.positionList.get(view.positionList.size() - 1).getY() + view.selectedGclass1.getRoot().getTranslateY()) / 2;

			System.out.println(x);
			System.out.println(y);
			newAssociation.setLabelPosition(50,50);
			newAssociation.setLabelPosition(x,y);
			
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
		public SequenceDiagram seqModel;
		public ClassDiagram clsModel;
		public String name;
		public String instanceClassName;
		
		public AddParticipant(GUIMain view, SequenceDiagram seqModel, ClassDiagram clsModel, String[] information) {
			this.view = view;
			this.seqModel = seqModel;
			this.clsModel = clsModel;
			this.name = information[0];
			this.instanceClassName = information[1];
		}
		
		@Override
		public void run() {
//			name = Converter.converToCamelCase(name);
			instanceClassName = Converter.converToCamelCase(instanceClassName); // TODO -> mozna volat pred hledanim tridy?
			UMLInterface instanceClass = this.clsModel.getInterface(instanceClassName) == null ?  this.clsModel.getClass(instanceClassName) : this.clsModel.getInterface(instanceClassName);
			if(instanceClass == null) {
				 instanceClass = this.clsModel.getInconsistent(instanceClassName);
				 if(instanceClass == null) {
					 instanceClass = new UMLInterface(instanceClassName);
					 this.clsModel.addInconsistent(instanceClass);
					 instanceClass.setIsInconsistent(true);					 
				 }
			}
			
			this.seqModel.createParticipant(this.name, instanceClass);
			this.view.setupFromSEQDiagram(seqModel);
		}
		
		@Override
		public void undo() {
			this.clsModel.removeInconsistent(this.seqModel.getParticipant(name).getInstanceOf());
			this.seqModel.deleteParticipant(name);
			this.view.setupFromSEQDiagram(seqModel);
		}
	}
	
	public class AddMessage implements UIAction{
		
		public GUIMain view;
		public SequenceDiagram model;
		public String messageText;
		public String messageType;
		public Position lineStart;
		public String ID;
		public boolean isInconsistent;
		
		public AddMessage(GUIMain view, SequenceDiagram model, Position lineStart, String messageText, String messageType, boolean Inconsistent) {
			this.view = view;
			this.model = model;
			this.messageText = messageText;
			this.messageType = messageType;
			this.lineStart = lineStart;
			this.isInconsistent = Inconsistent;
		}
		
		@Override
		public void run() {
			UMLMessage newMessage = this.model.createMessage(this.view.selectedParticipant1.name,
					this.view.selectedParticipant2.name, messageText, messageType);
			
			if(this.model.getMessageBefore() && messageType.equals("Return")){
				newMessage.setIsInconsistent(true);
			}
			
			if(isInconsistent) {
				newMessage.setIsInconsistent(true);
				this.model.setMessageBefore(true);
			}
			else {
				this.model.setMessageBefore(false);
			}
			
			this.ID = newMessage.getID();
			
			if(lineStart.getY() < 50) {
				lineStart.setY(50);
			}
			
			newMessage.addPosition(lineStart);
			this.view.setupFromSEQDiagram(model);
		}
		
		@Override
		public void undo() {
			this.model.deleteMessage(ID);
			this.view.setupFromSEQDiagram(model);
		}
	}
	
	public class AddActivationBox implements UIAction{
		
		public GUIMain view;
		public SequenceDiagram model;
		public Position pos;
		
		public AddActivationBox(GUIMain view, SequenceDiagram model, Position pos) {
			this.view = view;
			this.model = model;
			this.pos = pos;
		}
		
		@Override
		public void run() {
			UMLActivationBox box = this.model.createActivationBox(this.view.selectedParticipant1.name);
//			GActivationBox Gbox = new 
		}
		
		@Override
		public void undo() {

		}
	}
}
