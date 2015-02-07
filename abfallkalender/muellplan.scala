/**
 * Script to generate ICS files of semi-structured data.
 * Needs the ical4j library.
 * Most easy is to put the ical4j-1.0.5.jar in the same directory as this script and use the launch.sh provided (to set correct classpath...)
 *
 * Tested with scala 2.11.15
 */

import net.fortuna.{ ical4j => ical };
import net.fortuna.ical4j._
import net.fortuna.ical4j.model._
import net.fortuna.ical4j.model.component._
import net.fortuna.ical4j.model.Dur._
import net.fortuna.ical4j.util._
import net.fortuna.ical4j.model.property._
import net.fortuna.ical4j.util._
import scala.collection.immutable.BitSet

// not gmt time zone
System.setProperty("net.fortuna.ical4j.timezone.date.floating", "true")

//Konstanten fuer die Bitmasken
val GLASHUETTEN = BitSet(0x01)
val OBEREMS = BitSet(0x02)
val SCHLOSSBORN = BitSet(0x04)

val ALLEORTSTEILE = GLASHUETTEN ++ OBEREMS ++ SCHLOSSBORN

val BIOTONNE = BitSet(0x08)
val HAUSMUELL = BitSet(0x10)
val PAPIER = BitSet(0x20)
val GELBERSACK = BitSet(0x40)
val GRUENSCHNITT = BitSet(0x40 * 2)
val SCHADSTOFF = BitSet(0x40 * 4)

class Abfuhrtag(Datumi: String, Optioneni: BitSet, Bemerkungi: String = "") {
  val Datum: String = Datumi;
  val Optionen: BitSet = Optioneni;
  val Bemerkung: String = Bemerkungi;

  def getDur() = new Dur(1, 0, 0, 0)
  def getStart(): String = "00:00"
}

class Schadstoffmobil(Datumi: String, Optioneni: BitSet, Bemerkungi: String = "", Zeiti: String = "00:00", Daueri: Dur = new Dur(0, 1, 0, 0)) extends Abfuhrtag(Datumi, Optioneni, Bemerkungi) {
  val Zeit: String = Zeiti;
  val Dauer: Dur = Daueri;

  override def getDur() = Dauer;
  override def getStart() = Zeit;
}

val gruenschnitt_bemerkung = "Glashütten, Parkplatz Hobholz: 9.30-10.30 Uhr \nSchloßborn, Parkplatz hinter der Mehrzweckhalle: 8.00-9.00 Uhr\nOberems, Am Feuerwehrgerätehaus: 11.00-12.00 Uhr"
val schadstoff_bemerkung = "Glashütten, Parkplatz Waldfriedhof\nOberems, Feuerwehrgerätehaus\nSchloßborn, Parkplatz hinter Mehrzweckhalle"

val abfuhrliste: List[Abfuhrtag] = List(
  //Gelber Sack
  new Abfuhrtag("2015-01-08", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-01-22", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-02-05", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-02-19", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-03-05", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-03-19", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-04-01", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-04-16", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-04-30", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-05-15", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-05-29", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-06-11", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-06-25", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-07-09", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-07-23", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-08-06", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-08-20", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-09-03", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-09-17", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-10-01", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-10-15", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-10-29", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-11-12", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-11-26", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-12-10", ALLEORTSTEILE ++ GELBERSACK),
  new Abfuhrtag("2015-12-23", ALLEORTSTEILE ++ GELBERSACK),

  //Papier
  new Abfuhrtag("2015-01-20", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-01-21", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-02-17", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-02-18", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-03-17", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-03-18", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-04-14", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-04-15", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-05-12", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-05-13", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-06-09", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-06-10", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-07-07", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-07-08", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-08-04", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-08-05", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-09-01", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-09-02", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-09-29", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-09-30", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-10-27", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-10-28", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-11-24", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-11-25", GLASHUETTEN ++ OBEREMS ++ PAPIER),
  new Abfuhrtag("2015-12-22", SCHLOSSBORN ++ PAPIER),
  new Abfuhrtag("2015-12-23", GLASHUETTEN ++ OBEREMS ++ PAPIER),

  //Start Hausmuell+Biotonne
  new Abfuhrtag("2015-01-05", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-01-09", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-01-16", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-01-23", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-01-30", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-02-06", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-02-13", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-02-20", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-02-27", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-03-06", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-03-13", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-03-20", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-03-27", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),

  new Abfuhrtag("2015-04-04", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-04-10", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-04-17", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-04-24", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),

  new Abfuhrtag("2015-05-02", HAUSMUELL ++ BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-05-08", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-05-08", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-05-15", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-05-16", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-05-22", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-05-22", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-05-29", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-05-29", BIOTONNE ++ ALLEORTSTEILE),

  new Abfuhrtag("2015-06-05", HAUSMUELL ++ BIOTONNE ++ SCHLOSSBORN),
  new Abfuhrtag("2015-06-06", BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-06-12", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-06-12", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-06-19", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-06-19", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-06-26", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-06-26", BIOTONNE ++ ALLEORTSTEILE),

  new Abfuhrtag("2015-07-03", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-07-03", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-07-10", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-07-10", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-07-17", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-07-17", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-07-24", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-07-24", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-07-31", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-07-31", BIOTONNE ++ ALLEORTSTEILE),

  new Abfuhrtag("2015-08-07", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-08-07", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-08-14", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-08-14", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-08-21", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-08-21", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-08-28", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-08-28", BIOTONNE ++ ALLEORTSTEILE),

  new Abfuhrtag("2015-09-04", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-09-04", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-09-11", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-09-11", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-09-18", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-09-18", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-09-25", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-09-25", BIOTONNE ++ ALLEORTSTEILE),

  new Abfuhrtag("2015-10-02", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-10-02", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-10-09", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-10-09", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-10-16", BIOTONNE ++ ALLEORTSTEILE),
  new Abfuhrtag("2015-10-16", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-10-23", HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-10-23", BIOTONNE ++ ALLEORTSTEILE),

  new Abfuhrtag("2015-10-30", BIOTONNE ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-10-30", HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-10-31", BIOTONNE ++ SCHLOSSBORN),

  new Abfuhrtag("2015-11-06", BIOTONNE ++ HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-11-13", BIOTONNE ++ HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-11-20", BIOTONNE ++ HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-11-27", BIOTONNE ++ HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),

  new Abfuhrtag("2015-12-04", BIOTONNE ++ HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-12-11", BIOTONNE ++ HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),
  new Abfuhrtag("2015-12-18", BIOTONNE ++ HAUSMUELL ++ SCHLOSSBORN),
  new Abfuhrtag("2015-12-28", BIOTONNE ++ HAUSMUELL ++ GLASHUETTEN ++ OBEREMS),

  //Start Grünschnitt
  new Abfuhrtag("2015-03-28", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-04-11", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-04-25", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-05-09", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-05-23", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-06-06", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-07-04", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-07-18", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-08-01", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-08-15", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-08-29", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-09-12", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-09-26", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-10-10", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-10-24", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),
  new Abfuhrtag("2015-11-07", ALLEORTSTEILE ++ GRUENSCHNITT, gruenschnitt_bemerkung),

  //Start Schadstoffmobil
  new Schadstoffmobil("2015-02-28", GLASHUETTEN ++ SCHADSTOFF, schadstoff_bemerkung, "10:30", new Dur(0, 1, 15, 0)),
  new Schadstoffmobil("2015-03-12", SCHLOSSBORN ++ SCHADSTOFF, schadstoff_bemerkung, "09:00", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2015-03-12", OBEREMS ++ SCHADSTOFF, schadstoff_bemerkung, "10:45", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2015-05-30", GLASHUETTEN ++ SCHADSTOFF, schadstoff_bemerkung, "13:15", new Dur(0, 1, 15, 0)),
  new Schadstoffmobil("2015-06-11", SCHLOSSBORN ++ SCHADSTOFF, schadstoff_bemerkung, "11:15", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2015-06-11", OBEREMS ++ SCHADSTOFF, schadstoff_bemerkung, "13:00", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2015-08-29", GLASHUETTEN ++ SCHADSTOFF, schadstoff_bemerkung, "10:30", new Dur(0, 1, 15, 0)),
  new Schadstoffmobil("2015-09-10", SCHLOSSBORN ++ SCHADSTOFF, schadstoff_bemerkung, "09:00", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2015-09-10", OBEREMS ++ SCHADSTOFF, schadstoff_bemerkung, "10:45", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2015-11-28", GLASHUETTEN ++ SCHADSTOFF, schadstoff_bemerkung, "10:30", new Dur(0, 1, 15, 0)),
  new Schadstoffmobil("2015-12-10", SCHLOSSBORN ++ SCHADSTOFF, schadstoff_bemerkung, "17:15", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2015-12-10", OBEREMS ++ SCHADSTOFF, schadstoff_bemerkung, "15:30", new Dur(0, 1, 0, 0)));
/** Generates a calendar and saves it to a file */

def saveCalendar(terminliste: List[Abfuhrtag], dateiname: String) {
  val sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");

  val calendar = new Calendar();
  calendar.getProperties().add(ical.model.property.Version.VERSION_2_0);
  calendar.getProperties().add(new ical.model.property.ProdId("-//abfall-gem-glash//iCal4j 1.0//EN"));
  calendar.getProperties().add(ical.model.property.CalScale.GREGORIAN);
  calendar.getProperties().add(ical.model.property.Method.PUBLISH)

  val generelle_bemerkung = "Quelle: Abfallkalender der Gemeinde (http://www.gemeinde-glashuetten.de/dateien/Abfallkalender.pdf)"

  for (eintrag <- terminliste) {

    val typ = eintrag.Optionen match {
      case x if (BIOTONNE ++ HAUSMUELL).subsetOf(x) => "Biotonne,Hausmüll" //Komma für multiple Categories
      case x if BIOTONNE.subsetOf(x) => "Biotonne"
      case x if PAPIER.subsetOf(x) => "Papier"
      case x if HAUSMUELL.subsetOf(x) => "Hausmüll"
      case x if GELBERSACK.subsetOf(x) => "Gelber Sack"
      case x if GRUENSCHNITT.subsetOf(x) => "Grünschnitt"
      case x if SCHADSTOFF.subsetOf(x) => "Schadstoffmobil"
      case _ => "undefined"
    }

    val ort = eintrag.Optionen match {
      case x if ALLEORTSTEILE.subsetOf(x) => "Alle Ortsteile"
      case x if (OBEREMS ++ GLASHUETTEN).subsetOf(x) => "Glashütten/Oberems"
      case x if (OBEREMS).subsetOf(x) => "Oberems"
      case x if (GLASHUETTEN).subsetOf(x) => "Glashütten"
      case x if (SCHLOSSBORN).subsetOf(x) => "Schloßborn"
      case _ => "undefined"
    }

    val title = typ + " (" + ort + ")"

    val event = new VEvent({
      if (!eintrag.getStart.equalsIgnoreCase("00:00")) { //mit datetime
        new DateTime(sdf.parse(eintrag.Datum + ' ' + eintrag.getStart))
      } else { //nur mit date
        new Date(sdf.parse(eintrag.Datum + ' ' + eintrag.getStart))
      }
    }, eintrag.getDur(), title)

    // Generate a UID for the event..
    //object myHostInfo extends HostInfo { def getHostName = "abfall.gemeinde-glashuetten.de" }
    //event.getProperties().add(new UidGenerator(myHostInfo, eintrag.Datum).generateUid());
    
    //lets assume we will not have two bitmasks the same day
    val b = new StringBuilder();
    b.append(eintrag.Optionen.addString(new StringBuilder));
    b.append('-');
    b.append(eintrag.Datum);
    b.append("@abfall.gemeinde-glashuetten.de");
    event.getProperties().add(new Uid(b.toString()));
    //set transparency to transparent
    event.getProperties().add(net.fortuna.ical4j.model.property.Transp.TRANSPARENT)
    //add description?
    if (!eintrag.Bemerkung.isEmpty) {
      event.getProperties().add(new Description(eintrag.Bemerkung + "\n\n" + generelle_bemerkung));
    } else {
      event.getProperties().add(new Description(generelle_bemerkung));
    }
    event.getProperties().add(new Categories(typ));
    calendar.getComponents().add(event);
  }

  import java.io.FileOutputStream
  val fout = new FileOutputStream(dateiname);

  //to allow publish without organizer
  net.fortuna.ical4j.util.CompatibilityHints.setHintEnabled(net.fortuna.ical4j.util.CompatibilityHints.KEY_RELAXED_VALIDATION, true)

  import net.fortuna.ical4j.data.CalendarOutputter
  val outputter = new CalendarOutputter();
  outputter.output(calendar, fout);
}

/* filter the entrys and save in named files */
saveCalendar(abfuhrliste.filter(x => GLASHUETTEN.subsetOf(x.Optionen)), "abfall-2015-glashuetten.ics")
saveCalendar(abfuhrliste.filter(x => OBEREMS.subsetOf(x.Optionen)), "abfall-2015-oberems.ics")
saveCalendar(abfuhrliste.filter(x => SCHLOSSBORN.subsetOf(x.Optionen)), "abfall-2015-schlossborn.ics")

println("fertig")
