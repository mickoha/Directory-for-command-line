
package harjoitustyo.kayttoliittyma;

import harjoitustyo.iteraattorit.HakemistoIteraattori;
import harjoitustyo.omalista.OmaLista;
import harjoitustyo.tiedot.Hakemisto;
import harjoitustyo.tiedot.Tiedosto;
import harjoitustyo.tiedot.Tieto;
import java.util.LinkedList;

/**
 * Tämä luokka suorittaa käyttöliittymien komentojen perusteella ohjelman 
 * toiminnallisuuden.
 * @author Micko Hakala
 */
public class Tulkki {
   private final Hakemisto juuri;
   private Hakemisto nykyinen;
   
   /**
    * Tulkin käynnistyessä asetetaan sille juurihakemisto sekä nykyinen hakemisto
    * samaan hakemistoon.
    */
   public Tulkki() {
      Hakemisto yliHakemisto = new Hakemisto();
      nykyinen = yliHakemisto;
      juuri = yliHakemisto;
   }
   
   
   /** Metodi lisää hakemiston merkkijonon alkuun niin kauan kuin ollaan juuressa.
    * @return hakemistopolku
    */
   public String palautaHakemistoPolku() {
      String palautus = "/>";
      Hakemisto apu = nykyinen;
      while (nykyinen.ylihakemisto() != null) {
         palautus = "/" + nykyinen.nimi() + palautus;
         nykyinen = nykyinen.ylihakemisto();
      }
      nykyinen = apu;
      return palautus;
   }
   
   /** Luodaan hakemisto, jos sisältö ei sisällä saman nimistä tietoa.
    * @param nimi, luodaan hakemisto nimellä.
    * @return true, jos hakemiston luominen onnistuu.
    */
   public boolean luoHakemisto(String nimi) {
      OmaLista<Tieto> sisalto = nykyinen.sisalto();
      for (int i = 0; i < sisalto.size(); i++) {
         Tieto vertailtava = (Tieto) sisalto.get(i);
         if (vertailtava.equals(nimi)) {
            return false;
         }
      }
      try {
      Hakemisto uusi = new Hakemisto(new StringBuilder(nimi), nykyinen);
      sisalto.lisaa(uusi);
      return true;
      } catch (IllegalArgumentException e) {
         return false;
      }
   }   
   
      
   /** Ilman parametria saatu komento vie hakemiston juureen.
    * @return true, jos hakemiston vaihtaminen onnistuu.
    */
   public boolean vaihdaHakemisto() {
      nykyinen = juuri;
      return true;
   }
   
   /** 
    * Vaihdetaan hakemisto.
    * @param nimi, hakemiston uusi nimi.
    * @return true, jos hakemiston vaihtaminen onnistuu.
    */
   public boolean vaihdaHakemisto(String nimi) {
      if (nimi.equals("..") && nykyinen.ylihakemisto() != null) {
         nykyinen = nykyinen.ylihakemisto();
         return true;
      }
      
      if (nimi.equals("")) {
         return false;
      }
      
      OmaLista<Tieto> sisalto = nykyinen.sisalto();
      for (int i = 0; i < sisalto.size(); i++) {
         Tieto vertailtava = (Tieto) sisalto.get(i);
         if (vertailtava.equals(nimi)) {
            String object = sisalto.get(i).getClass().getSimpleName();
            if (object.equals("Hakemisto")) {
               nykyinen = (Hakemisto) sisalto.get(i);
               return true;
            }
         }
      }
      
      return false;
   }
   
   /** Luodaan uusi tiedosto parametrina saadun tiedon perusteella.
    * @param nimi, tiedoston nimi.
    * @return true, jos tiedoston luominen onnistuu.
    */
   public boolean luoTiedosto(String nimi) {
      
      //Jaetaan parametrina saatu nimi kahteen osaan, jolloin saadaan nimi ja 
      // koko eroteltua. Jos pelkkä nimi, palautetaan false;
      String[] nimiJaKoko = nimi.split(" ");
      if (nimiJaKoko.length != 2) {
         return false;
      }
      
      String apuNimi = nimiJaKoko[0];
      String onkoNumero = nimiJaKoko[1];
      if (!onkoNumero.matches("[0-9]+")) {
         return false;
      }
      
      int koko = Integer.parseInt(nimiJaKoko[1]);
      
      OmaLista<Tieto> sisalto = nykyinen.sisalto();
      for (int i = 0; i < sisalto.size(); i++) {
         Tieto vertailtava = (Tieto) sisalto.get(i);
         if (vertailtava.equals(apuNimi)) {
            return false;
         }
      }
      try {
         Tiedosto uusi = new Tiedosto(new StringBuilder(apuNimi), koko);
         nykyinen.sisalto().lisaa(uusi);
         return true;
      } catch (IllegalArgumentException e) {
         return false;
      }
   }
   /**
    * Nimeää tiedoston uudelleen tarkastusten jälkeen.
    * @param nimi, uusi nimi.
    * @return true, jos uudelleen nimeäminen onnistuu.
    */
   public boolean nimeaUudelleen(String nimi) {
      // Jaetaan parametrina saatu tieto kahtia, jos muutakuin 2, palautetaan false
      String[] nimet = nimi.split(" ");
      if (nimet.length != 2) {
         return false;      // verrataan pystytäänkö kopioimaan uudella nimellä.

      }
      
      
      // Verrataan sialtöä, löytyykö parametrin eka nimi listalta. Jos löytyy,
      OmaLista<Tieto> sisalto = nykyinen.sisalto();
      for (int i = 0; i < sisalto.size(); i++) {
         Tieto vertailtava = (Tieto) sisalto.get(i);
         if (vertailtava.equals(nimet[0])) {
            for (int a = 0; a < sisalto.size(); a++) {
               Tieto vertailtava2 = (Tieto) sisalto.get(a);
               if (vertailtava2.equals(nimet[1])) {
                  return false;
               }
            }
            try {
               Tieto vienti = vertailtava;
               vienti.nimi(new StringBuilder(nimet[1]));
               sisalto.poista(vertailtava);
               sisalto.lisaa(vienti);
               return true;
            } catch (IllegalArgumentException e) {
               return false;
            }
         }
      }
      
      return false;
   }
   
   /**
    * Listataan hakemisto.
    * @param nimi, hakusana.
    * @return Lista
    */
   public LinkedList<Tieto> listaaHakemisto(String nimi) {
      return nykyinen.hae(nimi);
   }
   
   /**
    * Tulostaa hakemiston käyttäjälle.
    * @param lista, lista joka tulostuu.
    * @return palauttaa hakemiston tiedot String -tyyppisenä.
    */
   public String tulostaHakemisto(LinkedList<Tieto> lista) {
      String palautus = lista.get(0).toString();
      for (int i = 1; i < lista.size(); i++) {
         palautus = palautus + "\n" + lista.get(i).toString();
      }
      
      return palautus;
   }
   
   /**
    * Poistaa tiedon hakemistosta.
    * @param nimi, poistaa parametrina saadun tiedon.
    * @return true, jos poistaminen onnistuu.
    */
   public boolean poistaTieto(String nimi) {
      int maara = 0;
      for (int i = 0; i < nykyinen.sisalto().size(); i++) {
         Tieto vertailtava = (Tieto) nykyinen.sisalto().get(i);
         if (vertailtava.equals(nimi)) {
            if (nykyinen.poista(vertailtava)) {
               maara++;
               i--;
            }
         }
      }
      
      if (maara == 0) {
         return false;
      } else {
         return true;
      }
   }
   
   /**
    * Syväkopioi tiedoston.
    * @param tiedosto mitä kopioidaan
    * @param kohde minne kopioidaan
    * @return true, jos kopiointi onnistuu.
    */
   public boolean tiedostonKopiointi(String tiedosto, String kohde) {
      return nykyinen.syvakopiointi(tiedosto, kohde);
   }
   
   /**
    * Listaa hakemiston tiedot järjestyksessä sekä tulostaa ne.
    * @return true, jos tulostus onnistuu.
    */
   public boolean listaaRekursiivisesti() {
      if (nykyinen.sisalto().isEmpty()) {
         return false;
      } 
      
      // Haetaan HakemistoIteraattori Hakemisto-luokan iterator medotilla.
      HakemistoIteraattori palautus = (HakemistoIteraattori) nykyinen.iterator();
     
      if (palautus == null) {
         return false;
      }
      
      // Tulostetaan HakemistoIteraattoria hyödyntäen.
      while (palautus.hasNext()) {
         System.out.println(palautus.next());
      }
      return true;
   }
}
