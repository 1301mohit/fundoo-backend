package com.bridgelabz.fundoo.RabbitMq;

import java.io.IOException;
import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.elasticSearch.ElasticSearch;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.service.MessageService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageListenerImpl implements MessageListener{
	
	@Autowired
	private RabbitMqBody rabbitMqBody;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private SearchNoteBody searchNoteBody;
	
	@Autowired
	private ElasticSearch elasticSearch;

	@Override
	public void onMessage(byte[] message) throws Exception {
      
		System.out.println(new Date());
		try {
			Thread.sleep(5000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Message Received:"+message);
		String msg = new String(message);
		System.out.println("Message received:"+msg);
		ObjectMapper mapper = new ObjectMapper();
		rabbitMqBody = mapper.readValue(msg, RabbitMqBody.class);
		messageService.sendEmail(rabbitMqBody);
		System.out.println(new Date());
		
	}

//	@Override
//	public void onMessageForSearch(byte[] message) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
	
	@RabbitListener(queues = "note.queue.key")
	public void onMessageForSearch(SearchNoteBody body) throws Exception {
		System.out.println("Message listner for note");
		System.out.println("Body in message listner:"+body);
		System.out.println("Date in message listner"+new Date());
		Note note = body.getNote();
	//	Note note = searchNoteBody.getNote();
		SearchNoteOperation operation = searchNoteBody.getNoteOperation();
		switch(operation) {
			case CREATE:
				elasticSearch.createNote(note);
				break;
			case UPDATE:
				elasticSearch.deleteNote(note);
				break;
			case DELETE:
				elasticSearch.deleteNote(note);
				break;
		}
	}
	
}
