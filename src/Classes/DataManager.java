package Classes;

import Elements.QueueRoom;
import Elements.Tables;
import People.Client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Klasa zawierająca dane aplikacji
 */
public class DataManager {

    /**
     * Liczba stolików
     * a - 1 osobowy
     * b - 2 osobowy
     * c - 3 osobowy
     * d - 4 osobowy
     * {a, b, c, d}
     */
    public static int[] tablesAmount = {6, 4, 4, 3};

    /**
     * Liczba GRUP jakie mogą stać w kolejce
     */
    public static int queueMaxSlots = 15;

    /**
     * Maksymalna liczba ludzi w grupie jacy mogą przychodzić co pizzerii
     * (maxymalna pojemność grupy)
     */
    public static int groupMaxAmount = 3;

    /**
     * Ilość milisekund co jakie zostanie wyrysowna nowa klatka symulacji
     * FPS - liczba klatek
     * 1000 / 33 ~ 30 klatek na sec
     */
    public static int frameDelay = 33;
    public static int FPS = (int) Math.floor((double) 1000 / frameDelay);

    public static double MAX_SPEED = 30F;
    public static double MIN_SPEED = 0F;

    /**
     * Szybkość animacji
     * UWAGA przyśpieszanie sprawia powiększenie fps
     */
    public static double speed = 10f;

    /**
     * Ilość pikseli jaką przebędzie klient w jednostce czasu
     */
    public static int move_range = 1;

    /**
     * Czas w milisekundach w jakim zjadana jest pizza
     */
    public static double eatingTime = 50000;

    /**
     * Prawdopodobieństwo wygenerowania nowych klientów 0-1
     */
    public static float newClientProbability = 0.2f;

    /**
     * Maksymalna liczba działających wątków
     */
    public static int maxClients = 30;

    /**
     * Miesce odstępu w px w kolejce
     */
    public static int queueInterspace = 50;

    /**
     * Koordynaty drzwi
     */
    public static int[] enterAndExitCords = {850, 450};

    // kolorki
    public static Color textColor1 = Color.blue;

    // obrazki
    public static BufferedImage chairImage = Helper.loadImage("chair.png");
    public static BufferedImage doorImage = Helper.loadImage("door.png");

    public static BufferedImage stickmanStandImage = Helper.loadImage("stickman_stand.png");
    public static BufferedImage stickmanPizzaImage = Helper.loadImage("stickman_pizza.png");
    public static BufferedImage stickmanSittingImage = Helper.loadImage("stickman_sitting.png");
    public static BufferedImage stickmanHappyImage = Helper.loadImage("stickman_happy.png");

    public static BufferedImage tableImage = Helper.loadImage("table.png");
    public static BufferedImage pizzaImage = Helper.loadImage("pizza.png");

    // niestatyczne dane aplikacji
    public final ArrayList<Client> clients = new ArrayList<>();
    public final QueueRoom queue = new QueueRoom();
    public final ArrayList<Tables> tables = new ArrayList<>();
}
