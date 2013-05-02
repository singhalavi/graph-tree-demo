package com.hs18.vaadin.addon;
 
import com.hs18.vaadin.addon.graph.GraphJSComponent;
import com.hs18.vaadin.addon.graph.GraphJSComponent.RECT_STYLE;
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
			graphJSComponent.addNode("fruits", "Fruits I Like", "level 1", "Example Tool Tip", null, null);//Give parent id as null for root node
			graphJSComponent.addNode("watermelon", "Watermelon", "level 2", "Its a very juicy fruit.", null, "fruits");//first child of node with id fruits
			graphJSComponent.addNode("mango", "Mango", "level 2", "Katrina Kaif's favourite.", null, "fruits");//second child of node with id fruits
			graphJSComponent.addNode("apple", "Apple", "level 2", null, null, "fruits");//third child of node with id fruits
			graphJSComponent.setNodeStyle("apple", RECT_STYLE.RED);
			graphJSComponent.setNodeLabel("apple", "Red Apple");//changing label from apple to red apple
			graphJSComponent.setNodeToolTip("apple", "One apple a day, keeps the doctor away");//Example, setting tooltip
			
			graphJSComponent.addNode("5", "Hapoos", "level 3", "One of the best mangos", null, "mango");//child of mango node
			graphJSComponent.addNode("6", "Green", "level 3", "Green from outside, red inside", null, "watermelon");//child of watermelon node
			graphJSComponent.setNodeStyle("6", RECT_STYLE.GREEN);
			
			//Another Tree in the same graph
			graphJSComponent.addNode("fruitsnotlike", "Fruits I Dont Like", "level 1", "Another tree in the same graph", null, null);//Give parent id as null
			graphJSComponent.addNode("lichy", "Lichy", "level 2", "because its nto easy to eat it.", null, "fruitsnotlike");//first child of node with id fruitsnotlike
			graphJSComponent.addNode("redlichy", "Red Lichy", "level 3", "red lichy", null, "lichy");
			
			graphJSComponent.refresh();//Call refresh after you are done with your changes
		} catch (Exception e) {
			e.printStackTrace();
		}//
	}
}
