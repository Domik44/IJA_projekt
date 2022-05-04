package controller;

import gui.ECW;
import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLInterface;

public class EditController {
	
	public class EditClass implements UIAction{
		public GUIMain view;
		public ClassDiagram model;
		public String name;
		public UMLClass originalClass;
		
		public EditClass(GUIMain view, ClassDiagram model, String name) {
			this.view = view;
			this.model = model;
			this.name = name;
		}
		
		@Override
		public void run() {
//			try {
//				this.originalClass = (UMLClass)this.model.getClass(name).clone();
//			} catch (CloneNotSupportedException e) {
//				e.printStackTrace();
//			}
			int GclassHeightdiff = 0;
			GclassHeightdiff = ECW.display(this.model.getClass(name));
//			this.model.createClass(name);
			if (GclassHeightdiff != 0)
                this.view.repairGclassBottomBorderRelations(this.view.selectedGclass, GclassHeightdiff);
			
			this.view.SetupFromDiagram(model);
			this.name = this.originalClass.getName();
		}
		
		@Override
		public void undo() {
			//TODO!!
			UMLClass restoreClass = this.model.getClass(name);
			this.view.SetupFromDiagram(model);
		}
	}
}
