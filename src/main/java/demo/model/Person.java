package demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public abstract class Person {
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String cf;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected int id;
    protected String password;
    protected Person(){}
    public Person(String username, String firstName, String lastName, String password, String cf) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.cf = cf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCf() {return cf;}

    public void setCf(String cf) {this.cf = cf;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return username.equals(person.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
