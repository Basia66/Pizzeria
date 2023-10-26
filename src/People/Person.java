package People;

import Classes.DataManager;
import Frame.Pizzeria;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Osoba to klasa posiadająca cechy wszystkich osób
 */
public class Person extends Thread {
    protected final Pizzeria pizzeria;

    protected int x;
    protected int y;

    /**
     * Czy osoba jest widoczna
     */
    protected boolean visibility;

    /**
     * Czy zostało wydane polecenie ruchu
     */
    protected boolean move_order = false;

    // Kordynaty w jakie ma się udać osoba
    protected int destination_x;
    protected int destination_y;

    // aktualnie wyświetlny obrazek osoby
    public BufferedImage image;

    public Person(Pizzeria pizzeria, int x, int y) {
        this.pizzeria = pizzeria;
        this.x = x;
        this.y = y;
        this.image = DataManager.stickmanStandImage;
        this.visibility = true;
        this.start();
    }

    public void setImageStand() {
        this.image = DataManager.stickmanStandImage;
    }

    public void setImagePizza() {
        this.image = DataManager.stickmanPizzaImage;
    }

    public void setImageHappy() {
        this.image = DataManager.stickmanHappyImage;
    }

    public void hide() {
        this.visibility = false;
    }

    public void show() {
        this.visibility = true;
    }

    /**
     * Opuszcza kilka milisekund by animacja wyglądała na płynność
     */
    public void sleep_anim() {
        try {
            while(DataManager.speed == 0) {
                sleep(100);
            }
            sleep((long) (DataManager.FPS / DataManager.speed));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rozkazuje klientowi przemieszczenie się na wskazane koordynaty
     */
    public void goTo(int x, int y) {
        destination_x = x;
        destination_y = y;
        move_order = true;
    }

    /**
     * Przesuwa(natychmistowo) osobę na wyznaczone miejsce
     */
    public void translate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Wykonuje translacje wraz z upływającym czasem
     */
    protected void translate_delta_time(int x, int y) {
        int x_change = this.x < x ? DataManager.move_range : -DataManager.move_range;
        int y_change = this.y < y ? DataManager.move_range : -DataManager.move_range;

        boolean x_reached = this.x == x;
        boolean y_reached = this.y == y;
        while (!x_reached || !y_reached) {
            // porusza grupą klientów
            if (!x_reached){
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
    }

    public void paint(Graphics g) {
        if (!visibility)
            return;

        // rysowanie obrazka osoby
        g.drawImage(image, x, y, null);
    }
}
