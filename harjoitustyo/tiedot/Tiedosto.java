
package harjoitustyo.tiedot;

import harjoitustyo.apulaiset.Syvakopioituva;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Tämä luokka on tiedostolle. Tiedoston nimi tulee Tieto -luokasta, koko on int
 * tyyppinen muuttuja..
 * @author Micko Hakala
 */
public class Tiedosto extends Tieto implements Syvakopioituva<Tiedosto>, Serializable {
   /**
    * Tiedoston koko, täytyy olla suurempi kuin 0.
    */
   private int koko;
   
   public Tiedosto() {
      super();
      koko = 0;
   }
   
   public Tiedosto(StringBuilder apuNimi, int apuKoko) 
   throws IllegalArgumentException {
      super(apuNimi);
      koko(apuKoko);
   }

   public int koko() {
      return koko;
   }

   public void koko(int apuKoko) throws IllegalArgumentException {
      if (apuKoko < 0) {
         throw new IllegalArgumentException("Error!"); 
      } else {
         koko = apuKoko;
      }
   }
   
   public String toString() {
      return super.toString() + " " + koko;
   }

   /**
    * Syväkopioi tiedoston.
    * @return Syväkopioitu tiedosto.
    */
   @SuppressWarnings("unchecked")
   @Override
   public Tiedosto kopioi() {
        try {
         // Byte-tyyppisten alkioiden (tavujen) taulukkoon kirjoittava virta.
         ByteArrayOutputStream bos = new ByteArrayOutputStream();

         // Olion tavuiksi muuntava virta, joka liittyy taulukkoon kirjoittavaan
         // virtaan.
         ObjectOutputStream oos = new ObjectOutputStream(bos);

         // Kirjoitetaan olio tavumuodossa taulukkoon.
         oos.writeObject(this);

         // Tyhjennetään puskuri ja suljetaan virta.
         oos.flush();
         oos.close();

         // Liitetään taulukkoon tavuja lukeva syötevirta.
         ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

         // Tavut olioksi muuttava virta, joka liittyy taulukosta lukevaan virtaan.
         ObjectInputStream ois = new ObjectInputStream(bis);

         // Kopio saadaan aikaiseksi lukemalla olion tavut taulukosta.
         Object kopio = ois.readObject();

         // Palautetaan oikean tyyppinen viite.
         return (Tiedosto) kopio;
      }
      // Sarjallistettavan olion oletusrakentaja hukassa.
      catch (InvalidClassException | NotSerializableException e) {
         return null;
      }
      // Löytyi olio, joka ei sarjallistu.

      // Tapahtui jotain yllättävää.
      catch (IOException | ClassNotFoundException e) {
         System.out.println("Error");
         return null;
      }
   }
}
