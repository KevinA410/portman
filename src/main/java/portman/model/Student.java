package portman.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;

public class Student {
    public static final String path = "studentInfo.json";

    private String id;
    private String name;
    private String degree;
    private String email;
    private String grade;
    private String group;
    
    public Student() {

    }

    public Student(String id, String name, String degree, String email) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.email = email;
    }

    public Student(String id, String name, String degree, String email, String grade, String group) {
        this(id, name, degree, email);
        this.grade = grade;
        this.group = group;
    }

    public void save() {
        File file = new File(path);
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);

        try {
            file.createNewFile();
            
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(jsonString);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get() {
        File file = new File(path);
        Gson gson = new Gson();
        String jsonString = "";
        Student tmp = new Student();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            jsonString = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tmp = gson.fromJson(jsonString, Student.class);

        id = tmp.id;
        name = tmp.name;
        degree = tmp.degree;
        email = tmp.email;
        grade = tmp.grade;
        group = tmp.group;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegree() {
        return degree;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
}
