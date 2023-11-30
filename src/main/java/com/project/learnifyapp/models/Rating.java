package com.project.learnifyapp.models;

import java.io.Serializable;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Entity
@Builder
@Table(name = "ratings")
public class Rating extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Long id;

	@Column(name ="number_rating")
	private Integer numberRating;

	@Lob
	@Column(name ="description")
	private String description;

	@ManyToOne
	@JoinColumn(name ="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
}