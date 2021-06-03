package com.lucinda.geoschool.codecs;

import java.time.LocalDate;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.lucinda.geoschool.models.Program;
import com.lucinda.geoschool.models.Student;

public class StudentCodec implements CollectibleCodec<Student> {

	private Codec<Document> codec;
	
	public StudentCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Student student, EncoderContext encoderContext) {
		ObjectId id = student.getId();
		String name = student.getName();
		String birthDate = student.getBirthDate();
		Program program = student.getProgram();
		
		Document document = new Document();
		document.put("_id", id);
		document.put("name", name);
		document.put("birthDate", birthDate);
		document.put("program", new Document("name", program.getName()));
		
		codec.encode(writer, document, encoderContext);
		
	}

	@Override
	public Class<Student> getEncoderClass() {
		return Student.class;
	}

	@Override
	public Student decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		Student student = new Student();
		student.setId(document.getObjectId("_id"));
		student.setName(document.getString("name"));
		student.setBirthDate(document.getString("birthDate"));
		Document programDoc = (Document) document.get("program");
		if(programDoc != null) {
			Program program = new Program();
			program.setName(programDoc.getString("name"));
			student.setProgram(program);
		}
		return student;
	}

	@Override
	public Student generateIdIfAbsentFromDocument(Student student) {
		return documentHasId(student) ? student : student.createId();
	}

	@Override
	public boolean documentHasId(Student student) {
		return student.getId() != null;
	}

	@Override
	public BsonValue getDocumentId(Student student) {
		if(!documentHasId(student)) {
			throw new IllegalStateException();
		}
		return new BsonString(student.getId().toHexString());
	}

}