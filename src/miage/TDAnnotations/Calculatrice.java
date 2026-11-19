package miage.TDAnnotations;

public class Calculatrice {

	public Calculatrice(){
		dernierResultat = 0;
	}
	
	private double dernierResultat;
	
	public void resetDernierResultat () {
		dernierResultat = 0;
	}

	public double getDernierResultat () {
		return dernierResultat ;
	}
	
    // Méthode pour additionner deux nombres
	public void additionner(double a, double b) {
    	dernierResultat = a + b;
    	System.out.println ("Résultat = " + dernierResultat);
    }

    // Méthode pour soustraire deux nombres
	public void soustraire(double a, double b) {
        dernierResultat = a - b;
        System.out.println ("Résultat = " + dernierResultat);
    }

    // Méthode pour multiplier deux nombres
	public void multiplier(double a, double b) {
    	dernierResultat =  a * b;
    	System.out.println ("Résultat = " + dernierResultat);
    }

    // Méthode pour diviser deux nombres
	public void diviser(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division par zéro");
        }
        dernierResultat =  a / b;
        System.out.println ("Résultat = " + dernierResultat);
    }

    public static void main(String[] args) {
       Calculatrice calc = new Calculatrice();
       calc.additionner(5, 3);
       calc.soustraire(5, 3);
       calc.multiplier(5, 3);
       calc.diviser(6, 3);
    }
}
