package com.bridgelabz.fundoo.RabbitMq;

import org.springframework.stereotype.Component;

@Component
public class RabbitMqBody {

	private String toEmailId;
	private String url;
	private String subject;
	
	
	public String getToEmailId() {
		return toEmailId;
	}
	public void setToEmailId(String toEmailId) {
		this.toEmailId = toEmailId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public RabbitMqBody() {
		super();
	}
	
	public RabbitMqBody(String fromEmailId, String toEmailId, String url, String subject) {
		super();
		this.toEmailId = toEmailId;
		this.url = url;
		this.subject = subject;
	}
	
	
	@Override
	public String toString() {
		return "RabbitMqBody [toEmailId=" + toEmailId + ", url=" + url + ", subject=" + subject + "]";
	}
	
}
