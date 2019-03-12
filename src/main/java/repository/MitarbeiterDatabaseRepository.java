package repository;

import gui.DataBaseConnection;
import model.BerufsPosition;
import model.Mitarbeiter;

import java.sql.*;
import java.util.List;

public class MitarbeiterDatabaseRepository implements CrudRepository<Mitarbeiter> {
    private List<Mitarbeiter> mitarbeiterDatabaseList;

    public MitarbeiterDatabaseRepository(List<Mitarbeiter> mitarbeiterDatabaseList) throws SQLException, ClassNotFoundException {
        this.mitarbeiterDatabaseList = mitarbeiterDatabaseList;
        searchMitarbeiter(this.mitarbeiterDatabaseList);
    }

    private static Mitarbeiter getMitarbeiterFromResultSet(ResultSet resultSet) throws SQLException {
        Mitarbeiter mitarbeiter = null;
        long id;
        String vorname, nachname, addresse;
        BerufsPosition beruf;
        Integer erfahrungNiveau, stundenProtag;
        double stundenLohn;
        if (resultSet.next()) {
            id = resultSet.getLong("Mitarbeiter_ID");
            vorname = resultSet.getString("Vorname");
            nachname = resultSet.getString("Nachname");
            addresse = resultSet.getString("Addresse");
            beruf = BerufsPosition.valueOf(resultSet.getString("Berufsposition"));
            erfahrungNiveau = resultSet.getInt("ErfahrungNiveau");
            stundenLohn = resultSet.getDouble("StundenLohn");
            stundenProtag = resultSet.getInt("StundenProTag");
            mitarbeiter = new Mitarbeiter(id, vorname, nachname, addresse, beruf, erfahrungNiveau, stundenLohn, stundenProtag);
        }
        return mitarbeiter;
    }

    public List<Mitarbeiter> searchMitarbeiter(List<Mitarbeiter> mitarbeiterList) throws SQLException, ClassNotFoundException {
        String querry = "SELECT * FROM Mitarbeiter";
        try {
            ResultSet resultSetMitarbeiter = DataBaseConnection.dbExecuteQuery(querry);
            mitarbeiterList = getMitarbeiterList(resultSetMitarbeiter);
            return mitarbeiterList;
        } catch (SQLException e) {
            System.out.println("SQL select operation failed: " + e);
            throw e;
        }
    }

    public List<Mitarbeiter> getMitarbeiterList(ResultSet resultSet) throws SQLException {
        //private static ObservableList<Mitarbeiter> getMitarbeiterList(ResultSet resultSet) throws SQLException {
        // ObservableList<Mitarbeiter> mitarbeiterObservableList = FXCollections.observableArrayList();

        List<Mitarbeiter> list = this.mitarbeiterDatabaseList;
        Mitarbeiter mitarbeiter = null;
        long id;
        String vorname, nachname, addresse;
        BerufsPosition beruf;
        Integer erfahrungNiveau, stundenProtag;
        double stundenLohn;
        while (resultSet.next()) {
            id = resultSet.getLong("Mitarbeiter_ID");
            vorname = resultSet.getString("Vorname");
            nachname = resultSet.getString("Nachname");
            addresse = resultSet.getString("Addresse");
            {
                String x1 = resultSet.getString("Berufsposition");
                String x2;
                x2 = x1.toLowerCase();
                x1 = x2.substring(0, 1).toUpperCase() + x2.substring(1);
                beruf = BerufsPosition.valueOf(x1);
            }
            erfahrungNiveau = resultSet.getInt("ErfahrungNiveau");
            stundenLohn = resultSet.getDouble("StundenLohn");
            stundenProtag = resultSet.getInt("StundenProTag");
            mitarbeiter = new Mitarbeiter(id, vorname, nachname, addresse, beruf, erfahrungNiveau, stundenLohn, stundenProtag);
            list.add(mitarbeiter);
        }
        return list;
    }

    @Override
    public Mitarbeiter findOne(Long id) throws Exception {
        String querry = "SELECT *" +
                "   FROM Mitarbeiter" +
                "   WHERE Mitarbeiter_ID = " + id;
        try {
            ResultSet resultSetMitarbeiter = DataBaseConnection.dbExecuteQuery(querry);
            return getMitarbeiterFromResultSet(resultSetMitarbeiter);
        } catch (SQLException e) {
            System.out.println("While searching an employee with " + id + " id, an error occurred: " + e);
            throw e;
        }
    }

    @Override
    public Iterable<Mitarbeiter> findAll() {
        return this.mitarbeiterDatabaseList;
    }

    @Override
    public Mitarbeiter save(Mitarbeiter mitarbeiter) throws Exception {
        String querry =
                "BEGIN\n" +
                        "INSERT INTO Mitarbeiter " +
                        "(Mitarbeiter_ID, Vorname, Nachname, Addresse, Berufsposition, ErfahrungNiveau, StundenLohn, StundenProTag) VALUES\n" +
                        "(" +
                        mitarbeiter.getId() + ", '" +
                        mitarbeiter.getVorName() + "', '" +
                        mitarbeiter.getNachName() + "', '" +
                        mitarbeiter.getAdresse() + "', '" +
                        mitarbeiter.getBerufsPosition() + "', " +
                        mitarbeiter.getErfahrungsNiveau() + ", " +
                        mitarbeiter.getStundenLohn() + ", " +
                        mitarbeiter.getStundenProTag() + ");\n" +
                        "END;";
        mitarbeiterDatabaseList.add(mitarbeiter);
        try {
            DataBaseConnection.dbExecuteUpdate(querry);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
        System.out.println(querry);
        return mitarbeiter;
    }

    @Override
    public Mitarbeiter delete(Long id) throws Exception {
        Mitarbeiter mitarbeiter = findOne(id);
        String querry =
                "BEGIN\n" +
                        "   DELETE FROM Mitarbeiter\n" +
                        "         WHERE Mitarbeiter_ID =" + id + ";\n" +
                        "END;";
        mitarbeiterDatabaseList.remove(mitarbeiter);
        try {
            DataBaseConnection.dbExecuteUpdate(querry);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
        System.out.println(querry);
        return mitarbeiter;
    }

    @Override
    public Mitarbeiter update(Mitarbeiter mitarbeiter) throws Exception {
        String querry =
                "BEGIN\n" +
                        "UPDATE Mitarbeiter\n" +
                        "       SET Vorname = '" + mitarbeiter.getVorName() +
                        "', Nachname = '" + mitarbeiter.getNachName() +
                        "', Addresse = '" + mitarbeiter.getAdresse() +
                        "', Berufsposition = '" + mitarbeiter.getBerufsPosition() +
                        "', ErfahrungNiveau = " + mitarbeiter.getErfahrungsNiveau() +
                        ", StundenLohn = " + mitarbeiter.getStundenLohn() +
                        ", StundenProTag = " + mitarbeiter.getStundenProTag() +
                        "\n WHERE Mitarbeiter_ID = " + mitarbeiter.getId() +
                        "\n END";
        for (Mitarbeiter mitarbeiterObject : mitarbeiterDatabaseList) {
            if (mitarbeiterObject.getId() == mitarbeiter.getId()) {
                mitarbeiterObject.setVorName(mitarbeiter.getVorName());
                mitarbeiterObject.setNachName(mitarbeiter.getNachName());
                mitarbeiterObject.setAdresse(mitarbeiter.getAdresse());
                mitarbeiterObject.setErfahrungsNiveau(mitarbeiter.getErfahrungsNiveau());
                mitarbeiterObject.setStundenLohn(mitarbeiter.getStundenLohn());
                mitarbeiterObject.setStundenProTag(mitarbeiter.getStundenProTag());
                break;
            }
        }
        try {
            DataBaseConnection.dbExecuteUpdate(querry);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
        System.out.println(querry);
        return mitarbeiter;
    }
}