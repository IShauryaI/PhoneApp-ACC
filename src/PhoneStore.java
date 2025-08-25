import java.util.*;
import java.util.regex.*;
import java.io.*;

public class PhoneStore {

    // Our dummy database of phones
    static List<Phone> phones = new ArrayList<>();
    // To track search frequency by phone (brand + model)
    static Map<String, Integer> searchFrequency = new HashMap<>();
    // Dictionary for spell checking / word completion
    static Set<String> dictionary = new HashSet<>();

    public static void main(String[] args) {
        initPhones();
        initDictionary();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            printMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch(Exception e) {
                choice = 0;
            }
            switch(choice) {
                case 1:
                    searchPhones(scanner);
                    break;
                case 2:
                    viewFeatures(scanner);
                    break;
                case 3:
                    viewPhones(scanner);
                    break;
                case 4:
                    showMostSearchedPhones();
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }

    // Prints the main menu
    static void printMenu() {
        System.out.println("\n==== Phone Store Menu ====");
        System.out.println("1. Search the phones");
        System.out.println("2. View all the features");
        System.out.println("3. View all the Phones");
        System.out.println("4. Most searched Phones");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // Initialize the phone database from the dummy data
    static void initPhones() {
        phones.add(new Phone(1, "Apple", "iPhone", 499, "2007-06-29",
                new Specs("1400 mAh", "2 MP", "N/A", "128 MB", "3.5 inches", "LCD", "Bluetooth 2.0")));
        phones.add(new Phone(2, "Apple", "iPhone 3G", 199, "2008-07-11",
                new Specs("1150 mAh", "2 MP", "N/A", "128 MB", "3.5 inches", "LCD", "Bluetooth 2.0")));
        phones.add(new Phone(3, "Apple", "iPhone 4", 499, "2010-06-24",
                new Specs("1420 mAh", "5 MP", "N/A", "512 MB", "3.5 inches", "Retina LCD", "Bluetooth 2.1")));
        phones.add(new Phone(4, "Apple", "iPhone 5", 649, "2012-09-21",
                new Specs("1440 mAh", "8 MP", "N/A", "1 GB", "4 inches", "Retina LCD", "Bluetooth 4.0")));
        phones.add(new Phone(5, "Apple", "iPhone 6", 649, "2014-09-19",
                new Specs("1810 mAh", "8 MP", "N/A", "1 GB", "4.7 inches", "LCD", "Bluetooth 4.0")));
        phones.add(new Phone(6, "Apple", "iPhone 6S", 649, "2015-09-25",
                new Specs("1715 mAh", "12 MP", "N/A", "2 GB", "4.7 inches", "LCD", "Bluetooth 4.2")));
        phones.add(new Phone(7, "Apple", "iPhone 7", 649, "2016-09-16",
                new Specs("1960 mAh", "12 MP", "IP67", "2 GB", "4.7 inches", "LCD", "Bluetooth 4.2")));
        phones.add(new Phone(8, "Apple", "iPhone 8", 699, "2017-09-22",
                new Specs("1821 mAh", "12 MP", "IP67", "2 GB", "4.7 inches", "Retina LCD", "Bluetooth 5.0")));
        phones.add(new Phone(9, "Apple", "iPhone X", 999, "2017-11-03",
                new Specs("2716 mAh", "12 MP dual", "IP67", "3 GB", "5.8 inches", "Super Retina OLED", "Bluetooth 5.0")));
        phones.add(new Phone(10, "Apple", "iPhone 11", 699, "2019-09-20",
                new Specs("3110 mAh", "Dual 12 MP", "IP68", "4 GB", "6.1 inches", "Liquid Retina LCD", "Bluetooth 5.0")));
        phones.add(new Phone(11, "Apple", "iPhone SE (2nd Gen)", 399, "2020-04-24",
                new Specs("1821 mAh", "12 MP", "IP67", "3 GB", "4.7 inches", "Retina LCD", "Bluetooth 5.0")));
        phones.add(new Phone(12, "Apple", "iPhone 12", 799, "2020-10-23",
                new Specs("2815 mAh", "Dual 12 MP", "IP68", "4 GB", "6.1 inches", "Super Retina XDR OLED", "Bluetooth 5.0")));
        phones.add(new Phone(13, "Apple", "iPhone 13", 799, "2021-09-24",
                new Specs("3240 mAh", "Dual 12 MP", "IP68", "4 GB", "6.1 inches", "Super Retina XDR OLED", "Bluetooth 5.0")));
        phones.add(new Phone(14, "Apple", "iPhone 14", 799, "2022-09-16",
                new Specs("3279 mAh", "Dual 12 MP", "IP68", "4 GB", "6.1 inches", "Super Retina XDR OLED", "Bluetooth 5.0")));
        phones.add(new Phone(15, "Google", "Nexus One", 299, "2010-01-05",
                new Specs("1400 mAh", "5 MP", "N/A", "512 MB", "3.7 inches", "Super AMOLED", "Bluetooth 2.1")));
        phones.add(new Phone(16, "Google", "Nexus 4", 349, "2012-11-13",
                new Specs("2100 mAh", "8 MP", "N/A", "2 GB", "4.7 inches", "LCD", "Bluetooth 4.0")));
        phones.add(new Phone(17, "Google", "Nexus 5", 349, "2013-10-31",
                new Specs("2300 mAh", "8 MP", "N/A", "2 GB", "4.95 inches", "LCD", "Bluetooth 4.0")));
        phones.add(new Phone(18, "Google", "Nexus 6", 649, "2014-11-05",
                new Specs("3220 mAh", "13 MP", "N/A", "3 GB", "5.96 inches", "LCD", "Bluetooth 4.1")));
        phones.add(new Phone(19, "Google", "Pixel", 649, "2016-10-04",
                new Specs("2770 mAh", "12.3 MP", "IP68", "4 GB", "5.0 inches", "OLED", "Bluetooth 4.2")));
        phones.add(new Phone(20, "Google", "Pixel 3", 699, "2018-10-18",
                new Specs("2915 mAh", "12.2 MP", "IP68", "4 GB", "5.5 inches", "OLED", "Bluetooth 5.0")));
        phones.add(new Phone(21, "Google", "Pixel 4a", 349, "2020-08-20",
                new Specs("3140 mAh", "12.2 MP", "N/A", "6 GB", "5.8 inches", "OLED", "Bluetooth 5.0")));
        phones.add(new Phone(22, "Google", "Pixel 5", 699, "2020-10-15",
                new Specs("4080 mAh", "12.2 MP", "IP68", "8 GB", "6.0 inches", "OLED", "Bluetooth 5.0")));
        phones.add(new Phone(23, "Google", "Pixel 6", 599, "2021-10-28",
                new Specs("4614 mAh", "50 MP", "IP68", "8 GB", "6.4 inches", "OLED", "Bluetooth 5.2")));
        phones.add(new Phone(24, "Google", "Pixel 7", 699, "2022-10-13",
                new Specs("4355 mAh", "50 MP dual", "IP68", "8 GB", "6.3 inches", "OLED", "Bluetooth 5.2")));
        phones.add(new Phone(25, "OnePlus", "OnePlus One", 299, "2014-04-23",
                new Specs("3100 mAh", "13 MP", "N/A", "2 GB", "5.5 inches", "LCD", "Bluetooth 4.0")));
        phones.add(new Phone(26, "OnePlus", "OnePlus 3", 399, "2016-06-16",
                new Specs("3000 mAh", "16 MP", "N/A", "6 GB", "5.5 inches", "AMOLED", "Bluetooth 4.2")));
        phones.add(new Phone(27, "OnePlus", "OnePlus 5", 449, "2017-06-20",
                new Specs("3300 mAh", "16 MP", "N/A", "6 GB", "5.5 inches", "Optic AMOLED", "Bluetooth 5.0")));
        phones.add(new Phone(28, "OnePlus", "OnePlus 6T", 549, "2018-11-06",
                new Specs("3700 mAh", "16 MP", "N/A", "6 GB", "6.4 inches", "AMOLED", "Bluetooth 5.0")));
        phones.add(new Phone(29, "OnePlus", "OnePlus 7T", 649, "2019-10-29",
                new Specs("3800 mAh", "48 MP", "N/A", "8 GB", "6.55 inches", "Fluid AMOLED", "Bluetooth 5.0")));
        phones.add(new Phone(30, "OnePlus", "OnePlus 8", 699, "2020-04-14",
                new Specs("4300 mAh", "48 MP", "N/A", "8 GB", "6.1 inches", "Fluid AMOLED", "Bluetooth 5.1")));
        phones.add(new Phone(31, "OnePlus", "OnePlus 9", 729, "2021-03-23",
                new Specs("4500 mAh", "48 MP triple", "N/A", "8 GB", "6.55 inches", "Fluid AMOLED", "Bluetooth 5.2")));
        phones.add(new Phone(32, "OnePlus", "OnePlus 10 Pro", 899, "2022-01-11",
                new Specs("5000 mAh", "48 MP quad", "IP68", "12 GB", "6.7 inches", "AMOLED", "Bluetooth 5.2")));
        phones.add(new Phone(33, "Samsung", "Galaxy S", 399, "2010-06-04",
                new Specs("1500 mAh", "5 MP", "N/A", "512 MB", "4.0 inches", "Super AMOLED", "Bluetooth 2.1")));
        phones.add(new Phone(34, "Samsung", "Galaxy S III", 599, "2012-05-29",
                new Specs("2100 mAh", "8 MP", "N/A", "1 GB", "4.8 inches", "Super AMOLED", "Bluetooth 4.0")));
        phones.add(new Phone(35, "Samsung", "Galaxy S4", 649, "2013-04-27",
                new Specs("2600 mAh", "13 MP", "N/A", "2 GB", "5.0 inches", "Super AMOLED", "Bluetooth 4.0")));
        phones.add(new Phone(36, "Samsung", "Galaxy S6", 749, "2015-04-10",
                new Specs("2550 mAh", "16 MP", "IP68", "3 GB", "5.1 inches", "Super AMOLED", "Bluetooth 4.1")));
        phones.add(new Phone(37, "Samsung", "Galaxy S7", 749, "2016-03-11",
                new Specs("3600 mAh", "12 MP", "IP68", "4 GB", "5.1 inches", "Super AMOLED", "Bluetooth 4.2")));
        phones.add(new Phone(38, "Samsung", "Galaxy S9", 719, "2018-03-16",
                new Specs("3000 mAh", "12 MP", "IP68", "4 GB", "5.8 inches", "Super AMOLED", "Bluetooth 5.0")));
        phones.add(new Phone(39, "Samsung", "Galaxy S10", 899, "2019-03-08",
                new Specs("3400 mAh", "Triple camera", "IP68", "8 GB", "6.1 inches", "Dynamic AMOLED", "Bluetooth 5.0")));
        phones.add(new Phone(40, "Samsung", "Galaxy S20", 999, "2020-03-06",
                new Specs("4000 mAh", "Triple camera", "IP68", "12 GB", "6.2 inches", "Dynamic AMOLED", "Bluetooth 5.1")));
        phones.add(new Phone(41, "Samsung", "Galaxy S21", 799, "2021-01-29",
                new Specs("4000 mAh", "Triple camera", "IP68", "8 GB", "6.2 inches", "Dynamic AMOLED", "Bluetooth 5.2")));
        phones.add(new Phone(42, "Samsung", "Galaxy S22", 799, "2022-02-09",
                new Specs("3700 mAh", "Triple camera", "IP68", "8 GB", "6.1 inches", "Dynamic AMOLED", "Bluetooth 5.2")));
        phones.add(new Phone(43, "Samsung", "Galaxy S23", 799, "2023-02-17",
                new Specs("3900 mAh", "Triple camera", "IP68", "8 GB", "6.1 inches", "Dynamic AMOLED", "Bluetooth 5.2")));
        phones.add(new Phone(44, "Samsung", "Galaxy Note 20", 999, "2020-08-21",
                new Specs("4300 mAh", "Triple camera", "IP68", "8 GB", "6.7 inches", "Dynamic AMOLED", "Bluetooth 5.1")));
    }

    // Initialize a simple dictionary for spell checking/word completion
    static void initDictionary() {
        // Add some common brands, models, and feature names
        dictionary.add("Apple");
        dictionary.add("Google");
        dictionary.add("OnePlus");
        dictionary.add("Samsung");
        dictionary.add("iPhone");
        dictionary.add("Nexus");
        dictionary.add("Pixel");
        dictionary.add("Galaxy");
        dictionary.add("Note");
        dictionary.add("battery");
        dictionary.add("camera");
        dictionary.add("ipRating");
        dictionary.add("ram");
        dictionary.add("displaySize");
        dictionary.add("displayType");
        dictionary.add("bluetooth");
    }

    // Option 1: Search phones by name or by a feature (sorted descending)
    static void searchPhones(Scanner scanner) {
        System.out.print("Enter phone name or feature to search: ");
        String query = scanner.nextLine().trim();

        // If query is not in our dictionary, try to suggest a correction.
        if (!dictionary.contains(query)) {
            String suggestion = SpellChecker.getClosestWord(query, new ArrayList<>(dictionary));
            if (suggestion != null) {
                System.out.println("Did you mean: " + suggestion + "?");
            }
        }

        // If query matches a feature name, sort by that feature (highest to lowest)
        if (isFeature(query)) {
            List<Phone> sorted = new ArrayList<>(phones);
            Collections.sort(sorted, (p1, p2) -> {
                double v1 = parseFeatureValue(p1.specs.getFeature(query));
                double v2 = parseFeatureValue(p2.specs.getFeature(query));
                return Double.compare(v2, v1);
            });
            System.out.println("Phones sorted by " + query + " (highest to lowest):");
            for (Phone p : sorted) {
                System.out.println(p.brand + " " + p.model + " - " + p.specs.getFeature(query));
            }
        } else {
            // Otherwise, search by phone name (brand or model) using token-based matching
            List<Phone> results = new ArrayList<>();
            String[] tokens = query.toLowerCase().split("\\s+");
            for (Phone p : phones) {
                String combined = (p.brand + " " + p.model).toLowerCase();
                boolean match = true;
                for (String token : tokens) {
                    if (!combined.contains(token)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    results.add(p);
                    // update search frequency count
                    String key = p.brand + " " + p.model;
                    searchFrequency.put(key, searchFrequency.getOrDefault(key, 0) + 1);
                }
            }
            if (results.isEmpty()) {
                // If no match, try word completion suggestions
                List<String> completions = WordCompletion.getCompletions(query, new ArrayList<>(dictionary));
                if (!completions.isEmpty()) {
                    System.out.println("No direct match found. Did you mean: " + completions);
                } else {
                    System.out.println("No results found.");
                }
            } else if (results.size() == 1) {
                // Only one result found, display its details directly
                Phone selected = results.get(0);
                displayPhoneDetailsAndBuyOption(scanner, selected);
            } else {
                // Multiple results found, list them and let user choose
                System.out.println("Search results:");
                int index = 1;
                for (Phone p : results) {
                    System.out.println(index + ". " + p.brand + " " + p.model);
                    index++;
                }
                System.out.print("Select a phone by number: ");
                int selection = Integer.parseInt(scanner.nextLine().trim());
                if (selection < 1 || selection > results.size()) {
                    System.out.println("Invalid selection.");
                    return;
                }
                Phone selected = results.get(selection - 1);
                displayPhoneDetailsAndBuyOption(scanner, selected);
            }
        }
    }

    // Option 2: List available features and then sort phones by the selected feature
    static void viewFeatures(Scanner scanner) {
        System.out.println("Available features:");
        System.out.println("1. battery");
        System.out.println("2. camera");
        System.out.println("3. ipRating");
        System.out.println("4. ram");
        System.out.println("5. displaySize");
        System.out.println("6. displayType");
        System.out.println("7. bluetooth");
        System.out.print("Select a feature (enter number): ");
        String choice = scanner.nextLine().trim();
        String feature = "";
        switch(choice) {
            case "1": feature = "battery"; break;
            case "2": feature = "camera"; break;
            case "3": feature = "ipRating"; break;
            case "4": feature = "ram"; break;
            case "5": feature = "displaySize"; break;
            case "6": feature = "displayType"; break;
            case "7": feature = "bluetooth"; break;
            default: System.out.println("Invalid choice."); return;
        }
        List<Phone> sorted = new ArrayList<>(phones);
        String finalFeature = feature;
        Collections.sort(sorted, (p1, p2) -> {
            double v1 = parseFeatureValue(p1.specs.getFeature(finalFeature));
            double v2 = parseFeatureValue(p2.specs.getFeature(finalFeature));
            return Double.compare(v2, v1);
        });
        System.out.println("Phones sorted by " + feature + " (highest to lowest):");
        for (Phone p : sorted) {
            System.out.println(p.brand + " " + p.model + " - " + p.specs.getFeature(feature));
        }
    }

    // Option 3: View phones by brand then select one to see details and “buy” it
    static void viewPhones(Scanner scanner) {
        System.out.print("Select a brand to view : \n");
        System.out.print("Apple\n");
        System.out.print("Google\n");
        System.out.print("Samsung\n");
        System.out.print("Oneplus\n");

        String query = scanner.nextLine().trim();
        List<Phone> results = new ArrayList<>();
        for (Phone p : phones) {
            if (p.brand.equalsIgnoreCase(query)) {
                results.add(p);
                // update frequency
                String key = p.brand + " " + p.model;
                searchFrequency.put(key, searchFrequency.getOrDefault(key, 0) + 1);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No phones found for brand: " + query);
            return;
        }
        System.out.println("Phones from " + query + ":");
        int index = 1;
        for (Phone p : results) {
            System.out.println(index + ". " + p.brand + " " + p.model);
            index++;
        }
        System.out.print("Select a phone by number: ");
        int selection = Integer.parseInt(scanner.nextLine().trim());
        if (selection < 1 || selection > results.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Phone selected = results.get(selection - 1);
        System.out.println("\n=== Phone Details ===");
        System.out.println("Brand: " + selected.brand);
        System.out.println("Model: " + selected.model);
        System.out.println("Price: $" + selected.price);
        System.out.println("Release Date: " + selected.releaseDate);
        System.out.println("Battery: " + selected.specs.battery);
        System.out.println("Camera: " + selected.specs.camera);
        System.out.println("IP Rating: " + selected.specs.ipRating);
        System.out.println("RAM: " + selected.specs.ram);
        System.out.println("Display Size: " + selected.specs.displaySize);
        System.out.println("Display Type: " + selected.specs.displayType);
        System.out.println("Bluetooth: " + selected.specs.bluetooth);
        System.out.print("\nWould you like to buy this phone? (Y/N): ");
        String buy = scanner.nextLine().trim();
        if (buy.equalsIgnoreCase("Y")) {
            // Use TextAnalyser (dummy scraping) to display contact details
            List<String> emails = TextAnalyser.getEmails("https://www.apple.com"); // dummy website
            List<String> phonesScraped = TextAnalyser.getPhoneNumbers("https://www.apple.com");
            List<String> urls = TextAnalyser.getURLs("https://www.apple.com");
            System.out.println("\nContact details from website:");
            System.out.println("Emails: " + emails);
            System.out.println("Phone Numbers: " + phonesScraped);
            System.out.println("URLs: " + urls);
        }
    }

    // New helper method to display phone details and offer buy option
    static void displayPhoneDetailsAndBuyOption(Scanner scanner, Phone selected) {
        System.out.println("\n=== Phone Details ===");
        System.out.println("Brand: " + selected.brand);
        System.out.println("Model: " + selected.model);
        System.out.println("Price: $" + selected.price);
        System.out.println("Release Date: " + selected.releaseDate);
        System.out.println("Battery: " + selected.specs.battery);
        System.out.println("Camera: " + selected.specs.camera);
        System.out.println("IP Rating: " + selected.specs.ipRating);
        System.out.println("RAM: " + selected.specs.ram);
        System.out.println("Display Size: " + selected.specs.displaySize);
        System.out.println("Display Type: " + selected.specs.displayType);
        System.out.println("Bluetooth: " + selected.specs.bluetooth);
        System.out.print("\nWould you like to buy this phone? (Y/N): ");
        String buy = scanner.nextLine().trim();
        if (buy.equalsIgnoreCase("Y")) {
            // Use TextAnalyser (dummy scraping) to display contact details
            List<String> emails = TextAnalyser.getEmails("https://www.apple.com");
            List<String> phonesScraped = TextAnalyser.getPhoneNumbers("https://www.apple.com");
            List<String> urls = TextAnalyser.getURLs("https://www.apple.com");
            System.out.println("\nContact details from website:");
            System.out.println("Emails: " + emails);
            System.out.println("Phone Numbers: " + phonesScraped);
            System.out.println("URLs: " + urls);
        }
    }
    // Option 4: Display the most searched phones based on search frequency
    static void showMostSearchedPhones() {
        if (searchFrequency.isEmpty()) {
            System.out.println("No searches recorded yet.");
            return;
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(searchFrequency.entrySet());
        Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        System.out.println("Most searched phones:");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " searches");
        }
    }

    // Helper: Determines if a given query is a feature name.
    static boolean isFeature(String query) {
        String lower = query.toLowerCase().replaceAll("\\s", "");
        return lower.equals("battery") || lower.equals("camera") || lower.equals("iprating") ||
                lower.equals("ram") || lower.equals("displaysize") || lower.equals("displaytype") ||
                lower.equals("bluetooth");
    }

    // Helper: Parses a numeric value from a string (e.g. "1400 mAh" returns 1400)
    static double parseFeatureValue(String value) {
        try {
            Matcher m = Pattern.compile("(\\d+(\\.\\d+)?)").matcher(value);
            if (m.find()) {
                return Double.parseDouble(m.group(1));
            }
        } catch(Exception e) {
            // ignore and return 0
        }
        return 0;
    }

    // ----- Inner Classes -----

    // Phone class
    static class Phone {
        int id;
        String brand;
        String model;
        int price;
        String releaseDate;
        Specs specs;

        public Phone(int id, String brand, String model, int price, String releaseDate, Specs specs) {
            this.id = id;
            this.brand = brand;
            this.model = model;
            this.price = price;
            this.releaseDate = releaseDate;
            this.specs = specs;
        }
    }

    // Specs class
    static class Specs {
        String battery;
        String camera;
        String ipRating;
        String ram;
        String displaySize;
        String displayType;
        String bluetooth;

        public Specs(String battery, String camera, String ipRating, String ram, String displaySize, String displayType, String bluetooth) {
            this.battery = battery;
            this.camera = camera;
            this.ipRating = ipRating;
            this.ram = ram;
            this.displaySize = displaySize;
            this.displayType = displayType;
            this.bluetooth = bluetooth;
        }

        // Returns the feature value based on key (ignores case and spaces)
        public String getFeature(String key) {
            switch(key.toLowerCase().replaceAll("\\s", "")) {
                case "battery": return battery;
                case "camera": return camera;
                case "iprating": return ipRating;
                case "ram": return ram;
                case "displaysize": return displaySize;
                case "displaytype": return displayType;
                case "bluetooth": return bluetooth;
                default: return "";
            }
        }
    }

    // SpellChecker class (simple implementation using Levenshtein distance)
    static class SpellChecker {
        public static String getClosestWord(String input, List<String> words) {
            int minDistance = Integer.MAX_VALUE;
            String closest = null;
            for (String word : words) {
                int dist = levenshteinDistance(input.toLowerCase(), word.toLowerCase());
                if (dist < minDistance) {
                    minDistance = dist;
                    closest = word;
                }
            }
            // Return suggestion if the edit distance is small (here, <=2) and input is not already correct.
            if (minDistance <= 2 && closest != null && !closest.equalsIgnoreCase(input)) {
                return closest;
            }
            return null;
        }

        // Standard Levenshtein distance algorithm
        public static int levenshteinDistance(String a, String b) {
            int[][] dp = new int[a.length()+1][b.length()+1];
            for (int i = 0; i <= a.length(); i++) {
                for (int j = 0; j <= b.length(); j++) {
                    if (i == 0) {
                        dp[i][j] = j;
                    } else if (j == 0) {
                        dp[i][j] = i;
                    } else {
                        dp[i][j] = Math.min(dp[i-1][j-1] + (a.charAt(i-1) == b.charAt(j-1) ? 0 : 1),
                                Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1));
                    }
                }
            }
            return dp[a.length()][b.length()];
        }
    }

    // WordCompletion class (returns words that start with the input)
    static class WordCompletion {
        public static List<String> getCompletions(String input, List<String> words) {
            List<String> completions = new ArrayList<>();
            for (String word : words) {
                if (word.toLowerCase().startsWith(input.toLowerCase())) {
                    completions.add(word);
                }
            }
            return completions;
        }
    }

    // TextAnalyser class (dummy methods that simulate scraping emails, phone numbers, and URLs)
    static class TextAnalyser {
        public static List<String> getEmails(String url) {
            return Arrays.asList("contact@apple.com", "support@apple.com");
        }
        public static List<String> getPhoneNumbers(String url) {
            return Arrays.asList("+1-800-APPLE", "+1-888-APPLE");
        }
        public static List<String> getURLs(String url) {
            return Arrays.asList("https://www.apple.com", "https://support.apple.com");
        }
    }
}