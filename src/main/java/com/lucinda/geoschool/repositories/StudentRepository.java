package com.lucinda.geoschool.repositories;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.lucinda.geoschool.codecs.StudentCodec;
import com.lucinda.geoschool.models.Student;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

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

	public Student getById(String id) {
		MongoClient mongoClient = getClient();
		Student student = this.students.find(Filters.eq("_id", new ObjectId(id))).first();
		mongoClient.close();
		return student;
	}

	public void update(Student student) {
		MongoClient mongoClient = getClient();
		this.students.updateOne(Filters.eq("_id", student.getId()), new Document("$set", student));
		mongoClient.close();
	}

	public List<Student> searchByName(String name){
		MongoClient mongoClient = getClient();
		MongoCursor<Student> result = this.students.find(Filters.eq("name", name), Student.class).iterator();
		List<Student> students = new ArrayList<Student>();
		while(result.hasNext()) {
			Student student = result.next();
			students.add(student);
		}
		mongoClient.close();
		return students;
	}

	public List<Student> searchByGrade(String status, Double grade){
		MongoClient mongoClient = getClient();
		MongoCursor<Student> result = null;
		if(status.equalsIgnoreCase("approved")) {
			result = this.students.find(Filters.elemMatch("grades",  Filters.gte("grade", grade)), Student.class).iterator();
		}else {
			System.out.println(grade);
			result = this.students.find(Filters.elemMatch("grades",  Filters.lt("grade", grade)), Student.class).iterator();
		}

		List<Student> students = new ArrayList<Student>();
		while(result.hasNext()) {
			Student student = result.next();
			students.add(student);
		}
		mongoClient.close();
		return students;
	}

	public List<Student> searchByGeo(Student student) {
		MongoClient mongoClient = getClient();
		this.students.createIndex(Indexes.geo2dsphere("contact"));
		List<Double> coordinates = student.getContact().getCoordinates();
		Point refPoint = new Point(new Position(coordinates.get(0), coordinates.get(1)));
		
		MongoCursor<Student> result = this.students.find(Filters.nearSphere("contact", refPoint, 20000.0, 0.0)).iterator();
		List<Student> students = new ArrayList<Student>();
		while(result.hasNext()) {
			Student otherStudent = result.next();
			students.add(otherStudent);
		}
		mongoClient.close();
		return students;
	}
}
