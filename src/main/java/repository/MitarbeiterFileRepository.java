package repository;

import model.BerufsPosition;
import model.Mitarbeiter;

import java.io.*;
import java.util.List;

public class MitarbeiterFileRepository implements CrudRepository<Mitarbeiter> {
    //filename =  "C:\\Users\\Denis\\IdeaProjects\\lab_3\\src\\persons.txt";
    //private MitarbeiterRepository mitarbeiterRepo = new MitarbeiterRepository();
    private List<Mitarbeiter> mitarbeiterFileList;
    private String filename;

    public MitarbeiterFileRepository(List<Mitarbeiter> mitarbeiterFileList, String fileName) throws Exception {
        this.mitarbeiterFileList = mitarbeiterFileList;
        this.filename = fileName;
        readFromFile(this.mitarbeiterFileList, this.filename);
    }


    public List<Mitarbeiter> getMitarbeiterFileList() {
        return this.mitarbeiterFileList;
    }


    public void writeMitarbeiterToFile(List<Mitarbeiter> mitarbeiterList) throws Exception {
        File file = new File("C:\\Users\\Denis\\IdeaProjects\\FXGLGames-master\\_1DBtest\\src\\main\\java\\persons.txt");
        FileWriter fileWriter;
        if (file.exists()) {
            fileWriter = new FileWriter(file);
        } else {
            file.createNewFile();
            fileWriter = new FileWriter(file);

        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            if (mitarbeiter instanceof Mitarbeiter) {
                printWriter.printf("%d,%s,%s,%s,%s,%d,%f,%d\n",
                        mitarbeiter.getId(),
                        mitarbeiter.getVorName(),
                        mitarbeiter.getNachName(),
                        mitarbeiter.getAdresse(),
                        mitarbeiter.getBerufsPosition(),
                        mitarbeiter.getErfahrungsNiveau(),
                        mitarbeiter.getStundenLohn(),
                        mitarbeiter.getStundenProTag());
            }
        }
        fileWriter.close();
        printWriter.close();
    }

    public List<Mitarbeiter> readFromFile(List<Mitarbeiter> mitarbeiterList, String filename) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] mitarbeiter = line.split(cvsSplitBy);
                Mitarbeiter mitarbeiterObject = null;
                for (int i = 0; i < 8; i++) {
                    long mitarbeiterId = Long.parseLong(mitarbeiter[0]);
                    String mitarbeiterVorName = mitarbeiter[1];
                    String mitarbeiterNachName = mitarbeiter[2];
                    String mitarbeiterAddress = mitarbeiter[3];
                    BerufsPosition mitmitarbeiterBerufsposition = null;
                    if ("Tester".equals(mitarbeiter[4])) {
                        mitmitarbeiterBerufsposition = BerufsPosition.Tester;
                    } else if ("Entwickler".equals(mitarbeiter[4])) {
                        mitmitarbeiterBerufsposition = BerufsPosition.Entwickler;
                    } else if ("ProjectManager".equals(mitarbeiter[4])) {
                        mitmitarbeiterBerufsposition = BerufsPosition.ProjectManager;
                    } else if ("TeamLeader".equals(mitarbeiter[4])) {
                        mitmitarbeiterBerufsposition = BerufsPosition.TeamLeader;
                    }
                    Integer mitarbeiterErfahrungsNiveau = Integer.parseInt(mitarbeiter[5]);
                    double mitarbeiterStundenLohn = Double.parseDouble(mitarbeiter[6]);
                    Integer mitarbeiterStundenProTag = Integer.parseInt(mitarbeiter[7]);

                    mitarbeiterObject = new Mitarbeiter(mitarbeiterId, mitarbeiterVorName, mitarbeiterNachName, mitarbeiterAddress, mitmitarbeiterBerufsposition, mitarbeiterErfahrungsNiveau, mitarbeiterStundenLohn, mitarbeiterStundenProTag);
                }
                mitarbeiterList.add(mitarbeiterObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mitarbeiterList;
    }

    @Override
    public Mitarbeiter findOne(Long id) throws Exception {
        for (Mitarbeiter mitarbeiter : getMitarbeiterFileList()) {
            if (mitarbeiter.getId() == id) {
                return mitarbeiter;
            }
        }
        throw new Exception("There is no worker with the given id!");
    }

    @Override
    public Iterable<Mitarbeiter> findAll() {
        return this.mitarbeiterFileList;
    }

    @Override
    public Mitarbeiter save(Mitarbeiter mitarbeiter) throws Exception {
        mitarbeiterFileList.add(mitarbeiter);
        writeMitarbeiterToFile(mitarbeiterFileList);
        return mitarbeiter;
    }

    @Override
    public Mitarbeiter delete(Long id) throws Exception {

        Mitarbeiter mitarbeiter = findOne(id);
        //System.out.println(mitarbeiter);
        mitarbeiterFileList.remove(mitarbeiter);
        writeMitarbeiterToFile(mitarbeiterFileList);
        return mitarbeiter;
    }

    @Override
    public Mitarbeiter update(Mitarbeiter mitarbeiter) throws Exception {

        if (mitarbeiter == null) return mitarbeiter;
        for (Mitarbeiter mitarbeiterObject : mitarbeiterFileList) {
            if (mitarbeiterObject.getId() == mitarbeiter.getId()) {
                mitarbeiterObject.setVorName(mitarbeiter.getVorName());
                mitarbeiterObject.setNachName(mitarbeiter.getNachName());
                mitarbeiterObject.setAdresse(mitarbeiter.getAdresse());
                mitarbeiterObject.setErfahrungsNiveau(mitarbeiter.getErfahrungsNiveau());
                mitarbeiterObject.setStundenLohn(mitarbeiter.getStundenLohn());
                mitarbeiterObject.setStundenProTag(mitarbeiter.getStundenProTag());
                writeMitarbeiterToFile(mitarbeiterFileList);
                return null;
            }
        }
        return mitarbeiter;
    }

}