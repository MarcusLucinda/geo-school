package com.lucinda.geoschool.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Student {

	private ObjectId id;
	private String name;
	private String birthDate;
	private Program program;
	private List<Grade> grades;
	private List<Skill> skills;
	private Contact contact;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public List<Grade> getGrades() {
		return grades;
	}
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	public List<Skill> getSkills() {
		return skills;
	}
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public Student createId() {
	this.setId(new ObjectId());
		return this;
	}
	
	public Student addSkill(Skill skill) {
		if(this.skills == null) {
			this.skills = new ArrayList<Skill>();
		}
		this.skills.add(skill);
		return this;
	}
	
	public Student addGrade(Grade grade) {
		if(this.grades == null) {
			this.grades = new ArrayList<Grade>();
		}
		this.grades.add(grade);
		return this;
	}
	
	
	
}
