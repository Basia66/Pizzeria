package Frame;

import Classes.DataManager;
import Classes.SimulationFrame;
import Classes.TablesManager;
import Elements.QueueRoom;
import People.Client;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

/**
 * Główny obiekt symulacji czyli okno
 */
public class Pizzeria extends JFrame {
    public final Center center;

    public final DataManager data;
    public TablesManager tablesManager;

    Timer timer;

    public Pizzeria() {
        data = new DataManager();
        tablesManager = new TablesManager(this);

        setTitle("Pizzeria");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 800));
        setMinimumSize(new Dimension(900, 800));
        setResizable(false);

        BorderLayout borderLayout = new BorderLayout();
        Top top = new Top(this);
        top.setPreferredSize(new Dimension(900, 150));
        center = new Center(this);
        center.setPreferredSize(new Dimension(900, 500));

        borderLayout.preferredLayoutSize(this);
        setLayout(borderLayout);

        add(top);
        add(center);

        borderLayout.addLayoutComponent(top, BorderLayout.NORTH);
        borderLayout.addLayoutComponent(center, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        scheduleFrameUpdate();
    }

    /**
     * Dodaje nowego klienta jeśli się taki wylosuje
     */
    public void genNewClient() {
        // 100000 żeby zmniejszyć prawdopodobieństwo
        if (data.clients.size() < DataManager.maxClients &&
                data.queue.list.size() < DataManager.queueMaxSlots &&
                DataManager.newClientProbability * 10 > new Random().nextInt(500)
        ) {
            Client c = new Client(this, DataManager.enterAndExitCords[0], DataManager.enterAndExitCords[1]);
            addNewClient(c);
            c.show();
            c.QueueClient(data.queue.queueSeatX, QueueRoom.queueSeatY);
        }
    }

    /**
     * Ustawia plan odświeżania ramek
     */
    public void scheduleFrameUpdate() {
        // nie kolejkuj silnika zmian klatek jeśli szubkość czasu to 0
        if(DataManager.speed == 0)
            return;

        timer = new Timer();
        timer.schedule(new SimulationFrame(this), 0, (long) (DataManager.frameDelay / DataManager.speed));
    }

    /**
     * Dodaje nowego klienta do pizzerii
     */
    public void addNewClient(Client client) {
        data.clients.add(client);
        data.queue.add(client);
    }
}
