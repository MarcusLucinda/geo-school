package com.lucinda.geoschool.repositories;


import org.springframework.stereotype.Repository;

import com.lucinda.geoschool.models.Student;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class StudentRepository {

	public void create(Student student) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		MongoCollection<Student> students =  db.getCollection("students", Student.class);
		students.insertOne(student);
		mongoClient.close();
	}
}
