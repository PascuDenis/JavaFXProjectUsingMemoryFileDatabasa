package repository;

import model.Mitarbeiter;
import repository.exceptions.ObjectAlreadyExistsException;

import java.util.List;

import static model.BerufsPosition.*;

public class MitarbeiterRepository implements CrudRepository<Mitarbeiter> {
    private List<Mitarbeiter> mitarbeiterList;

    public MitarbeiterRepository(List<Mitarbeiter> mitarbeiterList) {

        if (mitarbeiterList.size() == 0) {
            this.mitarbeiterList = mitarbeiterList;
            this.mitarbeiterList = populateMemory(this.mitarbeiterList);
        }
    }

    public List<Mitarbeiter> getMitarbeiterList() {
        return this.mitarbeiterList;
    }

    public void setMitarbeiterList(List<Mitarbeiter> mitarbeiterList) {
        this.mitarbeiterList = mitarbeiterList;
    }

    @Override
    public Mitarbeiter findOne(Long id) {
        for (Mitarbeiter mitarbeiter : getMitarbeiterList()) {
            if (mitarbeiter.getId() == id) {
                return mitarbeiter;
            }
        }
        return null;
    }

    @Override
    public Iterable<Mitarbeiter> findAll() {
        return this.mitarbeiterList;
    }

    @Override
    public Mitarbeiter save(Mitarbeiter mitarbeiter) throws ObjectAlreadyExistsException {
        mitarbeiterList.add(mitarbeiter);
        return mitarbeiter;
    }

    @Override
    public Mitarbeiter delete(Long id) throws Exception {
        Mitarbeiter mitarbeiter = findOne(id);
        if(mitarbeiter != null) {
            mitarbeiterList.remove(mitarbeiter);
            return mitarbeiter;
        }
        throw new Exception("The worker does not exist");
    }

    @Override
    public Mitarbeiter update(Mitarbeiter mitarbeiter) {
        if (mitarbeiter == null) return mitarbeiter;
        for (Mitarbeiter mitarbeiterObject : mitarbeiterList) {
            if (mitarbeiterObject.getId() == mitarbeiter.getId()) {
                mitarbeiterObject.setVorName(mitarbeiter.getVorName());
                mitarbeiterObject.setNachName(mitarbeiter.getNachName());
                mitarbeiterObject.setAdresse(mitarbeiter.getAdresse());
                mitarbeiterObject.setErfahrungsNiveau(mitarbeiter.getErfahrungsNiveau());
                mitarbeiterObject.setStundenLohn(mitarbeiter.getStundenLohn());
                mitarbeiterObject.setStundenProTag(mitarbeiter.getStundenProTag());
                return null;
            }
        }
        return mitarbeiter;
    }

    private List<Mitarbeiter> populateMemory(List<Mitarbeiter> mitarbeiterList){
        mitarbeiterList.add(new Mitarbeiter(1, "Ion", "Ionescu", "Cluj", Entwickler, 3, 15, 4));
        mitarbeiterList.add(new Mitarbeiter(2, "Maria", "Moldovan", "Munchen", Entwickler, 2, 20, 6));
        mitarbeiterList.add(new Mitarbeiter(3, "George", "Iures", "Mures", Tester, 1, 10, 8));
        mitarbeiterList.add(new Mitarbeiter(4, "Marcel", "Eminescu", "Sighisoara", TeamLeader, 4, 30, 8));
        mitarbeiterList.add(new Mitarbeiter(5, "Nicole", "Velea", "Cluj", ProjectManager, 1, 18.5, 8));
        return mitarbeiterList;
    }
}
