package People;

import Classes.DataManager;
import Elements.QueueRoom;
import Elements.Table;
import Frame.Pizzeria;

import java.awt.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klient to inaczej grupa składające się z kilku osobób(prawdopodobnie wezmą jeden rachunek)
 * liczność tej gurpy wyznacza podane cardinality
 */
public class Client extends Person {
    private Table table;
    private boolean served = false;

    /**
     * Liczność grupy
     *
     * @see Classes.DataManager groupMaxAmount
     */
    public int cardinality;

    public boolean inQueue = false;
    public boolean hasTable = false;

    /**
     * Index w kolejce
     */
    public int queueIndex;

    /**
     * Gdy stolik się zwolni to semafor zostanie odblokowany
     * lub gdy trzeba się przesunąć w kolejce
     */
    private final Semaphore busy = new Semaphore(0);

    /**
     * Tworzy nowego niewidocznego klienta z losową licznością
     */
    public Client(Pizzeria pizzeria, int x, int y) {
        super(pizzeria, x, y);
        this.visibility = false;
        this.cardinality = ThreadLocalRandom.current().nextInt(1, DataManager.groupMaxAmount + 1);
    }

    /**
     * Tworzy nowego widocznego klienta z ustaloną licznością oraz koordynatami
     */
    public Client(Pizzeria pizzeria, int x, int y, int cardinality) {
        super(pizzeria, x, y);
        this.cardinality = cardinality;
    }

    /**
     * Główna pętla wątku
     */
    @Override
    public void run() {
        super.run();

        while (true) {
            // obsłużony klient zwalnia stolik i wychodzi
            if (served) {
                // opuszcza stolik
                table.removeGroup(this);
                pizzeria.tablesManager.notifyTablesChanged();
                show();

                // wychodzi z lokalu
                setImageHappy();
                translate_delta_time(DataManager.enterAndExitCords[0], DataManager.enterAndExitCords[1]);
                hide();
                pizzeria.data.clients.remove(this);

                break;
            }

            // obsługuje chodzenie
            if (move_order) {
                translate_delta_time(destination_x, destination_y);
                move_order = false;
            }

            // obsługuje czekanie w kolejce
            if (inQueue) {
                pizzeria.tablesManager.notifyQueueChanged();
                try {
                    busy.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // obsługuje jedzenie
            if (hasTable) {
                // TUTAJ SKŁADA ZAMÓWIENIA
                // PASS

                // dostał pizze
                setImagePizza();

                // TODO
                // idzie do stolu
                boolean result = translate_delta_time_to_table(table.table.getX(), table.table.getY());
                // wraca do kolejki
                if (!result) {
                    setImageStand();
                    continue;
                }

                // TODO
//                // idzie do stolu
//                translate_delta_time(table.table.getX(), table.table.getY());

                hide();
                table.setGroup(this);

                eat();
                served = true;
            }
        }


    }

    /**
     * Wykonuje translacje wraz z upływającym czasem
     */
    protected boolean translate_delta_time_to_table(int x, int y) {
        int x_change = this.x < x ? DataManager.move_range : -DataManager.move_range;
        int y_change = this.y < y ? DataManager.move_range : -DataManager.move_range;

        boolean x_reached = this.x == x;
        boolean y_reached = this.y == y;
        while (!x_reached || !y_reached) {
            // TODO
            // jeśli wystąpił błąd podczas podziału stolików
            if (table.peopleAmount > table.size) {
                table.removeReserved(this);
                pizzeria.data.queue.insertAt(this, this.queueIndex);
                hasTable = false;
                inQueue = true;
                return false;
            }

            // porusza grupą klientów
            if (!x_reached) {
                this.x += x_change;

                // sprawdza warunki ukończenia poruszania się
                if (this.x == x)
                    x_reached = true;
            }

            if (!y_reached) {
                this.y += y_change;

                // sprawdza warunki ukończenia poruszania się
                if (this.y == y)
                    y_reached = true;
            }

            // usypia na chwilę klienta
            sleep_anim();
        }

        return true;
    }


    /**
     * Metoda czekająca aż klient zje
     */
    private void eat() {
        try {
            while (DataManager.speed == 0) {
                sleep(100);
            }

            sleep((long) (DataManager.eatingTime / DataManager.speed));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Kolejkuje klienta
     */
    public void QueueClient(int seatX, int seatY) {
        goTo(seatX, seatY);
        inQueue = true;
    }

    /**
     * Przemieszcza klienta w kolejce
     */
    public void moveInQueue(int index) {
        busy.release();
        queueIndex = index;
        goTo(queueIndex * DataManager.queueInterspace, QueueRoom.queueSeatY);
    }

    /**
     * Przemieszcza klienta do podanego stołu
     */
    public void goToAssignedTable(Table table) {
        busy.release();
        inQueue = false;
        this.table = table;
        hasTable = true;
    }

    /**
     * Rysuje klienta
     */
    public void paint(Graphics g) {
        if (!visibility)
            return;

        super.paint(g);

        // rysowanie ilości klientów w grupie
        g.setColor(DataManager.textColor1);
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.drawString(String.valueOf(cardinality), x, y);
    }
}
