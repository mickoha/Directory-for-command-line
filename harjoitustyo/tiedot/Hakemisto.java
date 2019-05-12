
package harjoitustyo.tiedot;

import harjoitustyo.apulaiset.Sailova;
import harjoitustyo.iteraattorit.HakemistoIteraattori;
import harjoitustyo.omalista.OmaLista;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Tämä luokka on hakemistolle Hakemistolla on OmaLista Tieto-tyyppinen 
 * sisalto sekä Hakemiston ylihakemisto. .
 * @author Micko Hakala
 */
public class Hakemisto extends Tieto implements Sailova<Tieto>, Iterable<Tieto> {
   /**
    * Lista, johon hakemiston sisältö tallennetaan.
    */
   private OmaLista<Tieto> sisalto;
   
   /**
    * Hakemiston ylihakemisto.
    */
   private Hakemisto ylihakemisto;

   public Hakemisto() {
      super();
      OmaLista<Tieto> uusiLista = new OmaLista<>();
      sisalto = uusiLista;
      ylihakemisto = null;
   }
   
   public Hakemisto(StringBuilder apuNimi, Hakemisto apuYlihakemisto) {
      super(apuNimi);
      OmaLista<Tieto> uusiLista = new OmaLista<>();
      sisalto = uusiLista;
      ylihakemisto = apuYlihakemisto;
      
   }

   public StringBuilder nimi() {
      return super.nimi();
   }

   public void nimi(StringBuilder apuNimi) {
      super.nimi(apuNimi);
   }
   
   public OmaLista<Tieto> sisalto() {
      return sisalto;
   }

   public void sisalto(OmaLista<Tieto> sisalto) {
      this.sisalto = sisalto;
   }

   public Hakemisto ylihakemisto() {
      return ylihakemisto;
   }

   public void ylihakemisto(Hakemisto ylihakemisto) {
      this.ylihakemisto = ylihakemisto;
   }
   
   /**
    * Metodi syväkopioi ensimmäisenä parametrina saadun tiedoston tiedoston
    * ylihakemistoon, nykyiseen hakemistoon tai alihakemistoon. Ensin tarkastetaan
    * onko kohde hakemisto. Jos ei, yritetään kopioida tiedosto uudella nimellä.
    * @param tiedosto mitä kopioidaan
    * @param kohde minne kopioidaan
    * @return true, jos kopiointi onnistuu.
    */
   public boolean syvakopiointi(String tiedosto, String kohde) {      
      boolean kohdeOnTiedosto = true;

      // Luodaan lista, johon viedään kaikki sisällön tiedot, jotka sopivat
      // parametrina saatuun tiedoston nimeen. 
      OmaLista <Tieto> lista = new OmaLista<>();
      for (int i = 0; i < sisalto.size(); i++){
         Tieto vertailtava = (Tieto) sisalto.get(i);
         if (vertailtava.equals(tiedosto)) {
            if (sisalto.get(i).getClass().getSimpleName().equals("Hakemisto")) {
               return false;
         } else {
               lista.lisaa(vertailtava);
            }
         }
      }
      
      if (lista.isEmpty()) {
         return false;
      }
      
      // Tarkastetaan, onko kopioitavan kohde ylihakemisto.
      if (ylihakemisto != null) {
         if (ylihakemisto.nimi().toString().equals(kohde) || kohde.equals("..")) {
         kohdeOnTiedosto = false;
         // Tarkastetaan, sisältääkö ylihakemisto yhtään saman nimistä
         // tiedostoa kuin kopioitavat on.
         for (int i = 0; i < ylihakemisto.sisalto.size(); i++) {
            for (int a = 0; a < lista.size(); a++) {
               if (lista.get(a).equals(ylihakemisto.sisalto().get(i))) {
                  return false;
               }
            }
         }
         
         // Viedäään kopioidut tiedostot hakemistoon
         for (int i = 0; i < lista.size(); i++) {
            Tiedosto kopioitava = (Tiedosto)lista.get(i);
            Tiedosto kopioitu = kopioitava.kopioi();
            ylihakemisto.lisaa(kopioitu);
         }
         
         return true;
         }
      }
      
      // Käydään for-loopilla läpi, onko kohde alihakemisto.
      for (int i = 0; i < sisalto.size(); i++) {
         Tieto vertailtava = (Tieto) sisalto.get(i);
         // Jos kohde löytyy, tarkistetaan löytyykö kohteesta saman nimisiä 
         // tiedostoja kuin kopioitavat.
         if (vertailtava.equals(kohde) && sisalto.get(i).getClass().getSimpleName().equals("Hakemisto")) {
            kohdeOnTiedosto = false;
            Hakemisto apu = (Hakemisto) sisalto.get(i);
            for (int a = 0; a < apu.sisalto.size(); a++) {
               for (int e = 0; e < lista.size(); e++) {
                  if (lista.get(e).equals(apu.sisalto.get(a))) {
                     return false;
                  }
               }
            }
            
            // Viedäään kopioidut tiedostot hakemistoon
            for (int y = 0; y < lista.size(); y++) {
               Tiedosto kopioitava = (Tiedosto)lista.get(y);
               Tiedosto kopioitu = kopioitava.kopioi();
               apu.lisaa(kopioitu);
            }
         
         return true;
         }
      }
      // Jos kohde on tiedoston nimi, tarkistetaan löytyykö hakemistosta 
      // uuden tiedoston nimeä.
      if (kohdeOnTiedosto) {
         if (lista.size() > 1) {
            return false;
         }
         for (int i = 0; i < sisalto.size(); i++) {
            for (int a = 0; a < lista.size(); a++) {
               Tieto vertailtava = (Tieto) sisalto.get(i);
               if (vertailtava.equals(kohde)) {
                  return false;
               }
            }
         }

         // Viedäään kopioidut tiedostot hakemistoon
         for (int y = 0; y < lista.size(); y++) {
            Tiedosto kopioitava = (Tiedosto)lista.get(y);
            Tiedosto kopioitu = kopioitava.kopioi();
            try {
               kopioitu.nimi(new StringBuilder(kohde));
               sisalto.lisaa(kopioitu);
            } catch (IllegalArgumentException e) {
               return false;
            }
         }
         
         return true;
      }
      
     return false;
   }
   
   /**
    * Luodaan lista, johon kaikki sisällöstä löytyvät tiedot parametrina saatua 
    * hakusanaa hyödyntäen sopivat.
    * @param hakusana haettava hakusana.
    * @return lista tiedoista, jotka löytyvät hakusanalla.
    */
   @Override
   public LinkedList<Tieto> hae(String hakusana) {
      LinkedList<Tieto> lista = new LinkedList<>();
      for (int i = 0; i < sisalto.size(); i++) {
         Tieto apu = (Tieto) sisalto.get(i);
         if (apu.equals(hakusana)) {
            lista.add(apu);
         }
      }
      
      return lista;
   }
   
   /**
    * Lisätään tieto listalle OmaListan lisaa metodia hyödyntäen.
    * @param lisattava lisättävä tieto.
    * @return true, jos lisääminen onnistuu.
    */
   @Override
   public boolean lisaa(Tieto lisattava) {
      boolean palautus = sisalto.lisaa(lisattava);
      return palautus;
   }

   /**
    * Poistetaan tieto listalta OmaListan poista metodia hyödyntäen.
    * @param poistettava poistettavan tieto.
    * @return true, jos poistaminen onnistuu.
    */
   @Override
   public boolean poista(Tieto poistettava) {
      int palautus = sisalto.poista(poistettava);
      if (palautus == 0) {
         return false;
      } else {
         return true;
      }
   }
   
   public String toString() {
      return super.nimi() + "/ " + sisalto.size();
   }

  
   /**
    * Luodaan iteraattori HakemistoIteraattori.
    * @return HakemistoIteraattori
    */
   @SuppressWarnings("unchecked")
   @Override
   public Iterator<Tieto> iterator() {
      OmaLista lista = new OmaLista<>();
      
      // Haetaan palautettava lista.
      lista = lisaaJarjestykseen(this, lista);
      
      
      HakemistoIteraattori palautus = new HakemistoIteraattori(lista);
      Iterator palautettava = (Iterator) palautus;
   
      return palautettava;
   }
   
   /**
    * Lisätään listalle tiedot esijärjestyksessä. Käydään läpi koko hakemisto.
    * @param apuHakemisto mikä hakemus järjestetään.
    * @param tiedot mihin järjestetään.
    * @return listan tiedot järjestyksessä.
    */
   @SuppressWarnings("unchecked")
   public OmaLista lisaaJarjestykseen(Hakemisto apuHakemisto, OmaLista tiedot) {
      OmaLista<Tieto> apuSisalto = apuHakemisto.sisalto;
      for (int i = 0; i < apuSisalto.size(); i++) {
         Tieto listanTieto = (Tieto) apuSisalto.get(i);
         tiedot.add(listanTieto);
         
         // Haetaan sisallon tieto luokkaan object ja vertaillaan, onko luokka =
         // Hakemisto
         Object vertailtava = apuSisalto.get(i);
         String luokka = vertailtava.getClass().getSimpleName();
         if (luokka.equals("Hakemisto")) {
            tiedot = lisaaJarjestykseen((Hakemisto)apuSisalto.get(i), tiedot);
         }
      }
      
      return tiedot;
   }
}