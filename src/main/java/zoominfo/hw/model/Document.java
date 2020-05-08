package zoominfo.hw.model;

import java.io.Serializable;

public class Document implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getId() { return id; }

    public void setId(String id) {  this.id = id; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String toString() {
        return  "{\"id\":\"" + id + "\", " +
                "\"firstName\":\"" + firstName + "\", " +
                "\"lastName\":\"" + lastName + "\", " +
                "\"age\":" + age + "}";
    }
}
