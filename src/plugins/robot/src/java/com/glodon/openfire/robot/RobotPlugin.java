package com.glodon.openfire.robot;

import java.io.File;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.component.AbstractComponent;
import org.xmpp.component.ComponentManager;
import org.xmpp.component.ComponentManagerFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Message.Type;

public class RobotPlugin extends AbstractComponent implements Plugin {

	private static final Logger Log = LoggerFactory.getLogger(RobotPlugin.class);
	
	private ComponentManager componentManager;
	private PluginManager pluginManager;
	
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		pluginManager = manager;
		// Register as a component.
        componentManager = ComponentManagerFactory.getComponentManager();
        try {
            componentManager.addComponent("robot", this);
        }
        catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
		
	}

	@Override
	public void destroyPlugin() {
		// Unregister component.
        if (componentManager != null) {
            try {
                componentManager.removeComponent("robot");
            }
            catch (Exception e) {
                Log.error(e.getMessage(), e);
            }
        }
        componentManager = null;
        pluginManager = null;
	}

	@Override
	public String getDescription() {
		// Get the description from the plugin.xml file.
        return pluginManager.getDescription(this);
	}

	@Override
	public String getName() {
		// Get the name from the plugin.xml file.
        return pluginManager.getName(this);
	}
	
	 @Override
    protected void handleMessage(Message received) {
        // construct the response
        Message response = new Message();
        response.setFrom(received.getTo());
        response.setTo(received.getFrom());
        response.setType(Type.chat);
        response.setBody("Hello!");

        // send the response using AbstractComponent#send(Packet)
        send(response);
    }

}
