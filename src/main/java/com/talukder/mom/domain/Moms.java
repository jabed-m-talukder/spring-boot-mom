package com.talukder.mom.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "moms")
public class Moms {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "mom_subject")
	private String momsubject;

	@Column(name = "mom")
	private String mom;

	@Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	Date created;

	@Column(name = "updated_at", columnDefinition = "DATETIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	Date updated;

	public Moms() {
		// TODO Auto-generated constructor stub
	}

	public Moms(int id) {
		this.id = id;

	}

	public Moms(String subject, String mom) {
		this.momsubject = subject;
		this.mom = mom;

	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setMomsubject(String momsubject) {
		this.momsubject = momsubject;
	}

	public String getMomsubject() {
		return this.momsubject;
	}

	public void setMom(String mom) {
		this.mom = mom;
	}

	public String getMom() {
		return this.mom;
	}

	@Override
	public String toString() {
		return "Id= " + this.id + "MomSubject: " + this.momsubject + "Mom: " + this.mom;
	}

	public void setCreated(Date date) {
		this.created = date;

	}

	public void setUpdated(Date date) {
		this.updated = date;

	}

	public Date getCreated(Date date) {
		return this.created;
	}

	public Date getUpdated(Date date) {
		return this.updated;
	}

}
