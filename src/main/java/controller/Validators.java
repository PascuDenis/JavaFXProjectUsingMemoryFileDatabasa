package controller;

import model.Mitarbeiter;
import model.Person;

import java.util.List;

public class Validators {

    public static void personValidator(Person person) throws Exception {
        idValidatorLessThenZero(person.getId());
        if (person.getVorName().equals(""))
            throw new Exception("Vorname can not be empty\n");
        if (person.getNachName().equals(""))
            throw new Exception("Nachname can not be empty\n");
        if (person.getAdresse().equals(""))
            throw new Exception("Adresse can not be empty\n");
    }

    public static void idValidatorLessThenZero(Long id) throws Exception {
        if (id <= 0)
            throw new Exception("Id can not be negative\n");
    }

    public static boolean idExistsValidation(Long id, List<Mitarbeiter> mitarbeiterList) throws Exception {
        for(Mitarbeiter mitarbeiter : mitarbeiterList){
            if (mitarbeiter.getId() == id)
                return true;
        }
        return false;
    }

    public static void idNotExistsValidation(Long id, List<Mitarbeiter> mitarbeiterList) throws Exception {
        for(Mitarbeiter mitarbeiter : mitarbeiterList){
            if (mitarbeiter.getId() == id)
                break;
        }
        throw new Exception("The id does not exist!");
    }

    public static void mitarbeiterValidator(Mitarbeiter mitarbeiter) throws Exception{
        personValidator(mitarbeiter);
        if (mitarbeiter.getErfahrungsNiveau() <= 0 && mitarbeiter.getErfahrungsNiveau() > 7)
            throw new Exception("Erfahrungs Niveau must be positiv and less than 8\n");
        if(mitarbeiter.getStundenLohn() <= 1)
            throw new Exception("StundenLohn must be greater than 1\n");
        if (mitarbeiter.getStundenProTag() <4 && mitarbeiter.getStundenProTag() > 10){
            throw new Exception("StundenProTag must be between 4 and 10 hrs \n");
        }
    }

    public static void listValidator(Mitarbeiter mitarbeiter, List<Mitarbeiter> mitarbeiterList) throws Exception{
        if (mitarbeiterList.contains(mitarbeiter))
            throw new  Exception("Duplicate Id\n");
    }
}
