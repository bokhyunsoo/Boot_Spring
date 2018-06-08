package net.Home.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer {
	
	@Id
	@GeneratedValue
	@JsonProperty
	private Long Id;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	@JsonProperty
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
	@JsonProperty
	private Question question;
	
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private LocalDateTime createDate;
	
	
	public Answer() {}

	public Answer(User writer, Question question, String contents) {
		this.writer = writer;
		this.question = question;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	public String getFormattedCreateDate() {
		
		if(createDate == null) {
			return "";
		}
		
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Answer [Id=" + Id + ", writer=" + writer + ", contents=" + contents + ", createDate=" + createDate
				+ "]";
	}

	public boolean isSameWriter(User sessionedUser) {
		if(!this.writer.equals(sessionedUser)) {
			return false;			
		}
		return this.writer.equals(sessionedUser);
	}
	
}
