/*
 *Copyright 2011-2017 Fabien Michel
 * 
 * This file is part of MaDKit-tutorials.
 * 
 * MaDKit-tutorials is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MaDKit-tutorials is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MaDKit-tutorials. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package conversation.ex02;

import java.util.logging.Level;

import madkit.gui.AgentFrame;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Madkit;
import madkit.kernel.Message;

/**
 * Shows how agents exchange messages.
 * 
 * #jws conversation.ex03.sendReply2 jws#
 * 
 * To interact with other agents, agents need a form of communication. Doing so,the agents have MailBox, they can use it
 * to receive and send messages. Here, two agents are replying to the other one.
 */

public class Agent4 extends Agent {

    /**
     * Firstly, take a position in the artificial society
     */
    @Override
    protected void activate() {

	getLogger().setLevel(Level.INFO);

	createGroupIfAbsent("communication", "GroupTest");
	requestRole("communication", "GroupTest", "RoleTest1");
	pause(500);

    }

    /**
     * Now we send a message and we wait for a reply, after the reception we reply once again
     */
    @Override
    protected void live() {
	// Searching another Agent
	AgentAddress other = null;
	while (other == null) {
	    other = getAgentWithRole("communication", "GroupTest", "RoleTest1");
	    pause(1000);
	}

	getLogger().info("\n\t I found someone !! \n\t I send a message \n " + other + "\n\n");
	pause(1000);
	// Sending a message and wait for a reply
	Message receivedMessage = sendMessageAndWaitForReply(other, new Message());

	getLogger().info("\n\t He replied!! \n" + receivedMessage.getSender() + "\n\n ");
	// Replying
	Message repliedMessage = new Message();
	sendReply(receivedMessage, repliedMessage);

	pause(10000);
    }

    /**
     * @param argss
     *            It will run one Agent3 and one Agent4.
     */

    public static void main(String[] argss)// Launching two agents, Agent3 and Agent4.
    {
	new Madkit("--launchAgents", Agent3.class.getName() + ",true,1;", Agent4.class.getName() + ",true,1;");
    }

    /*
     * Setting where the agent's window will be for a clear presentation.
     */
    @Override
    public void setupFrame(AgentFrame frame) {
	super.setupFrame(frame);
	frame.setLocation(100, 350 * (hashCode() - 2));
    }

}
