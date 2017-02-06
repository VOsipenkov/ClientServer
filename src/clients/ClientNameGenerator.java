package clients;

public class ClientNameGenerator {
    private static String[] names = {"intel", "AMD", "Siemens", "Motorolla", "Verison", "Beeline", "Harman",
            "Yota",
            "Liberty", "Aeroflot", "Gazprom", "Taxe Veset", "Nelsen", "Clever", "Anritsu", "Mera", "Appolo",
            "BBC" };

    public static String getName() {
        int index = (int) (Math.random() * names.length);

        return names[index];
    }
}
