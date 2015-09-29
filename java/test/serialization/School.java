package serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class School {
	List<Student> students = new ArrayList<Student>();
	List<Professor> professors = new ArrayList<Professor>();
	School(){
		this.students.add(new Student("tom",123));
		this.students.add(new Student("bill",121));
		this.students.add(new Student("smith",122));
		this.professors.add(new Professor("bob","biology"));
		this.professors.add(new Professor("michael","math"));
		this.professors.add(new Professor("duriel","computer science"));
	}
	public static void main(String[] args) {
        try {
			School school = new School();
	        Gson gson = new GsonBuilder().create();
	        String jsonString = gson.toJson(school);
	        School test = gson.fromJson(jsonString, School.class);
	        System.out.print(jsonString);
			System.in.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Student {
	Name name;
	ID id;
	Student(String name, int id){
		this.name = new Name(name);
		this.id = new ID(id);
	}
}

class Professor {
	Name name;
	Department department;
	Professor(String name, String department)
	{
		this.name = new Name(name);
		this.department = new Department(department);
	}
}

class Name {
	String value;
	Name(String name){
		this.value = name;
	}
}

class ID {
	int id;
	ID(int id){
		this.id = id;
	}
}

class Department {
	Name name;
	Department(String name)
	{
		this.name = new Name(name);
	}
}
