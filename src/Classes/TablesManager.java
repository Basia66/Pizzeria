package Classes;

import Elements.Table;
import Elements.Tables;
import Frame.Pizzeria;
import People.Client;

public class TablesManager {
    Pizzeria pizzeria;

    public TablesManager(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    /**
     * Powiadomienie że obłożenie stołów się zmieniło i trzeba rozpatrzeć zmiany
     */
    public synchronized void notifyTablesChanged() {
        reassignTables();
    }

    /**
     * Powiadomienie że kolejna się zmieniła i trzeba rozpatrzeć zmiany
     */
    public synchronized void notifyQueueChanged() {
        reassignTables();
    }

    /**
     * Przydziela wolne stoliki
     */
    private void reassignTables() {
        if (pizzeria.data.queue.list.isEmpty())
            return;

        synchronized (pizzeria.data) {
            int size = pizzeria.data.queue.list.size();
            for (int j = 0; j < size; j++) {
                Client c = pizzeria.data.queue.list.get(j);

                // na początku szukamy stoły o takiej samej wielkości
                // jaka jest wielkość grupy klientów i potem wyższej
                for (int i = c.cardinality - 1; i < pizzeria.data.tables.size(); i++) {
                    Tables ts = pizzeria.data.tables.get(i);
                    Table t = ts.getFreeTableForCardinality(c.cardinality);
                    if (t != null) {
                        // udało się znaleźć wolny stół
                        t.setReserved(c);

                        pizzeria.data.queue.remove(c);
                        j--;
                        size--;

                        c.goToAssignedTable(t);
                        break;
                    }
                }
            }
        }
    }
}
