package flashcards;

import flashcards.FlashcardCollection;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Scanner scanner;
    
    private FlashcardCollection card_collection = new FlashcardCollection();

    Game(Scanner _Scanner) {
        this.scanner = _Scanner;
    }

    private void manual_add() {
        System.out.println("The card: ");
        String term = scanner.nextLine();
        if (card_collection.contains_card(term)) {
            System.out.println("The card \"" + term + "\" already exists.");
            return;
        }

        System.out.println("The definition of card: ");
        String definition = scanner.nextLine();

        if (card_collection.contains_definition(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            return;
        }

        card_collection.add_card(term, definition);

        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }
    
    private void import_cards_from_file() {
        System.out.println("File name: ");
        String file_path = scanner.nextLine();
        File import_File = new File(file_path);
        card_collection.import_all_cards_from(import_File); 
    }
    
    private void remove_card() {
        System.out.println("The card: ");
        String card_to_remove = scanner.nextLine();
        card_collection.remove_card(card_to_remove);
    }

    private void export_card_collection() {
        System.out.println("File name: ");
        String export_file_path = scanner.nextLine();
        File export_file = new File(export_file_path);
        card_collection.export_collection_to(export_file); 
    }

    private void check_user_guess(String guessed_definition, String correct_defition) {
        guessed_definition = guessed_definition.trim().toLowerCase();
        String correct_defition_lowercase = correct_defition.toLowerCase();
        
        if (correct_defition_lowercase.equals(guessed_definition)) {
            System.out.println("Correct answer.");
        } else {
            if (card_collection.get_term_of(guessed_definition) != null) {
                System.out.println("Wrong answer, The correct one is \"" + correct_defition + 
                                    "\", you've just written the definition of \"" + 
                                    card_collection.get_term_of(guessed_definition) + "\".");
                return;
            }
            System.out.println("Wrong answer. The correct one is \"" + correct_defition + "\".");
        }
    }

    private void ask_user() throws NumberFormatException {
        System.out.println("How many time to ask: ");
        Integer time_to_ask = Integer.parseInt(scanner.nextLine());
        String[] terms_in_array = card_collection.get_terms_in_array().clone();
        int random_index = 0;
        String random_term;
        String definition_of_random_term;
        String guessed_definition;

        for (int i = 0; i < time_to_ask; i++) {
            random_index = new Random().nextInt(terms_in_array.length);
            random_term = terms_in_array[random_index];
            definition_of_random_term = card_collection.get_definition_of(random_term);
            System.out.println("Print the definition of \"" + random_term + "\":");
            guessed_definition = scanner.nextLine();

            check_user_guess(guessed_definition, definition_of_random_term);
        }
    }
    
    protected void start_game() {
        String user_option;
        boolean do_exit = false;
        while (!do_exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit): ");
            user_option = scanner.nextLine();
            switch (user_option) {
                case "add":
                    manual_add();       
                    break;
                case "remove":
                    remove_card();
                    break;
                case "import":
                    import_cards_from_file();
                    break;
                case "export":
                    export_card_collection();
                    break;
                case "ask":
                    ask_user();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    do_exit = true;
                    break;
            }
        }
    }
}