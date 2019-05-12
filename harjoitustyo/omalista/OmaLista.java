
package harjoitustyo.omalista;

import java.util.LinkedList;
import harjoitustyo.apulaiset.Ooperoiva;

/**
 * Tämä luokka rakentaa oman listan, jota käytetään hakemistona. 
 * Lisaa metodi lisää alkiot aakkosjärjestykseen ja poista metodi poistaa
 * parametrina saadun alkion listalta.
 * @author Micko Hakala
 * @param <E> Tieto
 */
public class OmaLista<E> extends LinkedList implements Ooperoiva<E> {

   /**
    * Metodi lisää omalle listalle alkiot suoraan aakkosjärjestykseen.
    * @param uusi lisätään parametrina saatu tieto
    * @return true, jos lisääminen onnistuu.
    */
   @Override
   @SuppressWarnings({"unchecked"})
   public boolean lisaa(E uusi) {
      if (uusi == null) {
         return false;
      }
      
      String object = uusi.getClass().getName();
      if (object.contains("Object")) {
         return false;
      }
      
      if (this.isEmpty()) {
         this.add(uusi);
         return true;
      } else {
         Comparable eka = (Comparable)uusi;
         if (this.size() == 1) {
            Comparable verrattava = (Comparable) this.get(0);
            if (eka.compareTo(verrattava) < 0) {
               this.add(0, uusi);
               return true;
            } else {
               this.add(uusi);
               return true;
            }
         }
         for (int i = 0; i < this.size() - 1; i++) {
            Comparable toka = (Comparable)this.get(i);
            Comparable kolmas = (Comparable)this.get(i+1);
            if (eka.compareTo(toka) < 0) {
               this.add(0, uusi);
               return true;
            } else if (eka.compareTo(toka) == 0) {
               this.add(i, uusi);
               return true;
            } else if (eka.compareTo(kolmas) == 0) {
               this.add(i+1, uusi);
               return true;
            } else if (eka.compareTo(toka) > 0 && eka.compareTo(kolmas) < 0) {
               this.add(i+1, uusi);
               return true;
            } else if (i == this.size() - 1 && eka.compareTo(kolmas) > 0) {
               this.add(uusi);
               return true;
            } else if (eka.compareTo(kolmas) > 0 && i+1 == this.size() -1) {
               this.add(uusi);
               return true;
            }
         }
      }
      return false;
   }
   
   /**
    * Poistaa paramentrina saadun tiedon listalta.
    * @param poistettava poistetaan paremetrina saatu tieto.
    * @return lukumäärä, montako poistettu.
    */
   @Override
   public int poista(E poistettava) {
      int palautus = 0;
      for (int i = 0; i < this.size(); i++) {
         if (poistettava == this.get(i)) {
            this.remove(i);
            i = i-1;
            palautus++;
         }
      }
      
      return palautus;
   }   
}
