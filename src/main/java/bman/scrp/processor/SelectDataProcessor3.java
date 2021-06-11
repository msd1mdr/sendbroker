package bman.scrp.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bman.scrp.persist.Broker;
import bman.scrp.persist.BrokerRepository;
import bman.scrp.persist.FileQueue;
import bman.scrp.persist.FileQueueRepository;
import bman.scrp.properties.KseiConfig;

@Component
public class SelectDataProcessor3 implements Processor {{}

	@Autowired
	private KseiConfig config;
	@Autowired
	private FileQueueRepository fileQueueRepo;
	@Autowired
	private BrokerRepository brokerRepository ;
	
	@Override
	public void process(Exchange exchange) throws Exception {
//	    final Logger log = LoggerFactory.getLogger(SelectDataProcessor3.class);

	   
		List<FileQueue> listFileQueue = fileQueueRepo.findByStatus("READY");
		List<FileQueue> filterListFiles = new ArrayList<>();

		for (FileQueue fileQueue : listFileQueue) {
			String eval = "";
			if (fileQueue.getSendMethod().equals("EMAIL")) eval = config.getSendemail() + "EMAIL";
			if (fileQueue.getSendMethod().equals("FTP")) eval = config.getSendftp() + "FTP";
			
			if ((eval.equals("yesEMAIL"))||(eval.equals("yesFTP"))) {

					Optional<Broker> optBroker = brokerRepository.findById(fileQueue.getRecipient());
					fileQueue.setRcptFtpHost(optBroker.get().getIpAddress());
					fileQueue.setFtpUsername(optBroker.get().getFtpUser());
					fileQueue.setFtpPassword(optBroker.get().getFtpPasswd());
					fileQueue.setRcptEmailAddress(optBroker.get().getEmailAddress());
					
					filterListFiles.add(fileQueue);
			} 				
		}

		exchange.getIn().setBody(filterListFiles);
		System.out.println("get list: " + filterListFiles.size());
	}

}