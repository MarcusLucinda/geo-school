package com.lucinda.geoschool.codecs;

import java.util.ArrayList;
import java.util.List;

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

import com.lucinda.geoschool.models.Contact;
import com.lucinda.geoschool.models.Grade;
import com.lucinda.geoschool.models.Program;
import com.lucinda.geoschool.models.Skill;
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
		List<Skill> skills = student.getSkills();
		List<Grade> grades = student.getGrades();
		Contact contact = student.getContact();

		Document document = new Document();
		document.put("_id", id);
		document.put("name", name);
		document.put("birthDate", birthDate);
		document.put("program", new Document("name", program.getName()));

		if(skills != null) {
			List<Document> skillsDoc = new ArrayList<Document>();
			for (Skill skill : skills) {
				skillsDoc.add(new Document("name", skill.getName()).append("level", skill.getLevel()));
			}
			document.put("skills", skillsDoc);
		}

		if(grades != null) {
			List<Document> gradesDoc = new ArrayList<Document>();
			for (Grade grade : grades) {
				gradesDoc.add(new Document("grade", grade.getGrade()));
			}
			document.put("grades", gradesDoc);
		}
		
		List<Double> coordinates = new ArrayList<Double>();
		for (Double coord : contact.getCoordinates()) {
			coordinates.add(coord);
		}
		document.put("contact", new Document().append("endereco", contact.getAddress())
				.append("coordinates", coordinates).append("type", contact.getType()));
		
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

		List<Document> skillsDocs = (List<Document>) document.get("skills");
		if(skillsDocs != null) {
			List<Skill> skills = new ArrayList<Skill>();
			for (Document skillDoc : skillsDocs) {
				Skill skill = new Skill();
				skill.setName(skillDoc.getString("name"));
				skill.setLevel(skillDoc.getString("level"));
				skills.add(skill);
			}
			student.setSkills(skills);
		}
		
		List<Document> gradesDocs = (List<Document>) document.get("grades");
		if(gradesDocs != null) {
			List<Grade> grades = new ArrayList<Grade>();
			for (Document gradeDoc : gradesDocs) {
				Grade grade = new Grade();
				grade.setGrade(gradeDoc.getDouble("grade"));
				grades.add(grade);
			}
			student.setGrades(grades);
		}
		
		Document contact = (Document) document.get("contact");
		if (contact != null) {
			String address = contact.getString("adress");
			List<Double> coordinates = (List<Double>) contact.get("coordinates");
			student.setContact(new Contact(address, coordinates));
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
