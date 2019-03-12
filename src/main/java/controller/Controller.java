package controller;

import model.BerufsPosition;
import model.Mitarbeiter;
import repository.CrudRepository;
import repository.MitarbeiterDatabaseRepository;
import repository.MitarbeiterFileRepository;
import repository.MitarbeiterRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    //private MitarbeiterRepository mitarbeiterRepo = new MitarbeiterRepository();
    public Controller(CrudRepository<Mitarbeiter> repository) {
        this.repository = repository;
    }

    private CrudRepository<Mitarbeiter> repository;
    private String filename = "C:\\Users\\Denis\\IdeaProjects\\FXGLGames-master\\_1DBtest\\src\\main\\java\\salaries.txt";

    public String getFilename() {
        return filename;
    }

    public List<Mitarbeiter> getMitarbeiterList() throws Exception {
        chooseRepository(1);
        return (List<Mitarbeiter>) repository.findAll();
    }

    public List<Mitarbeiter> getMitarbeiterFileList() throws Exception {
        chooseRepository(2);
        return (List<Mitarbeiter>) repository.findAll();
    }

    public List<Mitarbeiter> getMitarbeiterDatabaseList() throws Exception {
        chooseRepository(3);
        return (List<Mitarbeiter>) repository.findAll();
    }

    public void chooseRepository(final int OPTION) throws Exception {
        if (OPTION == 1) {
            //List<Mitarbeiter> list = repository.
            repository = new MitarbeiterRepository(new ArrayList<>());
        }
        else if (OPTION == 2)
            repository = new MitarbeiterFileRepository(new ArrayList<>(), "C:\\Users\\Denis\\IdeaProjects\\FXGLGames-master\\_1DBtest\\src\\main\\java\\persons.txt");
        else if (OPTION == 3)
            repository = new MitarbeiterDatabaseRepository(new ArrayList<>());
    }

    public Mitarbeiter findMitarbeiterById(Long id) throws Exception {
        Validators.idValidatorLessThenZero(id);
        return repository.findOne(id);
    }

    public Iterable<Mitarbeiter> findAll() {
        return repository.findAll();
    }

    public Mitarbeiter saveMitarbeiter(Mitarbeiter mitarbeiter) throws Exception {
        Validators.mitarbeiterValidator(mitarbeiter);
        Validators.listValidator(mitarbeiter, (List<Mitarbeiter>) repository.findAll());
        repository.save(mitarbeiter);
        return mitarbeiter;
    }

    public Mitarbeiter updateMitarbeiter(Mitarbeiter mitarbeiter) throws Exception {
        Validators.idValidatorLessThenZero(mitarbeiter.getId());
        Validators.mitarbeiterValidator(mitarbeiter);
        repository.update(mitarbeiter);
        return mitarbeiter;
    }

    public Mitarbeiter deleteMitarbeiter(Long id) throws Exception {
        Validators.idValidatorLessThenZero(id);
        return repository.delete(id);

    }

    public double calculateSalary(Mitarbeiter mitarbeiter, Integer numberOfDays) throws Exception {
        Mitarbeiter mitarbeiterObject = findMitarbeiterById(mitarbeiter.getId());
        return mitarbeiterObject.getStundenLohn() * mitarbeiterObject.getStundenProTag() * numberOfDays;
    }

    private void writeFile(String data) throws FileNotFoundException {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(new File(filename), true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (printWriter != null) {

            printWriter.append(data);
        }
        printWriter.close();
    }

    public List<Double> calculateSalaryBerufsPosition(BerufsPosition berufsPosition) throws Exception {
        List<Double> salariesList = new ArrayList<>();
        new FileOutputStream(new File(filename), false);
        List<Mitarbeiter> mitarbeiterList = (List<Mitarbeiter>) findAll();
        writeFile("\nSalaries for all: " + berufsPosition + " \n");
        int count = 0;
        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            if (mitarbeiter.getBerufsPosition() == berufsPosition) {
                double salary = calculateSalary(mitarbeiter, 365);
                salariesList.add(salary);
                writeFile(mitarbeiter.getNachName() + "  " + mitarbeiter.getVorName() + " has salary: " + salary + " \n");
                count++;
            }
        }
        if (count == 0) writeFile("\nThere is no worker for this experience level!");
        return salariesList;
    }

    public List<Double> calculateSalaryForAllWorkers() throws Exception {
        List<Double> salariesList = new ArrayList<>();
        new FileOutputStream(new File(filename), false);
        List<Mitarbeiter> mitarbeiterList = (List<Mitarbeiter>) findAll();
        writeFile("\nSalaries for all workers: \n");
        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            double salary = calculateSalary(mitarbeiter, 365);
            salariesList.add(salary);
            writeFile(mitarbeiter.getNachName() + "  " + mitarbeiter.getVorName() + " has salary: " + salary + " \n");
        }
        return salariesList;
    }

    public List<Double> calculateSalaryForAllExperianceLevels(int erfahrungsNiveau) throws Exception {
        List<Double> salariesList = new ArrayList<>();
        new FileOutputStream(new File(filename), false);
        List<Mitarbeiter> mitarbeiterList = (List<Mitarbeiter>) findAll();
        int count = 0;
        writeFile("\nSalaries for experience level: " + erfahrungsNiveau + '\n');
        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            if (mitarbeiter.getErfahrungsNiveau() == erfahrungsNiveau) {
                double salary = calculateSalary(mitarbeiter, 365);
                salariesList.add(salary);
                writeFile(mitarbeiter.getNachName() + "  " + mitarbeiter.getVorName() + " has salary: " + salary + " \n");
                count++;
            }
        }
        if (count == 0) writeFile("\nThere is no worker for this experience level!");
        return salariesList;
    }
}