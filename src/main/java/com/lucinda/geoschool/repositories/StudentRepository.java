package com.lucinda.geoschool.repositories;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

import com.lucinda.geoschool.codecs.StudentCodec;
import com.lucinda.geoschool.models.Student;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Repository
public class StudentRepository {
	
	private MongoDatabase db;
	private MongoCollection<Student> students;
	
	private MongoClient getClient() {
		Codec<Document> codec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
		StudentCodec studentCodec = new StudentCodec(codec);
		CodecRegistry reg = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(studentCodec), MongoClientSettings.getDefaultCodecRegistry());
		MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(reg).build();
		
		MongoClient mongoClient = MongoClients.create(settings);
		this.db = mongoClient.getDatabase("test");
		this.students =  db.getCollection("students", Student.class);
		return mongoClient;
	}

	public void create(Student student) {
		MongoClient mongoClient = getClient();
		this.students.insertOne(student);
		mongoClient.close();
	}
	
	public List<Student> listAll(){
		MongoClient mongoClient = getClient();
		
		MongoCursor<Student> result = this.students.find().iterator();
		List<Student> studentsList = new ArrayList<Student>();
		while(result.hasNext()) {
			Student student = result.next();
			studentsList.add(student);
		}
		mongoClient.close();
		return studentsList;
	}
}
