package main_package;

import java.util.HashMap;

/**
 * Class responsible for all shortcut-like transformations.
 */
public class ShortcutTransformation extends TransformationDexorator {

   private static final HashMap<String,String> shortcutsWord = new HashMap<String, String>() {{
        put("np.", "na przykład ");
        put("Np.", "Na przykład ");
        put("NP.", "Na Przykład ");
        put("itd.", "i tym podobne ");
        put("Itd.", "I Tym Podobne ");
        put("ITD.", "I TYM PODOBNE ");
        put("dr", "doktor ");
        put("Dr", "Doktor ");
        put("DR", "DOKTOR ");
        put("prof.", "profesor ");
        put("Prof.", "Profesor ");
        put("PROF.", "PROFESOR ");
    }};
    private static final HashMap<String,String> wordShortcuts = new HashMap<String, String>() {{
        put("na przykład", "np. ");
        put("i tym podobne", "itd. ");
        put("między innymi", "m.in. ");
    }};
    private static final HashMap<String,String> latex = new HashMap<String, String>() {{
        put("&", "\\& ");
        put("$", "\\$ ");
    }};
    
    /**
     * Function to change format to Latex
     * @param text String to transformation
     * @return text after transformation
     */
    private static String toLatex (String text) {
        String [] result;
        String finaly = "";
        result = text.split(" ");

        for (int i = 0; i < result.length; i++)
            switch (result[i]) {
                case "&":
                    finaly += latex.get("&");
                    break;

                case "$":
                    finaly += latex.get("$");
                    break;

                default:
                    finaly += result[i] + " ";
            }
        return finaly;
    }

    /**
     * Function to reduce words to shortcuts
     * @param text String to transformation
     * @return text after transformation
     */
    private static String wordToShortcut (String text) {
        String[] result;
        String finaly = "";
        result = text.split(" ");

        for (int i = 0; i < result.length; i++) {           //zakladamy, ze nie ma skrotu jednowyrazowego
            if (i < result.length - 1)
                switch (toLowerCase(result[i]) + toLowerCase(result[i+1]) ) {
                    case "itym":
                        if (i != result.length - 2 && toLowerCase(result[i+2]).equals("podobne"))
                            finaly += wordShortcuts.get("i tym podobne");
                        i += 2;
                        break;
                    case "międzyinnymi":
                        finaly += wordShortcuts.get("między innymi");
                        i++;
                        break;
                    case "naprzykład":
                        finaly += wordShortcuts.get("na przykład");
                        i++;
                        break;
                    default:
                        finaly += result[i] + " ";
                        break;
                }
            else
                finaly += result[i];
        }

        return finaly;
    }

    /**
     * Function to expand shortcuts
     * @param text String to transformation
     * @return text after transformation
     */
    private static String shortcutToWord (String text) {
        String [] result;
        String finaly = "", pom;
        result = text.split(" ");

        for (int i = 0; i < result.length; i++) {
            pom = shortcutsWord.get(result[i]);
            if (pom != null)
                finaly += pom;
            else
                finaly += result[i] + " ";
        }

        return finaly;
    }

    /**
     * Main function that depends on given index returns:
     * 0: word to shortcut
     * 1: shortcut to word
     * 2: to Latex format
     * @param text custom String given by user
     * @param index transformation index (doesn't matter in this subclass)
     * @return text after transformation
     */
    @Override
    public String transform(String text, int index) {
        switch (index) {
            case 0:
                return wordToShortcut(text);
            case 1:
                return shortcutToWord(text);
            case 2:
                return toLatex(text);
            default:
                return "Uratowałeś mnie! Ale Twoja funkcja jest w innym zamku!";
        }
    }
}
