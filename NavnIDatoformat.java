package navnidatoformat;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Program for å endre mappenavn av type datoformat dd.mm.yyyy til yyyy-mm-dd
 * i en mappe og alle undermapper.
 * Mappene kan da sorteres i kronologisk.
 * Programmet skriver ut alle endrete mappenavn.
 * 
 * @author agegu
 */
public class NavnIDatoformat {

    static Pattern mappeformat = Pattern.compile("[0-3]\\d\\.[01]\\d\\.[12]\\d\\d\\d$");

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("<startmappe> (for eksempel O:\\videosener)");
            return;
        }
        String startmappe = args[0];
        File mappe = new File(startmappe);
        try {
            mappeRenamer(mappe);
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Ferdig!");
    }

    static void mappeRenamer(File mappe) throws IOException {
        if (!mappe.isDirectory()) {
            return;
        }
        String navn = mappe.getName();
        String path = mappe.getCanonicalPath();
        //System.out.println("Undersøker " + path);

        if (mappeformat.matcher(navn).matches()) {
            // Denne mappen må døpes om
            String nyttNavn = navn.substring(6, 10) + "-" + navn.substring(3, 5) + "-" + navn.substring(0, 2);
            System.out.println("Døper om til " + nyttNavn);
            File nyMappe = new File(mappe.getParent() + "\\" + nyttNavn);
            mappe.renameTo(nyMappe);
            System.out.println("Rename " + mappe.getCanonicalPath());
        } else { // En normal mappe
            // Vi må gå gjennom alle filene/mappene i denne mappen
            for (File mappeinnhold : mappe.listFiles()) {
                mappeRenamer(mappeinnhold);
            }
        }
    }
}
