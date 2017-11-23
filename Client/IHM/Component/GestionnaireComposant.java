package Client.IHM.Component;

import java.util.ArrayList;
import java.util.List;

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
	
	public void addComponent(Component c) {
		listComponent.add(c);
	}
	
	public void render() {
		for(Component c : listComponent)
			c.render();
	}
	
	public void update() {
		for(Component c : listComponent)
			c.update();
	}
	
	public void doAction() {
		for(Component c : listComponent)
			c.doAction(listener);
	}
	
	public void clear() {
		listComponent.clear();
	}
}
