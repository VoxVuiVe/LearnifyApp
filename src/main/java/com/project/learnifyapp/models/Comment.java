package com.project.learnifyapp.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
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

	@Lob
	@Column(name ="comment", length = 150)
	private String comment;
	
	@Column(name ="create_date", nullable = false)
	private LocalDateTime createDate = LocalDateTime.now();
	
	@Column(name ="quantity_like")
	private Integer quantity;

	@Column(name ="is_like")
	private Boolean isLike;

	@ManyToOne
	@JoinColumn(name ="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
}
