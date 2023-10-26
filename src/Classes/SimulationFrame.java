package Classes;

import Frame.Pizzeria;

import java.util.TimerTask;

/**
 * Klatka aplikacji(aktulanie wyświetlany obraz)
 */
public class SimulationFrame extends TimerTask {
    Pizzeria pizzeria;

    public SimulationFrame(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    /**
     * Metoda wywoływana co klatkę symulacji
     */
    @Override
    public void run() {
        // generuje nowych klientów
        pizzeria.genNewClient();

        // odświeża dane o stoliku
        if (pizzeria.center.selectedTable != null) {
            pizzeria.center.selectedTable.refreshInformationLabel();
        }

        // obsługuje generowanie się nowych klientów
        pizzeria.center.repaint();

        // przemalowuje wszystkie grafiki
//        pizzeria.repaint();
    }
}
