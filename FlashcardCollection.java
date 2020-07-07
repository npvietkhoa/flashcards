package flashcards;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

/**
 * FlashcardCollection
 */
public class FlashcardCollection {

    private final HashMap<String, String> flashcard_collection = new HashMap<>();

    protected int get_size() {
        return flashcard_collection.size();
    }

    protected void add_card(String term, String definition) {
        this.flashcard_collection.put(term, definition);
    }

    protected boolean contains_card(String term) {
        return this.flashcard_collection.containsKey(term);
    }

    protected boolean contains_definition(String definition) {
        return this.flashcard_collection.containsValue(definition);
    }

    protected void remove_card(String term) {
        if (!this.flashcard_collection.containsKey(term)) {
            System.out.println("Can't remove \"" + term + "\": there is no such card.");
            return;
        }

        this.flashcard_collection.remove(term);
        System.out.println("The card has been removed.");
    }

    protected String get_term_of(String definition) {
        for (var entry : flashcard_collection.entrySet()) {
            if (entry.getValue().toLowerCase().equals(definition)) {
                return entry.getKey();
            }
        }
        return null;
    }

    protected String get_definition_of(String term) {
        return flashcard_collection.get(term);
    }

    protected String[] get_terms_in_array() {
        String[] terms_in_array = new String[flashcard_collection.size()];
        this.flashcard_collection.keySet().toArray(terms_in_array);
        return terms_in_array;
    }

    protected void export_collection_to(File export_file) {
        try {
            FileWriter file_writer = new FileWriter(export_file);
            for (var entry : flashcard_collection.entrySet()) {
                file_writer.write(entry.getKey() + ":" + entry.getValue());
                file_writer.write("\n");
            }
            System.out.println(this.flashcard_collection.size() + " cards have been saved.");
            file_writer.close();
        } catch (Exception e) {
            //TODO: handle exception
            e.getMessage();
        }
    }

    protected void import_all_cards_from(File import_file) {
        if (!import_file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try {
            Scanner file_scanner = new Scanner(import_file);
            int number_of_card_in_file = 0;
            String current_line;
            String[] current_pair;
            String term_of_current_pair;
            String definition_of_current_pair;

            while (file_scanner.hasNextLine()) {
                current_line = file_scanner.nextLine();
                current_pair = current_line.split(":");
                term_of_current_pair = current_pair[0];
                definition_of_current_pair = current_pair[1];
                this.flashcard_collection.put(term_of_current_pair, definition_of_current_pair);
                number_of_card_in_file += 1;
            }
            file_scanner.close();
            System.out.println(number_of_card_in_file + " cards have been loaded.");
        } catch (Exception e) {
            //TODO: handle exception
            e.getMessage();
        }
    }
}