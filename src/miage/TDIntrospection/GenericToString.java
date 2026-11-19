package miage.TDIntrospection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import miage.exemple.Adresse;
import miage.exemple.Etudiant;
import miage.exemple.Personne;

public class GenericToString {
	StringBuffer sb;

	public String toString(Object o) {
		return toString(o, 1);
	}

	public String toString(Object o, int profondeur) {
		sb = new StringBuffer();
		buildString(o, profondeur);
		return sb.toString();
	}

	private void buildString(Object o, int profondeur) {
		if(o==null) { sb.append("null"); return; }
		if(profondeur <= 0) { sb.append(o.toString()); return; }

		Class<?> c = o.getClass();

		// Chaînes, types primitifs (emballés) : affichage direct
		if (o instanceof String || o instanceof Number || o instanceof Boolean || o instanceof Character) {
			sb.append(o.toString());
			return;
		}

		// Tableaux : on affiche chaque élément
		if (c.isArray()) {
			sb.append("[");
			int n = Array.getLength(o);
			for (int i = 0; i < n; i++) {
				if (i > 0) sb.append(", ");
				buildString(Array.get(o, i), profondeur - 1);
			}
			sb.append("]");
			return;
		}

		// Objets : champs de la classe et de ses superclasses
		sb.append(c.getName()).append("[");
		boolean premier = true;
		for (Class<?> k = c; k != null && k != Object.class; k = k.getSuperclass()) {
			for (Field f : k.getDeclaredFields()) {
				if (Modifier.isStatic(f.getModifiers())) continue;
				f.setAccessible(true);
				if (!premier) sb.append(", ");
				premier = false;
				sb.append(f.getName()).append("=");
				try {
					buildString(f.get(o), profondeur - 1);
				} catch (IllegalAccessException e) {
					sb.append("?");
				}
			}
		}
		sb.append("]");
	}

	static public void main(String[] args) {
		Adresse a = new Adresse("Nice", "Valrose", 28);
		Personne p = new Personne("Martin", "jean", a);
		Personne m = new Personne("Martin", "jeanne", a);
		Etudiant e = new Etudiant("Dupond", 15, p, m);
		System.out.println("classic:" + e.toString());
		System.out.println("deep:" + new GenericToString().toString(e, 4));

		Object o2 = new Object() {
			Object[] t = { new Adresse("Marseille", "le vieux port", 24), null, new int[] { 23, 24 } };
			private int v = 12;
			String nom = "Paul";
			Object self = this;
		};
		System.out.println("classic:" + o2.toString());

		System.out.println("deep: " + new GenericToString().toString(o2, 3));
		System.out.println("deep: " + new GenericToString().toString(o2, 4));
	}
}
