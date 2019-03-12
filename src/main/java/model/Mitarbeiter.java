package model;

import javafx.collections.ObservableList;

public class Mitarbeiter extends Person {
    private BerufsPosition berufsPosition;
    private Integer erfahrungsNiveau;
    private double stundenLohn;
    private Integer stundenProTag;

    public Mitarbeiter(long id, String vorName, String nachName, String adresse, BerufsPosition berufsPosition, Integer erfahrungsNiveau, double stundenLohn, Integer stundenProTag) {
        super(id, vorName, nachName, adresse);
        this.berufsPosition = berufsPosition;
        this.erfahrungsNiveau = erfahrungsNiveau;
        this.stundenLohn = stundenLohn;
        this.stundenProTag = stundenProTag;
    }

    @Override
    public String toString() {
        return //"\n" +
                getId() +
                "," + getVorName() +
                "," + getNachName() +
                "," + getAdresse() +
                "," + berufsPosition +
                "," + erfahrungsNiveau +
                "," + stundenLohn +
                "," + stundenProTag + "\n";
    }

    public BerufsPosition getBerufsPosition() {
        return berufsPosition;
    }

    public void setBerufsPosition(BerufsPosition berufsPosition) {
        this.berufsPosition = berufsPosition;
    }

    public Integer getErfahrungsNiveau() {
        return erfahrungsNiveau;
    }

    public void setErfahrungsNiveau(Integer erfahrungsNiveau) {
        this.erfahrungsNiveau = erfahrungsNiveau;
    }

    public double getStundenLohn() {
        return stundenLohn;
    }

    public void setStundenLohn(double stundenLohn) {
        this.stundenLohn = stundenLohn;
    }

    public Integer getStundenProTag() {
        return stundenProTag;
    }

    public void setStundenProTag(Integer stundenProTag) {
        this.stundenProTag = stundenProTag;
    }

}
