package Elements;

import Classes.DataManager;
import People.Client;

import java.util.LinkedList;

public class QueueRoom {
    public LinkedList<Client> list = new LinkedList<>();
    public int queueSeatX = 0;
    public static int queueSeatY = 450;

    /**
     * Przemieszcza kolejkę
     */
    public void moveQueue() {
        for (int i = 0; i < list.size(); i++) {
            try {
                list.get(i).moveInQueue(i);
            } catch (Exception ignored) {
                // źle zaaktualizowana lista
            }
        }
    }

    /**
     * Dodaje nowego klienta
     */
    public void add(Client client) {
        client.queueIndex = list.size();
        list.add(client);
        moveQueue();
        queueSeatX += DataManager.queueInterspace;
    }

    /**
     * Dodaje klienta na poszczególne miejsce w liśsie
     */
    public void insertAt(Client client, int index) {
        if(index > list.size()-1) {
            try {
                add(client);
            } catch (Exception e) {
                list.add(index, client);
            }
        } else {
            list.add(index, client);
        }

        moveQueue();
        queueSeatX += DataManager.queueInterspace;
    }

    /**
     * Usuwa pierwszego klienta w kolejce
     */
    public void remove(Client client) {
        queueSeatX -= DataManager.queueInterspace;
        moveQueue();
        list.remove(client);
    }
}
