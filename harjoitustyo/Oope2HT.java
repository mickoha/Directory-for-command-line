package harjoitustyo;


import harjoitustyo.kayttoliittyma.Kayttoliittyma;
import harjoitustyo.kayttoliittyma.Tulkki;

/**
 * Luokan ajaminen käynnistää hakemiston.
 * @author Micko Hakala
 */
public class Oope2HT {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
     Tulkki tulkki = new Tulkki();
     Kayttoliittyma kayttis = new Kayttoliittyma(tulkki);
     kayttis.suoritus();
   }
}
