package miage.TDIntrospection;

/**
 * @version 1.2 - 5 novembre 2024
 * @author Michel Buffa + amélioration de Philippe Lahire
 * Inspire par la classe Reflectiontest.java de
 * Cay S. Horstmann & Gary Cornell, publiee dans le livre Core Java, Sun Press
 */

import java.lang.reflect.Type;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnalyseurDeClasse {

    public static void analyseClasse(String nomClasse, String decalage) throws ClassNotFoundException {
        // Récupération d'un objet de type Class correspondant au nom passé en paramètre et analyse en plusieurs étapes

        Class<?> cl = getClasse(nomClasse);

        affichePackageDeLaClasse(cl, decalage);

        afficheEnTeteClasse(cl, decalage);

        // System.out.println(decalage + "// Inner Classes");
        afficheInnerClasses(cl, decalage);

        // System.out.println(decalage + "// Champs");
        afficheAttributs(cl, decalage);

        // System.out.println("\n" + decalage + "// Constructeurs");
        afficheConstructeurs(cl, decalage);

        // System.out.println(decalage + "\n" + decalage + "// Méthodes");
        afficheMethodes(cl, decalage);

        // L'accolade fermante de fin de classe !
        System.out.println(decalage + "}");
    }

    public static Class<?> getClasse(String nomClasse) throws ClassNotFoundException {
        return Class.forName(nomClasse);
    }

    public static void affichePackageDeLaClasse(Class<?> cl, String decalage) {
        System.out.println(decalage + "package " + cl.getPackageName() + ";");
    }

    public static void afficheEnTeteClasse(Class<?> cl, String decalage) {
        String mods = Modifier.toString(cl.getModifiers());
        System.out.print(decalage + mods + " class " + cl.getName());

        Class<?> parent = cl.getSuperclass();
        if (parent != null && parent != Object.class) {
            System.out.print(" extends " + parent.getName());
        }

        Class<?>[] interfaces = cl.getInterfaces();
        if (interfaces.length > 0) {
            System.out.print(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(interfaces[i].getName());
            }
        }

        System.out.println(" {");
    }

    public static void afficheInnerClasses (Class<?> cl, String decalage) throws ClassNotFoundException {
        // Classes imbriquées (statiques ou non) : analyse récursive avec un décalage supplémentaire
        for (Class<?> inner : cl.getDeclaredClasses()) {
            analyseClasse(inner.getName(), decalage + "    ");
        }
    }

    public static void afficheAttributs(Class<?> cl, String decalage) {
        Field[] champs = cl.getDeclaredFields();
        for (Field f : champs) {
            String mods = Modifier.toString(f.getModifiers());
            System.out.print(decalage + "    " + mods + " ");
            afficheTypeFull(f.getType(), f.getGenericType(), decalage);
            System.out.println(" " + f.getName() + ";");
        }
    }

    private static void afficheTypeBasic(Class<?> cl, String decalage) {
        System.out.print(cl.getTypeName());
    }

    private static void afficheTypeFull(Class<?> t, Type gt, String decalage) {
        // gt est le type générique (ex: java.util.List<java.lang.String>)
        System.out.print(gt.getTypeName());
    }

    public static void afficheConstructeurs(Class<?> cl, String decalage) {
        Constructor<?>[] constructeurs = cl.getDeclaredConstructors();
        for (Constructor<?> constructor : constructeurs) {
            String mods = Modifier.toString(constructor.getModifiers());
            System.out.print(decalage + "    " + mods + " " + constructor.getName() + "(");
            afficheParamsBasic(constructor.getParameterTypes(), decalage);

            System.out.println(");");
        }
    }

    public static void afficheMethodes(Class<?> cl, String decalage) {
        // Affichage des m�thodes de la classe : utilise afficheParamsBasic ou afficheParamsFull
        Method [] methodes = cl.getDeclaredMethods();
        for (Method methode : methodes) {
            String mods = Modifier.toString(methode.getModifiers());
            System.out.print(decalage + "    " + mods + " " + methode.getGenericReturnType().getTypeName() + " " + methode.getName() + "(");

            afficheParamsFull(methode.getParameterTypes(), methode.getGenericParameterTypes(), decalage);

            System.out.println(");");

        }

    }

    private static void afficheParamsBasic(Class<?>[] params, String decalage) {
        // Affichage du type de chaque param�tre : Ne prend pas en compte l'instanciation de types g�n�riques (variante simple)
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(params[i].getTypeName());
        }
    }

    private static void afficheParamsFull(Class<?>[] params, Type[] gparams, String decalage) {
        for (int i = 0; i < gparams.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(gparams[i].getTypeName());
        }
    }

    public static void main(String[] args) {
        try {
            analyseClasse(args[0], "");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe introuvable : " + args[0]);
        }
    }
}