import java.util.Scanner;
/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Scanner main_scanner = new Scanner(System.in);
        Game new_game = new Game(main_scanner);
        new_game.start_game();
    }
}