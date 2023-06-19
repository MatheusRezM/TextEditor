import java.io.*;
import java.util.*;

public class TextEditor1 {
    private Set<String> dictionary;
    private String dictionaryFilePath;

    public TextEditor1() {
        dictionary = new HashSet<>();
        dictionaryFilePath = "C:/Users/mathe/OneDrive/Java/aaa/src/dictionary.txt";
        loadDictionary();
    }

    private void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                dictionary.add(word.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o dicionário: " + e.getMessage());
        }
    }

    private void saveDictionary() {
        String tempFilePath = "C:/Users/mathe/OneDrive/Java/aaa/src/dictionary.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath))) {
            for (String word : dictionary) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o dicionário temporário: " + e.getMessage());
        }
       
    }

    private boolean isWordInDictionary(String word) {
        String lowercaseWord = word.toLowerCase();
        for (String dictWord : dictionary) {
            if (dictWord.equalsIgnoreCase(lowercaseWord)) {
                return true;
            }
        }
        return false;
    }

    private void addWordToDictionary(String word) {
        dictionary.add(word.toLowerCase());
        saveDictionary();
    }

    public void editFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                processLine(line, lineNumber);
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
    }

    private void processLine(String line, int lineNumber) {
        String[] words = line.split("\\s+");

        for (String word : words) {
            String cleanedWord = word.replaceAll("[^a-zA-ZÀ-ÿ]", "");

            if (!isWordInDictionary(cleanedWord)) {
                System.out.println("Palavra inválida na linha " + lineNumber + ": " + word);
                System.out.print("Deseja adicionar a palavra ao dicionário? (S/N): ");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine().trim().toLowerCase();

                if (choice.equals("s")) {
                   addWordToDictionary(word);
                }
            }
        }
    }

    public static void main(String[] args) {
        TextEditor1 editor = new TextEditor1();

        // Exemplo de edição de um novo arquivo
        editor.editFile("C:/Users/mathe/OneDrive/Java/aaa/src/novo_arquivo.txt");

        // Exemplo de edição de um arquivo existente
        editor.editFile("C:/Users/mathe/OneDrive/Java/aaa/src/arquivo_existente.txt");
    }
}
