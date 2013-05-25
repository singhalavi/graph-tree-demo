package com.hs18.vaadin.addon;
 
import com.hs18.vaadin.addon.graph.GraphJSComponent;
import com.hs18.vaadin.addon.graph.listener.GraphJsLeftClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
	GraphJSComponent graphJSComponent;
    @Override
    protected void init(VaadinRequest request) {
    	
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
    	graphJSComponent = new GraphJSComponent();
		graphJSComponent.setLeftClickListener(new GraphJsLeftClickListener() {
			
			@Override
			public void onLeftClick(String id, String type, String parentId) {
				System.out.println(id + " "+ type + " "+ parentId);
				Notification.show("Clicked on node with id = " + id + " at " + type, Notification.Type.WARNING_MESSAGE); 
			}
		});
        graphJSComponent.setImmediate(true);
        
        String lhtml = "<div id='graph' class='graph' ></div>";//add style='overflow:scroll' if required
        Label graphLabel = new Label(lhtml, Label.CONTENT_XHTML);
        
        layout.addComponent(graphLabel);
        layout.addComponent(graphJSComponent);
        prepareGraph();
    }

	private void prepareGraph(){
		try {
			graphJSComponent.addNode("fruits", "Fruits I Like", "level 1", null, null);//Give parent id as null for root node
			graphJSComponent.getNodeProperties("fruits").put("title", "Fruits I Like"); 
			graphJSComponent.addNode("watermelon", "Watermelon", "level 2", null, "fruits");//first child of node with id fruits
			graphJSComponent.getNodeProperties("watermelon").put("title", "Its a very juicy fruit."); 
			graphJSComponent.addNode("mango", "Mango", "level 2", null, "fruits");//second child of node with id fruits
			graphJSComponent.getNodeProperties("mango").put("title", "Katrina Kaif's favourite."); 
			graphJSComponent.addNode("apple", "Apple", "level 2", null, "fruits");//third child of node with id fruits
			graphJSComponent.getNodeProperties("apple").put("title", "One apple a day, keeps the doctor away"); 
			graphJSComponent.getNodeProperties("apple").put("fill", "#F00");
			graphJSComponent.getNodeProperties("mango").put("fill", "yellow");
			
			graphJSComponent.addNode("5", "Hapoos", "level 3", null, "mango");//child of mango node
			graphJSComponent.getNodeProperties("5").put("title", "One of the best mangos"); 
			
			graphJSComponent.addNode("6", "Green", "level 3", null, "watermelon");//child of watermelon node
			graphJSComponent.getNodeProperties("6").put("title", "Green from outside, red inside"); 
			graphJSComponent.getNodeProperties("6").put("fill", "#0F0");
			
			//Another Tree in the same graph
			graphJSComponent.addNode("fruitsnotlike", "Fruits I Dont Like", "level 1",  null, null);//Give parent id as null
			graphJSComponent.getNodeProperties("fruitsnotlike").put("title", "Another tree in the same graph"); 
			graphJSComponent.addNode("lichy", "Lichy", "level 2", null, "fruitsnotlike");//first child of node with id fruitsnotlike
			graphJSComponent.getNodeProperties("lichy").put("title", "because its nto easy to eat it."); 
			graphJSComponent.addNode("redlichy", "Red Lichy", "level 3", null, "lichy");
			graphJSComponent.getNodeProperties("redlichy").put("title",  "red lichy"); 
			
			graphJSComponent.refresh();//Call refresh after you are done with your changes
		} catch (Exception e) {
			e.printStackTrace();
		}//
	}
}
