package uml.sequenceDiagram;

import java.util.ArrayList;
import java.util.List;

import uml.Element;
import uml.classDiagram.UMLInterface;
import uml.classDiagram.UMLOperation;
import workers.Converter;

public class SequenceDiagram extends Element {
	private List<UMLParticipant> participants = new ArrayList<UMLParticipant>();
	private List<UMLMessage> messages = new ArrayList<UMLMessage>();
	private List<UMLActivationBox> activationBoxes = new ArrayList<UMLActivationBox>();
	private boolean isSaved = true;
	
	
	public SequenceDiagram(String name) {
		super(name);
	}
	
	public UMLParticipant createParticipant(String name, UMLInterface instanceOf) {
		name = Converter.converToCamelCase(name);
		if(this.getParticipant(name) != null) {
			// TODO exception handling
			return null;
		}
		
		UMLParticipant newParticipant = new UMLParticipant(name);
		newParticipant.setInstanceOf(instanceOf);
		this.participants.add(newParticipant);
		
		return newParticipant;
	}
	
	public List<UMLParticipant> getParticipants(){
		List<UMLParticipant> copy = List.copyOf(this.participants);
	
		return copy;
	}
	
	public UMLParticipant getParticipant(String name) {
		for(UMLParticipant par : this.participants) {
			if(par.getName().equals(name)) {
				return par;
			}
		}
		
		return null;
	}
	
	public void deleteParticipant(String name) { //TODO -> odstraneni messagu
		UMLParticipant toBeDeleted = this.getParticipant(name);
		if(toBeDeleted != null) {
			this.deleteMessagesWith(toBeDeleted);
			this.participants.remove(toBeDeleted);
			toBeDeleted = null;
		}
	}
	
	public UMLMessage createMessage(String startParticipantName, String endParticipantName, String name, String type) { // TODO!!! -> pridat aby se tady nastavovali i participenti!!
//		name = Converter.converToCamelCase(name);
		UMLParticipant startParticipant = this.getParticipant(startParticipantName);
		UMLParticipant endParticipant = this.getParticipant(endParticipantName);
		UMLMessage newMessage = new UMLMessage(name, type);
		newMessage.setStartObject(startParticipant);
		newMessage.setEndObject(endParticipant);
		this.messages.add(newMessage);
		
		return newMessage;
	}
	
	public UMLMessage createMessage(String name, String type) { // TODO!!! -> pridat aby se tady nastavovali i participenti!!
//		name = Converter.converToCamelCase(name);
		UMLMessage newMessage = new UMLMessage(name, type);
		this.messages.add(newMessage);
		
		return newMessage;
	}
	
	public void deleteMessage(String ID) {
		this.messages.removeIf(mess -> (mess.getID().equals(ID)));
	}
	
	public void deleteMessagesWith(UMLParticipant participant) {
		this.messages.removeIf(mess -> (mess.getStartObject().equals(participant) || mess.getEndObject().equals(participant)));
	}
	
	public List<UMLMessage> getMessages(){
		List<UMLMessage> copy = List.copyOf(this.messages);
		
		return copy;
	}
	
	public UMLMessage getMessage(String name) {
		for(UMLMessage mes : this.messages) {
			if(mes.getName().equals(name)) {
				return mes;
			}
		}
		
		return null;
	}
	
	// Vytvoril se aktivacni box a pridal k participantovi
	// canCreate je false, takze dokud neukoncim tenhle nemuzu pridat dalsi 
	// ukoncit to muze message -> hrani si s pozici (konci na pozici message) -> nastaveni canCreate na true
	public UMLActivationBox createActivationBox(){
//		UMLParticipant participant = this.getParticipant(pacticipantName);
		UMLActivationBox newBox = new UMLActivationBox();
		this.activationBoxes.add(newBox);
//		participant.addBox(newBox);
//		participant.setCanCreateActivationBox(false);
		
		return newBox;
	}
	
	public void deleteActivationBox(String ID) {
		this.activationBoxes.removeIf(box -> (box.getID().equals(ID)));
	}
	
	public List<UMLActivationBox> getActivationBoxes(){
		List<UMLActivationBox> copy = List.copyOf(this.activationBoxes);
		
		return copy;
	}
	
	public UMLActivationBox getActivationBox(String ID) {
		for(UMLActivationBox box : this.activationBoxes) {
			if(box.getID().equals(ID)) {
				return box;
			}
		}
		
		return null;
	}
	
	public void setIsSaved(boolean value) {
		this.isSaved = value;
	}
	
	public boolean getIsSaved() {
		return this.isSaved;
	}
	
	public List<UMLOperation> getAvailableMessages(String participantName) {
		UMLParticipant paritcipant = this.getParticipant(participantName);
		UMLInterface InstanceClass = paritcipant.getInstanceOf();
		
		return InstanceClass.getAllMethods();
	}
	
	public List<UMLParticipant> getAvailableParticipents(String participantName){
		UMLParticipant givenParitcipant = this.getParticipant(participantName);
		UMLInterface InstanceClass = givenParitcipant.getInstanceOf();
		
		List<UMLInterface> communicatesWith = InstanceClass.getCommunications();
		List<UMLParticipant> returnList = new ArrayList<UMLParticipant>();
		for(UMLParticipant par : this.getParticipants()) {
			if(communicatesWith.contains(par.getInstanceOf())) {
				returnList.add(par);
			}
		}
		
		return returnList;
	}

}
