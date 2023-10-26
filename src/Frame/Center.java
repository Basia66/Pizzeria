package Frame;
import Classes.DataManager;
import People.Client;
import Elements.Tables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Center extends JPanel implements MouseListener {
    Pizzeria pizzeria;
    public Tables selectedTable;

    public Center(Pizzeria pizzeria){
        this.pizzeria = pizzeria;
        this.setBackground(Color.orange);

        this.addMouseListener(this);

        pizzeria.data.tables.add(new Tables(140,30,1, DataManager.tablesAmount[0]));
        pizzeria.data.tables.add(new Tables(540,30,2, DataManager.tablesAmount[1]));
        pizzeria.data.tables.add(new Tables(140,230,3, DataManager.tablesAmount[2]));
        pizzeria.data.tables.add(new Tables(540,230,4, DataManager.tablesAmount[3]));
    }

    @Override
    protected void paintComponent(Graphics g){
        g.setColor(Color.orange);
        g.fillRect(0,0,1000,1000);

        // rysownie krzeseł stolików
        g.drawImage(DataManager.chairImage, 60, 10, null);
        g.drawImage(DataManager.chairImage, 460,10, null);
        g.drawImage(DataManager.chairImage, 60,210, null);
        g.drawImage(DataManager.chairImage, 460,210, null);

        // rysowanie stolików
        for (Tables table : pizzeria.data.tables)
            table.paint(g);

        // rysownie queue
        g.drawImage(DataManager.doorImage, 800, 450, null);

        // rysowanie klientów
        try {
            for (Client client : pizzeria.data.clients) {
                client.paint(g);
            }
        } catch (Exception ignored){
            // wyjątek java.util.ConcurrentModificationException
            // opuszczamy jedną klatkę
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // sprawdzenie jaki stół został naciśniety
        if(x > 30 && x < 250 && y > 10 && y < 150) {
            selectedTable = pizzeria.data.tables.get(0);
            selectedTable.displayInformation();
        } else if(x > 450 && x < 800 && y > 10 && y < 150) {
            selectedTable = pizzeria.data.tables.get(1);
            selectedTable.displayInformation();
        } else if(x > 30 && x < 250 && y > 200 && y < 400) {
            selectedTable = pizzeria.data.tables.get(2);
            selectedTable.displayInformation();
        } else if(x > 450 && x < 800 && y > 200 && y < 400) {
            selectedTable = pizzeria.data.tables.get(3);
            selectedTable.displayInformation();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
