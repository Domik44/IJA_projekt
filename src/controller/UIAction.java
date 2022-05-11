package controller;

/**
 * Interface defining run/undo action for controllers.
 *
 * @author Dominik Pop
 * @version 1.0
 * @since 2022-05-10
 */
public interface UIAction {
	void run();
	void undo();
}
