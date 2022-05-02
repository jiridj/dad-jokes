package com.softwareag.demo.dadjokes.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Joke {

	@Id 
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID", 
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String id;
	
	@Column(nullable = false, unique = true)
	private String content; 
	
	private boolean accepted;
	
	@CreationTimestamp
	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone = "UTC")
	private Date created;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone = "UTC")
	private Date updated;
	
}
