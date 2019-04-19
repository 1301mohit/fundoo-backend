package com.bridgelabz.fundoo.RabbitMq;

public interface MessageListener {

	public void onMessage(byte[] message) throws Exception;

}
