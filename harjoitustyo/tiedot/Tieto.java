
package harjoitustyo.tiedot;

import harjoitustyo.apulaiset.Tietoinen;
import java.io.Serializable;

/**
 * Tämä luokka on abstract ja sen tieto periytetään luokkiin Tiedosto sekä 
 * hakemisto.
 * @author Micko Hakala
 */
public abstract class Tieto implements Tietoinen, Comparable<Tieto>, Serializable{
   /**
    * Nimi tiedolle.
    */
   private StringBuilder nimi;
   
   public Tieto() {
      StringBuilder uusi = new StringBuilder();
      nimi = uusi;
   }
   
   public Tieto(StringBuilder apuNimi) throws IllegalArgumentException {
      nimi(apuNimi);
   }

   public StringBuilder nimi() {
      return nimi;
   }

   public void nimi(StringBuilder apuNimi) throws IllegalArgumentException {     
      if (apuNimi != null && apuNimi.length() > 1) {
         for (int i = 0; i < apuNimi.toString().length(); i++) {
            if (apuNimi.charAt(i) == '.' && apuNimi.charAt(i+1) == '.') {
               throw new IllegalArgumentException("Error");
            } else if (!onkoKirjain(apuNimi.charAt(i))) {
               throw new IllegalArgumentException("Error");
            }
         }
         
         nimi = apuNimi;
      }
   }
   
   public boolean onkoKirjain(char apu) {
      return (apu >= 'a' && apu <= 'z') || (apu >= 'A' && apu <= 'Z') ||
         (apu >= '0' && apu <= '9') || (apu == '_') || (apu == '.');
   }
   
   public String toString() {
      return nimi.toString();
   }   

   
   @Override
   public int hashCode() {
      int hash = 5;
      return hash;
   }
   /**
    * equals-metodi vertailee ovatko sanat samoja. Hakusanaan on mahdollista lisätä
    *  -merkki, jolloin haettavasta sanasta haetaan hakusanaa alusta, lopusta tai 
    * keskeltä.
    * @param hakusana haettava hakusana.
    * @return true, jos hakusana täsmää.
    */
   @Override
   public boolean equals(String hakusana) {
      if (hakusana == null) {
         return false;
      }
      if (hakusana.equals("*")) {
         return true;
      }
      
      if (hakusana.isEmpty()) {
         return true;
      }
      if (nimi.toString().equals(hakusana)) {
         return true;
      }
      int aloituskohta = nimi.toString().length() - hakusana.length() + 1;
      
      // Tarkastetaan, onko tähtimerkki alussa.
      if (hakusana.charAt(0) == '*' && hakusana.charAt(hakusana.length()-1) != '*') {
         if (nimi.toString().substring(aloituskohta).equals(hakusana.substring(1))) {
            return true;
         }
         
      // Tarkastetaan, onko tähtimerkki lopussa.
      } else if (hakusana.charAt(0) != '*' && hakusana.charAt(hakusana.length()-1) == '*') {
         if (nimi.toString().substring(0, hakusana.length()-1)
            .equals(hakusana.substring(0, hakusana.length()-1))) {
            return true;         
         }
         
      // Tarkastetaan, onko tähtimerkki alussa sekä lopussa.
      } else if (hakusana.charAt(0) == '*' && hakusana.charAt(hakusana.length()-1) == '*') {
         if (hakusana.substring(1, hakusana.length() -1 ).contains("*")) {
            return false;
         }
         if (nimi.toString().contains(hakusana.substring(1, hakusana.length() - 1))) {
            if (hakusana.substring(1, hakusana.length() - 1).isEmpty()) {
               return false;
            }
            return true;
         }         
      }
      
      return false;
   }
   
   /**
    * Tarkastaa, ovatko nimet samat.
    * @param obj vertailtava tieto.
    * @return true, jos nimet samat.
    */
   @Override
   public boolean equals(Object obj) {
      
      if (obj == null) {
         return false;
      }
      
      if (obj.getClass().getName().contains("Double")) {
         return false;
      }
      
      Tieto testi = (Tieto) obj;
      if (testi.nimi().toString().equals(this.nimi().toString())){
         return true;
      }
     
      return false;
   }

   /**
    * Vertailee aakkosjärjestystä.
    * @param o vertailtava tieto.
    * @return -1, jos järjestyksessä ennen, 1 jos jälkeen ja 0 jos samat.
    */
   @Override
   public int compareTo(Tieto o) {
      String alkuperainen = this.toString();
      String vertailtava = o.toString();
      for (int i = 0; i < o.toString().length(); i++) {
         if (alkuperainen.charAt(i) < vertailtava.charAt(i)) {
            return -1;
         } else if (alkuperainen.charAt(i) > vertailtava.charAt(i)) {
            return 1;
         }
      }
      
      return 0;
   }
   
   
}
