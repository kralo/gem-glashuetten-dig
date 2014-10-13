/*

Script to generate ICS files of semi-structured data.
Needs the ical4j library.
Most easy is to put the ical4j-1.0.5.jar in the same directory as this script and use the launch.sh provided (to set correct classpath...)
*/

//import java.util.Calendar;
import net.fortuna.{ ical4j => ical }
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
val GLASHUETTEN = 0x01
val OBEREMS = 0x02
val SCHLOSSBORN = 0x04

val ALLEORTSTEILE = BitSet(GLASHUETTEN, OBEREMS, SCHLOSSBORN)

val BIOTONNE = 0x08
val HAUSMUELL = 0x10
val PAPIER = 0x20
val GELBERSACK = 0x40
val GRUENSCHNITT = 0x40 * 2
val SCHADSTOFF = 0x40 * 4

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
  new Abfuhrtag("2014-09-05", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-09-12", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-09-19", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-09-26", ALLEORTSTEILE ++ BitSet(BIOTONNE)),

  new Abfuhrtag("2014-10-02", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-10-10", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-10-17", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-10-24", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-10-31", ALLEORTSTEILE ++ BitSet(BIOTONNE)),

  new Abfuhrtag("2014-11-07", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-11-21", ALLEORTSTEILE ++ BitSet(BIOTONNE)),

  new Abfuhrtag("2014-12-05", ALLEORTSTEILE ++ BitSet(BIOTONNE)),
  new Abfuhrtag("2014-12-19", ALLEORTSTEILE ++ BitSet(BIOTONNE)),

  //Start Papier
  new Abfuhrtag("2014-10-28", BitSet(GLASHUETTEN, OBEREMS, PAPIER)),
  new Abfuhrtag("2014-10-29", BitSet(SCHLOSSBORN, PAPIER)),

  new Abfuhrtag("2014-11-25", BitSet(GLASHUETTEN, OBEREMS, PAPIER)),
  new Abfuhrtag("2014-11-26", BitSet(SCHLOSSBORN, PAPIER)),

  new Abfuhrtag("2014-12-23", BitSet(GLASHUETTEN, OBEREMS, PAPIER)),
  new Abfuhrtag("2014-12-24", BitSet(SCHLOSSBORN, PAPIER)),

  //Start gelber Sack
  new Abfuhrtag("2014-10-16", ALLEORTSTEILE ++ BitSet(GELBERSACK)),
  new Abfuhrtag("2014-10-30", ALLEORTSTEILE ++ BitSet(GELBERSACK)),

  new Abfuhrtag("2014-11-13", ALLEORTSTEILE ++ BitSet(GELBERSACK)),
  new Abfuhrtag("2014-11-27", ALLEORTSTEILE ++ BitSet(GELBERSACK)),

  new Abfuhrtag("2014-12-11", ALLEORTSTEILE ++ BitSet(GELBERSACK)),
  new Abfuhrtag("2014-12-24", ALLEORTSTEILE ++ BitSet(GELBERSACK)),

  //Start Hausmuell
  new Abfuhrtag("2014-10-20", BitSet(GLASHUETTEN, OBEREMS, HAUSMUELL)),
  new Abfuhrtag("2014-10-27", BitSet(SCHLOSSBORN, HAUSMUELL)),

  new Abfuhrtag("2014-11-03", BitSet(GLASHUETTEN, OBEREMS, HAUSMUELL)),
  new Abfuhrtag("2014-11-10", BitSet(SCHLOSSBORN, HAUSMUELL)),

  new Abfuhrtag("2014-11-17", BitSet(GLASHUETTEN, OBEREMS, HAUSMUELL)),
  new Abfuhrtag("2014-11-24", BitSet(SCHLOSSBORN, HAUSMUELL)),

  new Abfuhrtag("2014-12-01", BitSet(GLASHUETTEN, OBEREMS, HAUSMUELL)),
  new Abfuhrtag("2014-12-08", BitSet(SCHLOSSBORN, HAUSMUELL)),

  new Abfuhrtag("2014-12-15", BitSet(GLASHUETTEN, OBEREMS, HAUSMUELL)),
  new Abfuhrtag("2014-12-22", BitSet(SCHLOSSBORN, HAUSMUELL)),
  new Abfuhrtag("2014-12-29", BitSet(GLASHUETTEN, OBEREMS, HAUSMUELL)),

  //Start Grünschnitt
  new Abfuhrtag("2014-10-25", ALLEORTSTEILE ++ BitSet(GRUENSCHNITT), gruenschnitt_bemerkung),
  new Abfuhrtag("2014-11-08", ALLEORTSTEILE ++ BitSet(GRUENSCHNITT), gruenschnitt_bemerkung),

  //Start Schadstoffmobil
  new Schadstoffmobil("2014-11-29", BitSet(GLASHUETTEN, SCHADSTOFF), schadstoff_bemerkung, "10:30", new Dur(0, 1, 15, 0)),
  new Schadstoffmobil("2014-12-11", BitSet(OBEREMS, SCHADSTOFF), schadstoff_bemerkung, "15:30", new Dur(0, 1, 0, 0)),
  new Schadstoffmobil("2014-12-11", BitSet(SCHLOSSBORN, SCHADSTOFF), schadstoff_bemerkung, "17:15", new Dur(0, 1, 0, 0)))

/* Generates a calendar and saves it to a file */
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
      case x if BitSet(BIOTONNE).subsetOf(x) => "Biotonne"
      case x if BitSet(PAPIER).subsetOf(x) => "Papier"
      case x if BitSet(HAUSMUELL).subsetOf(x) => "Hausmüll"
      case x if BitSet(GELBERSACK).subsetOf(x) => "Gelber Sack"
      case x if BitSet(GRUENSCHNITT).subsetOf(x) => "Grünschnitt"
      case x if BitSet(SCHADSTOFF).subsetOf(x) => "Schadstoffmobil"
      case _ => "undefined"
    }

    val ort = eintrag.Optionen match {
      case x if ALLEORTSTEILE.subsetOf(x) => "Alle Ortsteile"
      case x if BitSet(OBEREMS, GLASHUETTEN).subsetOf(x) => "Glashütten/Oberems"
      case x if BitSet(OBEREMS).subsetOf(x) => "Oberems"
      case x if BitSet(GLASHUETTEN).subsetOf(x) => "Glashütten"
      case x if BitSet(SCHLOSSBORN).subsetOf(x) => "Schloßborn"
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
    event.getProperties().add(new UidGenerator(eintrag.Datum).generateUid());
    //set transparency to transparent
    event.getProperties().add(net.fortuna.ical4j.model.property.Transp.TRANSPARENT)
    //add description?
    if (!eintrag.Bemerkung.isEmpty) {
      event.getProperties().add(new Description(eintrag.Bemerkung + "\n\n" + generelle_bemerkung));
    } else {
      event.getProperties().add(new Description(generelle_bemerkung));
    }
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
saveCalendar(abfuhrliste.filter(x => BitSet(GLASHUETTEN).subsetOf(x.Optionen)), "abfall-2014-glashuetten.ics")
saveCalendar(abfuhrliste.filter(x => BitSet(OBEREMS).subsetOf(x.Optionen)), "abfall-2014-oberems.ics")
saveCalendar(abfuhrliste.filter(x => BitSet(SCHLOSSBORN).subsetOf(x.Optionen)), "abfall-2014-schlossborn.ics")

println("fertig")
