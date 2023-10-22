package com.project.shopapp.models;

import java.util.Date;

import javax.swing.text.AbstractDocument.Content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Entity
@Table(name = "favourite")
public class Favourite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="favourite_id")
	private Long favouriteId;
	
	@Column(name ="isActive")
	private Boolean isActive ;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
}