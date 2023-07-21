import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class hw5 {
    public static void main(String[] args) {
        boolean progStat = true;

        File dataFile = new File("data.txt");

        Map<String, List<String>> phonebook;
        phonebook = loadBookFile(dataFile);

        while (progStat) {
            switch (mainMenu()) {
                case 1:
                    addContact(phonebook, inputNew());
                    save(dataFile, phonebook);
                    break;
                case 2:
                    printPhonebook(sortedBook(phonebook));
                    break;
                case 3:
                    System.out.println("Работа завершена");
                    progStat = false;
                    break;
                default:
                    System.out.println("Нет такого пункта в меню.");
            }
        }
    }

    public static Map<String, List<String>> loadBookFile(File dataFile) {
        Map<String, List<String>> phonebook = new HashMap<>();

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (dataFile.canRead()) {
            try {
                Scanner scanner = new Scanner(dataFile);
                while (scanner.hasNextLine()) {
                    addContact(phonebook, scanner.nextLine().split(":"));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("READ ERROR");
                throw new RuntimeException(e);
            }
        }
        return phonebook;
    }

    public static Integer mainMenu() {
        String menu = "\nСправочник.\n\nВыберите пункт меню:\n1 - Добавить запись\n2 - Отобразить данные\n3 - Завершить работу\n";
        Scanner select = new Scanner(System.in);

        System.out.print(menu);
        System.out.print("Выбор: ");
        String userInput = select.nextLine();
        while (!userInput.matches("[1-4]")) {
            System.out.print("Некорректный ввод. Попробуйте ещё раз: ");
            userInput = select.nextLine();
        }
        return Integer.parseInt(userInput);
    }

    public static void addContact(Map<String, List<String>> phonebook, String[] newRecord) {
        String name = newRecord[0];
        String phone = newRecord[1];

        if (phonebook.containsKey(name)) {
            phonebook.get(name).add(phone);
        } else {
            phonebook.put(name, new ArrayList<>(Collections.singletonList(phone)));
        }
    }

    public static String[] inputNew() {
        Scanner inputScan = new Scanner(System.in);

        System.out.print("Введи имя (ФИО): ");
        String name = inputScan.nextLine().strip();
        System.out.print("Введи номер телефона: ");
        String phone = inputScan.nextLine().strip();
        return new String[] { name, phone };
    }

    public static void save(File dataFile, Map<String, List<String>> phonebook) {
        if (dataFile.canWrite()) {
            try (FileWriter fw = new FileWriter(dataFile, false)) {
                for (var item : phonebook.entrySet()) {
                    String name = item.getKey();
                    List<String> phoneList = item.getValue();

                    for (int i = 0; i < phoneList.size(); i++) {
                        fw.write(name + ":");
                        fw.write(phoneList.get(i) + "\n");
                    }
                }
                fw.flush();
                System.out.println("Контакт добавлен!");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public static void printPhonebook(Map<String, List<String>> phonebook) {
        System.out.println("Список контактов.");
        for (var reccord : phonebook.entrySet()) {
            String name = reccord.getKey();
            List<String> phones = reccord.getValue();
            System.out.print("ФИО: " + name + "\nНомера: \n");

            for (int i = 0; i < phones.size(); i++) {
                System.out.println(phones.get(i));
            }
        }
    }

    public static LinkedHashMap<String, List<String>> sortedBook(Map<String, List<String>> phonebook) {
        int size = phonebook.size();
        HashMap<String, Integer> tempMap = new HashMap<>(size);

        for (var items : phonebook.entrySet()) {
            tempMap.put(items.getKey(), items.getValue().size());
        }

        LinkedHashMap<String, Integer> sortMap = tempMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new));

        LinkedHashMap<String, List<String>> sortedMap = new LinkedHashMap<>();

        for (var item : sortMap.entrySet()) {
            sortedMap.put(item.getKey(), phonebook.get(item.getKey()));
        }

        return sortedMap;
    }
}
