package eu.openminted.registry.core.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSFunctions {

    private TopicConnection connection = null;
    private TopicSession session = null;
    private Topic topic = null;
	private InitialContext iniCtx;
    
	public JMSFunctions(){
		
	}
	
	public String createTopicFunction(String topicName){
		try {
	
			TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://83.212.121.189:61616");
			connection = connectionFactory.createTopicConnection();
			session = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
					
			connection.start();	
			Topic topic = session.createTopic(topicName);
			session.close();
			if (connection != null) {
				connection.close();
			}
			return "All good";
		} catch (JMSException e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
	
	public String publishMessage(String topicName, String message){
//		Properties props = new Properties();
//		props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
//		props.setProperty(Context.PROVIDER_URL,"tcp://83.212.121.189:61616");
		
		 try {
//			 InitialContext iniCtx = new InitialContext(props);
//			 Object tmp = iniCtx.lookup("ConnectionFactory");
//		     TopicConnectionFactory tcf = (TopicConnectionFactory) tmp;
//		     connection = tcf.createTopicConnection();
//		     topic = (Topic) iniCtx.lookup(topicName);
//		     session = connection.createTopicSession(false,TopicSession.AUTO_ACKNOWLEDGE);
//		     connection.start();
//		     
			 TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://83.212.121.189:61616");
			 connection = connectionFactory.createTopicConnection();
			 session = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
						
			 connection.start();	
			 Topic topic = session.createTopic(topicName);
			 
		     TopicPublisher send = session.createPublisher(topic);
		     TextMessage tm = session.createTextMessage(message);
		     send.publish(tm);
		     send.close();
		     return "All good";
		}  catch (JMSException e) {
			return "wtf2 "+ e.getMessage();
		}
		
	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
