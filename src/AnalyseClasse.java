import java.lang.reflect.Modifier;

public class AnalyseClasse {

    public static void main(String[] args) {
        // Récupère la class correspondant au string en paramètre si elle existe
        // Sinon affiche l'erreur
        try {
            Class<?> c = Class.forName(args[0]);
            int mods = c.getModifiers();
            String texteMods = Modifier.toString(mods);
            System.out.println(texteMods + " " + c.getName());

            Class<?> parent = c.getSuperclass();
            if (parent != null) {
                System.out.println("  hérite de : " + parent.getName());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Classe introuvable : " + args[0]);
        }
    }
}