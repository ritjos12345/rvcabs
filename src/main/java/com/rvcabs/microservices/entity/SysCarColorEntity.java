package com.rvcabs.microservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="syscarcolor")
public class SysCarColorEntity implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(unique = true, nullable = false)
		private Integer id;
		
		@Column
		private String name;
		
		@Column(name = "active", nullable = false, columnDefinition = "tinyint(1) default 1")
		private boolean active;

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public boolean isActive() {
			return active;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

	}


