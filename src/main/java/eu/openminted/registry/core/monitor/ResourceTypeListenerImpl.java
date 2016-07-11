package eu.openminted.registry.core.monitor;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import eu.openminted.registry.core.domain.ResourceType;
import eu.openminted.registry.core.jms.JMSService;
import eu.openminted.registry.core.solr.service.SolrOperationsService;

public class ResourceTypeListenerImpl implements ResourceTypeListener {

	@Autowired
	SolrOperationsService solrOperationService;
	
	@Autowired
	JMSService jmsService;
	
	
	@Override
	public void resourceTypeAdded(ResourceType resourceType) throws IOException, URISyntaxException, SolrServerException, ParserConfigurationException, SAXException, TransformerException, InterruptedException {
		solrOperationService.createCore(resourceType);
		jmsService.createTopicFunction(resourceType.getName()+"-create");
		jmsService.createTopicFunction(resourceType.getName()+"-update");
		jmsService.createTopicFunction(resourceType.getName()+"-delete");
		jmsService.closeConnection();
	}

}
