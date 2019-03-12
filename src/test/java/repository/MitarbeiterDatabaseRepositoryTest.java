package repository;

import gui.DataBaseConnection;
import model.BerufsPosition;
import model.Mitarbeiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MitarbeiterDatabaseRepositoryTest {

    List<Mitarbeiter> mitarbeiterList;
    MitarbeiterDatabaseRepository mitarbeiterDatabaseRepository;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        mitarbeiterList = new ArrayList<>();
        mitarbeiterDatabaseRepository = new MitarbeiterDatabaseRepository(mitarbeiterList);
    }

    @Test
    void searchMitarbeiter() throws SQLException, ClassNotFoundException {
//        mitarbeiterList = mitarbeiterDatabaseRepository.searchMitarbeiter(mitarbeiterList);
//        System.out.println(mitarbeiterList);
        String querry = "SELECT * FROM Mitarbeiter";
        ResultSet rs = DataBaseConnection.dbExecuteQuery(querry);

        System.out.println(mitarbeiterDatabaseRepository.getMitarbeiterList(rs));
    }

    @Test
    void findOne() throws Exception {
        System.out.println(mitarbeiterDatabaseRepository.findOne((long) 1));
    }

    @Test
    void findAll() {
        System.out.println(mitarbeiterDatabaseRepository.findAll());
    }

    @Test
    void save() throws Exception {
        Mitarbeiter mitarbeiter = new Mitarbeiter(10, "DEnis", "Pascu", "LALALA", BerufsPosition.Entwickler, 8,8,8);
        System.out.println(mitarbeiterDatabaseRepository.save(mitarbeiter));
    }

    @Test
    void delete() throws Exception {
        System.out.println(mitarbeiterDatabaseRepository.delete((long) 10));

    }

    @Test
    void update() throws Exception {
        Mitarbeiter mitarbeiter = new Mitarbeiter(2, "DEnis", "Pascu", "LALALA", BerufsPosition.Entwickler, 8,8,8);
        System.out.println(mitarbeiterDatabaseRepository.update(mitarbeiter));
    }
}