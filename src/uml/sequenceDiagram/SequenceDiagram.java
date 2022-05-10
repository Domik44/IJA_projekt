package uml.sequenceDiagram;

import java.util.ArrayList;
import java.util.List;

import uml.Element;
import uml.classDiagram.UMLInterface;
import uml.classDiagram.UMLOperation;
import workers.Converter;

/**
 * This Class represents sequence diagram and its actions.
 *
 * @author Dominik Pop
 * @version 1.0
 * @since 2022-05-10
 */
public class SequenceDiagram extends Element {
	private List<UMLParticipant> participants = new ArrayList<UMLParticipant>();
	private List<UMLMessage> messages = new ArrayList<UMLMessage>();
	private List<UMLActivationBox> activationBoxes = new ArrayList<UMLActivationBox>();
	private boolean isSaved = true;
	private boolean messageBeforeInconsistent = false;
	
	/**
	 * Constructor for class representing sequence diagram.
	 * 
	 * @param name Contains name of diagram.
	 */
	public SequenceDiagram(String name) {
		super(name);
	}
	
	/**
	 * Method creates participant and adds it to participants list.
	 * 
	 * @param name Contains name of participant.
	 * @param instanceOf Contains class that participant is based on.
	 * @return Returns reference to newly created participant.
	 */
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
	
	/**
	 * Method adds participant to participants list.
	 * 
	 * @param par Participant to be added.
	 */
	public void addParticipant(UMLParticipant par) {
		this.participants.add(par);
	}
	
	/**
	 * Getter for participants list.
	 * 
	 * @return Returns reference to participants list.
	 */
	public List<UMLParticipant> getParticipants(){
		List<UMLParticipant> copy = List.copyOf(this.participants);
	
		return copy;
	}
	
	/**
	 * Getter for participant by its name.
	 * 
	 * @param name Contains name of participant.
	 * @return Returns reference to certain participant.
	 */
	public UMLParticipant getParticipant(String name) {
		for(UMLParticipant par : this.participants) {
			if(par.getName().equals(name)) {
				return par;
			}
		}
		
		return null;
	}
	
	/**
	 * Deletes participant by its name.
	 * 
	 * @param name Contains name of participant to be deleted.
	 */
	public void deleteParticipant(String name) {
		UMLParticipant toBeDeleted = this.getParticipant(name);
		if(toBeDeleted != null) {
			this.deleteMessagesWith(toBeDeleted);
			this.participants.remove(toBeDeleted);
		}
	}
	
	/**
	 * Deletes all participants with given instance interface/class.
	 * 
	 * @param instanceClass Interface/class determining if participant should be deleted.
	 */
	public void deleteParticipantsWith(UMLInterface instanceClass) {
		for(UMLParticipant par : this.participants) {
			if(par.getInstanceOf() == instanceClass) {
				this.deleteMessagesWith(par);
			}
		}
		
		this.participants.removeIf(par -> (par.getInstanceOf() == instanceClass));
	}
	
	/**
	 * Creates message and adds it to the messages list.
	 * 
	 * @param startParticipantName Contains name of participant where message begins.
	 * @param endParticipantName Contains name of participant where message ends.
	 * @param name Contains name of message.
	 * @param type Contains type of message.
	 * @return Returns reference to newly created message.
	 */
	public UMLMessage createMessage(String startParticipantName, String endParticipantName, String name, String type) {
//		name = Converter.converToCamelCase(name);
		UMLParticipant startParticipant = this.getParticipant(startParticipantName);
		UMLParticipant endParticipant = this.getParticipant(endParticipantName);
		UMLMessage newMessage = new UMLMessage(name, type);
		newMessage.setStartObject(startParticipant);
		newMessage.setEndObject(endParticipant);
		this.messages.add(newMessage);
		
		return newMessage;
	}
	
	/**
	 * Method used for creating message when reading from input file.
	 * 
	 * @param name Contains name of message.
	 * @param type Contains type of message.
	 * @return Returns reference to newly created message.
	 */
	public UMLMessage createMessage(String name, String type) {
//		name = Converter.converToCamelCase(name);
		UMLMessage newMessage = new UMLMessage(name, type);
		this.messages.add(newMessage);
		
		return newMessage;
	}
	
	/**
	 * Method adds message to message list.
	 * 
	 * @param added Message to be added.
	 */
	public void addMessage(UMLMessage added) {
		this.messages.add(added);
	}
	
	/**
	 * Method deletes message with given ID.
	 * 
	 * @param ID Contains ID of message to be deleted.
	 */
	public void deleteMessage(String ID) {
		this.messages.removeIf(mess -> (mess.getID().equals(ID)));
	}
	
	/**
	 * Method deletes all messages which are related to given participant.
	 * 
	 * @param participant Contains participant that decides if message should be deleted.
	 */
	public void deleteMessagesWith(UMLParticipant participant) {
		this.messages.removeIf(mess -> (mess.getStartObject().equals(participant) || mess.getEndObject().equals(participant)));
	}
	
	/**
	 * Gets list of all messages in sequence diagram.
	 * 
	 * @return Returns unmodifiable list of messages.
	 */
	public List<UMLMessage> getMessages(){
		List<UMLMessage> copy = List.copyOf(this.messages);
		
		return copy;
	}
	
	/**
	 * Gets message by its name.
	 * 
	 * @param name Contains name of message we want to get.
	 * @return Returns reference to wanted message.
	 */
	public UMLMessage getMessage(String ID) {
		for(UMLMessage mes : this.messages) {
			if(mes.getID().equals(ID)) {
				return mes;
			}
		}
		
		return null;
	}
	
	/**
	 * Creates activation box and sets its unique ID.
	 * 
	 * @return Returns reference to newly created activation box.
	 */
	public UMLActivationBox createActivationBox(String pacticipantName){
		UMLParticipant participant = this.getParticipant(pacticipantName);
		UMLActivationBox newBox = new UMLActivationBox();
		this.activationBoxes.add(newBox);
		participant.addBox(newBox);
		
		return newBox;
	}
	
	/**
	 * Method adds activation box to AB list.
	 * 
	 * @param added Activation box to be added.
	 */
	public void addActivationBox(UMLActivationBox added) {
		this.activationBoxes.add(added);
	}
	
	/**
	 * Deletes activation box that holds given ID.
	 * 
	 * @param ID Contains ID of box that is to be deleted.
	 */
	public void deleteActivationBox(String ID) {
		UMLActivationBox deletedBox = this.getActivationBox(ID);
		UMLParticipant par = deletedBox.getBelognsTo();
		par.deleteBox(ID);
		this.activationBoxes.removeIf(box -> (box.getID().equals(ID)));
	}
	
	/**
	 * Gets list of all activation boxes.
	 * 
	 * @return Returns reference to activation boxes list.
	 */
	public List<UMLActivationBox> getActivationBoxes(){
		List<UMLActivationBox> copy = List.copyOf(this.activationBoxes);
		
		return copy;
	}
	
	/**
	 * Gets activation box that has given ID.
	 * 
	 * @param ID Contains ID  of searched activation box.
	 * @return Returns reference to wanted activation box.
	 */
	public UMLActivationBox getActivationBox(String ID) {
		for(UMLActivationBox box : this.activationBoxes) {
			if(box.getID().equals(ID)) {
				return box;
			}
		}
		
		return null;
	}
	
	/**
	 * Sets boolean value of diagram attribute.
	 * 
	 * @param value Contains value determaning if diagram is (not) saved.
	 */
	public void setIsSaved(boolean value) {
		this.isSaved = value;
	}
	
	/**
	 * Getter for isSaved.
	 * 
	 * @return Returns if diagram is saved or not.
	 */
	public boolean getIsSaved() {
		return this.isSaved;
	}
	
	/**
	 * Gets all messages that can be used for given participant.
	 * 
	 * @param participantName Contains name of participant.
	 * @return Returns list of all available messages.
	 */
	public List<UMLOperation> getAvailableMessages(String participantName) {
		UMLParticipant paritcipant = this.getParticipant(participantName);
		UMLInterface InstanceClass = paritcipant.getInstanceOf();
		
		return InstanceClass.getAllMethods();
	}
	
	/**
	 * Gets all available participants that given participant can communicate with.
	 * 
	 * @param participantName Contains name of participant.
	 * @return Returns list of all available participant.
	 */
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
	
	/**
	 * Sets if message before this one was inconsistent.
	 * 
	 * @param value True if it was, false if it wasn't.
	 */
	public void setMessageBefore(boolean value) {
		this.messageBeforeInconsistent = value;
	}
	
	/**
	 * 
	 * @return Returns True if message before this one was inconsistent, false if not.
	 */
	public boolean getMessageBefore() {
		return this.messageBeforeInconsistent;
	}

}
