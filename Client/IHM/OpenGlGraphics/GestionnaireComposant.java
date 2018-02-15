package Client.IHM.OpenGlGraphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

public class GestionnaireComposant {
	private ComponentListener listener;
	private List<Component> listComponent;
	
	public GestionnaireComposant() {
		listComponent = new ArrayList<>();
	}
	
	public GestionnaireComposant(ComponentListener listener) {
		listComponent = new ArrayList<>();
		this.listener = listener;
	}
	
	public List<Component> getComponent() {
		return listComponent;
	}
	
	public void addComponent(Component c) {
		listComponent.add(c);
	}
	
	public void render() {
		/*for(Component c : listComponent)
			c.render();*/
		Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			Component c = componentIterator.next();
			c.render();
		}
	}
	
	public void update() {
		clearBufferText();
		/*for(Component c : listComponent)
			c.update();*/
		Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			Component c = componentIterator.next();
			c.update();
			if(c.autoSupression()) componentIterator.remove();
		}
	}
	
	public void doAction() {
		/*for(Component c : listComponent)
			c.doAction(listener);*/
		
		Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			Component c = componentIterator.next();
			c.doAction(listener);
		}
	}
	
	public void clear() {
		//listComponent.clear();
		listComponent = new ArrayList<>();
		Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			componentIterator.next();
			componentIterator.remove();
		}
	}
	
	public void clearBufferText() {
		for(Component c : listComponent) {
			if(c.isFocused()) return;
		}
		
		while (Keyboard.next()) {
			//on vide le buffer
		}
	}
}
