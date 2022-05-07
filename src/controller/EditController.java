package controller;

import gui.ECW;
import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLInterface;

public class EditController {
	
	public class EditInterface implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		public UMLInterface originalInterface;
		public int HeightDiff;
		
		public EditInterface(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
			this.HeightDiff = 0;
		}
		
		@Override
		public void run() {
			UMLInterface editedInterface = this.model.getInterface(name);
			this.originalInterface = new UMLClass(name);
			this.originalInterface.copy(editedInterface);
			int GclassHeightdiff = 0;
			GclassHeightdiff = ECW.display(editedInterface, this.model);
			
			if (GclassHeightdiff != 0)
                this.view.repairGclassBottomBorderRelations(this.view.selectedGclass1, GclassHeightdiff);
			
			this.view.setupFromDiagram(model);
			this.name = editedInterface.getName();
			this.HeightDiff = -GclassHeightdiff;
		}
		
		@Override
		public void undo() {
			//TODO!!
			UMLInterface restoreInterface = this.model.getInterface(name);
			restoreInterface.rename(this.originalInterface.getName());
			restoreInterface.clean();
			restoreInterface.copy(this.originalInterface);
			
			if (this.HeightDiff != 0)
                this.view.repairGclassBottomBorderRelations(this.view.selectedGclass1, this.HeightDiff);
			
			this.view.setupFromDiagram(model);
		}
	}
	
	public class EditClass implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		public UMLClass originalClass;
		public int HeightDiff;
		
		public EditClass(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
			this.HeightDiff = 0;
		}
		
		@Override
		public void run() {
			UMLClass editedClass = this.model.getClass(name);
			this.originalClass = new UMLClass(name);
			this.originalClass.copy(editedClass);
			int GclassHeightdiff = 0;
			GclassHeightdiff = ECW.display(editedClass, this.model);
			
			if (GclassHeightdiff != 0)
                this.view.repairGclassBottomBorderRelations(this.view.selectedGclass1, GclassHeightdiff);
			
			this.view.setupFromDiagram(model);
			this.name = editedClass.getName();
			this.HeightDiff = -GclassHeightdiff;
		}
		
		@Override
		public void undo() {
			//TODO!!
			UMLClass restoreClass = this.model.getClass(name);
			restoreClass.rename(this.originalClass.getName());
			restoreClass.clean();
			restoreClass.copy(this.originalClass);
			
			if (this.HeightDiff != 0)
                this.view.repairGclassBottomBorderRelations(this.view.selectedGclass1, this.HeightDiff);
			
			this.view.setupFromDiagram(model);
		}
	}
	
	
}
