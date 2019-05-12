
package harjoitustyo.iteraattorit;

import harjoitustyo.omalista.OmaLista;
import harjoitustyo.tiedot.Tieto;
import java.util.Iterator;

/**
 * Tässä luokassa toteutaan iteraattori, jolla käydään läpi hakemiston kaikki
 * tiedot. Iteraattorin avulla tulostetaan find-komentoa käyttäen hakemiston tiedot
 * käyttäjälle.
 * @author Micko Hakala
 * @param <E> Tieto.
 */
public class HakemistoIteraattori<E> implements Iterator<E> {
   /**
    * Hakemisto, johon iteraattori tehdään.
    */
   private final OmaLista hakemisto;
   /**
    * Indeksi, jonka avulla tarkistetaan, onko seuraavaa tiedostoa hakemistossa.
    */
   private int nykyinenIndex;
   
   public HakemistoIteraattori(OmaLista<Tieto> apuHakemisto) {
      hakemisto = apuHakemisto;
      nykyinenIndex = 0;
   }
   
   /**
    * Tarkastetaan, onko indeksi alle hakemiston koon.
    * @return true, jos indeksi pienempi kuin hakemiston koko.
    */
   @SuppressWarnings("unchecked")
   @Override
   public boolean hasNext() {
      return nykyinenIndex < hakemisto.size();
   }
   
   /**
    * Palautetaan indeksin tieto.
    * @return tieto
    */
   @SuppressWarnings("unchecked")
   @Override
   public E next() {
      E palautus = (E) hakemisto.get(nykyinenIndex);
      nykyinenIndex++;
      return (E) palautus;
   }
   
}
