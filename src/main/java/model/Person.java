package model;

import java.util.Objects;

public class Person {

    private long id;
    private String vorName;
    private String nachName;
    private String adresse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getId() == person.getId() &&
                Objects.equals(getVorName(), person.getVorName()) &&
                Objects.equals(getNachName(), person.getNachName()) &&
                Objects.equals(getAdresse(), person.getAdresse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVorName(), getNachName(), getAdresse());
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", vorName='" + vorName + '\'' +
                ", nachName='" + nachName + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }

    public Person(long id, String vorName, String nachName, String adresse) {
        this.id = id;
        this.vorName = vorName;
        this.nachName = nachName;
        this.adresse = adresse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVorName() {
        return vorName;
    }

    public void setVorName(String vorName) {
        this.vorName = vorName;
    }

    public String getNachName() {
        return nachName;
    }

    public void setNachName(String nachName) {
        this.nachName = nachName;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
