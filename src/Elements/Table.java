package Elements;

import People.Client;

import java.util.ArrayList;

public class Table {
    public Tables table;
    public int size;

    public Table(Tables table, int size) {
        this.table = table;
        this.size = size;
    }

    public final ArrayList<Client> groups = new ArrayList<>();
    public final ArrayList<Client> reserved = new ArrayList<>();
    public int peopleAmount;

    /**
     * Wynosi 0 jeśli nikogo nie ma przy stole
     */
    private int actualCardinality;

    public synchronized int getActualCardinality() {
        return actualCardinality;
    }

    /**
     * Usuwa klienta ze stołu
     */
    public synchronized void removeGroup(Client group) {
        groups.remove(group);
        if (groups.isEmpty()) {
            actualCardinality = 0;
        }
        recalcPeopleAmount();
    }

    /**
     * Rezerwuje stolik aż do przybycia klienta
     */
    public synchronized void setReserved(Client group) {
        reserved.add(group);
        actualCardinality = group.cardinality;
        recalcPeopleAmount();
    }

    public synchronized void removeReserved(Client group) {
        reserved.remove(group);
        if (!reserved.isEmpty()) {
            actualCardinality = reserved.get(0).cardinality;
        }
        recalcPeopleAmount();
    }

    /**
     * Dodaje klienta do stołu
     */
    public synchronized void setGroup(Client group) {
        groups.add(group);
        reserved.remove(group);
        actualCardinality = group.cardinality;
        recalcPeopleAmount();
    }

    /**
     * Przelicza liczbę osób przy stole
     */
    public synchronized void recalcPeopleAmount() {
        peopleAmount = (reserved.size() + groups.size()) * actualCardinality;
        table.recalcPizza();
    }

    /**
     * Zwraca true jeśli aktualne cardinality jest takie samo jak te
     * które jest podane oraz gdy jest jeszcze na nie miejsce
     */
    public synchronized boolean isAvaiableForCardinality(int cardinality) {
        return cardinality == actualCardinality && size >= peopleAmount + cardinality;
    }
}
