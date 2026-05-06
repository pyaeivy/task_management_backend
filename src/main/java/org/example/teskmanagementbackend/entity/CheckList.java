package org.example.teskmanagementbackend.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String checkDescription;
	@ManyToOne
	private Task task;
	
}
