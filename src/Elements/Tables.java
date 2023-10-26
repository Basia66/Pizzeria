package Elements;

import Classes.DataManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tables {
    private final int x;
    private final int y;
    private final int tablesCount;

    /**
     * Typ stołu(1 osobowy, 2 osobowy)
     */
    public final int type;

    /**
     * Lista poszczególnych stołów
     */
    public final ArrayList<Table> list;

    public boolean has_pizza = false;

    public String informationString = "";
    public JLabel informationLabel = new JLabel("", JLabel.CENTER);

    public Tables(int x, int y, int type, int tablesCount) {
        this.x = x;
        this.y = y;
        this.tablesCount = tablesCount;

        this.type = type;
        list = new ArrayList<>();

        for (int i = 0; i < tablesCount; i++) {
            list.add(new Table(this, type));
        }
    }

    /**
     * Zwraca stół który jest dobry do umieszczenia w nim grupy o podanym cardinality
     */
    public synchronized Table getFreeTableForCardinality(int cardinality) {
        for (Table x : list) {
            // jeśli stolik jest pusty lub jest miejsce i cardinality się zgadza
            if (x.getActualCardinality() == 0 || x.isAvaiableForCardinality(cardinality)) {
                return x;
            }
        }

        return null;
    }

    /**
     * Przelicza czy ktoś jest przy stole by była wyświetlana pizza
     */
    public void recalcPizza() {
        for (Table x : list) {
            if (!x.groups.isEmpty()) {
                has_pizza = true;
                return;
            }
        }
        has_pizza = false;
    }

    /**
     * Generuje html wyświetlany w okienku statystyk stołów
     */
    private String genInformatioString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>" +
                "<table style='border: 1px solid #A8A8A8;'>" +
                "<tr>" +
                "<th style='border: 1px solid #A8A8A8;'>" +
                "LP." +
                "</th>" +
                "<th style='border: 1px solid #A8A8A8;'>" +
                "liczba miejsc:" +
                "</th>" +
                "<th style='border: 1px solid #A8A8A8;'>" +
                "liczba ludzi:" +
                "</th>" +
                "<th style='border: 1px solid #A8A8A8;'>" +
                "liczność grup:" +
                "</th>" +
                "</tr>");
        int i = 0;
        for (Table table : list) {
            sb.append("<tr>");

            sb.append("<td style='border: 1px solid #A8A8A8;'>");
            sb.append(i);
            sb.append("</td>");

            sb.append("<td style='border: 1px solid #A8A8A8;'>");
            sb.append(table.size);
            sb.append("</td>");

            sb.append("<td style='border: 1px solid #A8A8A8;'>");
            sb.append(table.groups.size() * table.getActualCardinality());
            sb.append("</td>");

            sb.append("<td style='border: 1px solid #A8A8A8;'>");
            sb.append(table.getActualCardinality());
            sb.append("</td>");

            i++;

            sb.append("</tr>");
        }

        sb.append("</table></html>");

        return informationString = sb.toString();
    }

    public void displayInformation() {
        informationLabel.setText(genInformatioString());
        JOptionPane.showMessageDialog(null, informationLabel, "Stoliki " + type + "-osobowe", JOptionPane.INFORMATION_MESSAGE);
    }

    public void refreshInformationLabel() {
        informationLabel.setText(genInformatioString());
    }

    /**
     * Rysuje stolik na panelu
     */
    public void paint(Graphics g) {
        // rysowanie stolika
        g.drawImage(DataManager.tableImage, x, y, null);

        // rysowanie pizzy
        if (has_pizza) {
            g.drawImage(DataManager.pizzaImage, x + 20, y, null);
            g.drawImage(DataManager.stickmanSittingImage, x - 30, y - 20, null);
        }

        // rysowanie napisu stolika
        g.setColor(Color.blue);
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.drawString("Stół " + type + "-osobowy", x + 100, y + 160);
        g.drawString("" + list.size(), x + 150, y + 130);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
