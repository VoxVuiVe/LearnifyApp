package com.project.learnifyapp.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Entity
@Builder
@Table(name = "comments")
public class Comment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="comment", length = 150)
	private String comment;
	
	@Column(name ="create_date", nullable = false)
	private Date createDate;
	
	@Column(name ="quantity_like")
	private Integer quantity;

	@Column(name ="is_like")
	private Boolean isLike;

	@ManyToOne
	@JoinColumn(name ="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "lesson_id")
	private Lesson lesson;
}
