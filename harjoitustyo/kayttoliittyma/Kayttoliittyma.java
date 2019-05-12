
package harjoitustyo.kayttoliittyma;

import harjoitustyo.apulaiset.In;
/**
 * Tämä luokka käy kaiken vuorovaikutuksen ohjelman käyttäjän kanssa.
 * @author Micko Hakala
 */
public class Kayttoliittyma {
   /**
    * Vakiona virheen tulostamiselle.
    */
   final String virhe = "Error!";
   
   /**
    * Käyttöliittymää varten tulkki
    */
   private Tulkki tulkki;
   /**
    * Käyttöliittymää varten lukija.
    */
   private In lukija;
   
   public Kayttoliittyma(Tulkki apuTulkki) {
      tulkki = apuTulkki;
      In apuLukija = new In();
      lukija = apuLukija;
   }
   /**
    * Ohjelman käyttöliittmän käynnistys. Käy vuorovaikutuksen käyttäjän kanssa.
    */
   public void suoritus() {
      System.out.println("Welcome to SOS.");
      boolean kaynnissa = true;
      while (kaynnissa) {
         // Tulostaa hakemistopolun.
         System.out.print(tulkki.palautaHakemistoPolku());
         String kayttajanKomento = In.readString();
         
         // Jos komento alle 2 merkkiä, tulostetaan virhe.
         if (kayttajanKomento.length() < 2) {
            System.out.println(virhe);
         } else {
            // Jaetaan operaatio sekä parametri erilleen.
            String operaatio = kayttajanKomento.substring(0, 2);
            String tieto = "";
            if (kayttajanKomento.length() > 2) {
               tieto = kayttajanKomento.substring(3);
            }

            if (operaatio.equals("md")) {
               if (kayttajanKomento.length() > 2) {
                  if (!tulkki.luoHakemisto(tieto)) {
                     System.out.println(virhe);
                  }
               } else {
                  System.out.println(virhe);
               }
            } else if (operaatio.equals("cd")) {
               if (kayttajanKomento.length() == 2) {
                  if (!tulkki.vaihdaHakemisto()) {
                     System.out.println(virhe);
                  }
               } else {
                  if (!tulkki.vaihdaHakemisto(tieto)) {
                     System.out.println(virhe);
                  }
               }
            } else if (operaatio.equals("mf")) {
               if (!tulkki.luoTiedosto(tieto)) {
                  System.out.println(virhe);
               }
            } else if (operaatio.equals("mv")) {
               if (!tulkki.nimeaUudelleen(tieto)) {
                  System.out.println(virhe);
               }
            } else if (operaatio.equals("ls")) {  
               if (!tulkki.listaaHakemisto(tieto).isEmpty()) {          
                  System.out.println(tulkki.tulostaHakemisto(tulkki.listaaHakemisto
                  (tieto)));
               } else if (tulkki.listaaHakemisto(tieto).isEmpty() &&
                  !tieto.equals("*") && !tieto.equals("")) {
                  System.out.println(virhe);
               }
               
            } else if (operaatio.equals("rm")) {
               if (!tieto.equals("")) {               
                  if (!tulkki.poistaTieto(tieto)) {
                     System.out.println(virhe);
                  }
               } else {
                  System.out.println(virhe);
               }
            } else if (operaatio.equals("cp")) {
               String[] tiedot = kayttajanKomento.split(" ");
               if (tiedot.length <= 2) {
                  System.out.println(virhe);
               } else if (!tulkki.tiedostonKopiointi(tiedot[1], tiedot[2])) {
                  System.out.println(virhe);
               }
            } else if (kayttajanKomento.equals("find")) {
               if (!tulkki.listaaRekursiivisesti()) {
                  System.out.println(virhe);
               }
            } else if (kayttajanKomento.equals("exit")) {
               kaynnissa = false;
               System.out.println("Shell terminated.");
            } else {
               // Jos syöte virheellinen, tulostetaan virhe.
               System.out.println(virhe);
            }
         }
      }   
   }
}

