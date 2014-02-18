#!/bin/bash

#altes masterfile löschen
rm muellkalender+gemeinde+glashuetten+gesamt.ics


#vorspann fuer masterfile
cp vorspann.ics.templ muellkalender+gemeinde+glashuetten+gesamt.ics

#masterfile erstellen
cat muellkalender+gemeinde+glashuetten_raw_masterfile.ics.templ >>muellkalender+gemeinde+glashuetten+gesamt.ics


#Beschreibungen hinzufügen
sed -i 's$ION:schadstoff$ION:Schadstoffmobil\\nGlashütten\\, Parkplatz Waldfriedhof\\nOberems\\, Feuerwehrgerätehaus\\nSchloßborn\\, Parkplatz hinter Mehrzweckhalle\\n\\nAbfallkalender der Gemeinde Glashütten/Taunus\\nhttp://www.gemeinde-glashuetten.de/dateien/Abfallkalender.pdf$g' muellkalender+gemeinde+glashuetten+gesamt.ics

sed -i 's$ION:papier$ION:Papier\\nAbfallkalender der Gemeinde Glashütten/Taunus\\nhttp://www.gemeinde-glashuetten.de/dateien/Abfallkalender.pdf$g' muellkalender+gemeinde+glashuetten+gesamt.ics

sed -i 's$ION:hausmuell$ION:Hausmüll\\nAbfallkalender der Gemeinde Glashütten/Taunus\\nhttp://www.gemeinde-glashuetten.de/dateien/Abfallkalender.pdf$g' muellkalender+gemeinde+glashuetten+gesamt.ics

sed -i 's$ION:gelber_sack$ION:Gelber Sack\\nAbfallkalender der Gemeinde Glashütten/Taunus\\nhttp://www.gemeinde-glashuetten.de/dateien/Abfallkalender.pdf$g' muellkalender+gemeinde+glashuetten+gesamt.ics

sed -i 's$ION:Grünschnitt$ION:Grünschnitt\\nGlashütten\\, Parkplatz Hobholz: 9.30-10.30 Uhr\\nSchloßborn\\, Parkplatz hinter der Mehrzweckhalle: 8.00-9.00 Uhr\\nOberems\\, Am Feuerwehrgerätehaus: 11.00-12.00 Uhr\\n\\nAbfallkalender der Gemeinde Glashütten/Taunus\\nhttp://www.gemeinde-glashuetten.de/dateien/Abfallkalender.pdf$g' muellkalender+gemeinde+glashuetten+gesamt.ics



#nachspann masterfile
echo "END:VCALENDAR" >> muellkalender+gemeinde+glashuetten+gesamt.ics


#zwischen jedem Event war zwangsweise ein Leerzeichen. Dann pro Zeile ein Event schreiben

cat muellkalender+gemeinde+glashuetten+gesamt.ics | sed 's/^\s*$/_umbruch1_/g' | sed ':a;N;$!ba;s$\r\n$_umbruch_$g' | sed ':a;N;$!ba;s$\n$_umbruch_$g' | sed 's$_umbruch1_$\n$g' \
> muellkalender+gemeinde+glashuetten+glashuetten.ics

#nur Glashütten-Relevante filtern
sed -i '/(Glashütten\|Alle Ortsteile/!d' muellkalender+gemeinde+glashuetten+glashuetten.ics
sed  's$_umbruch_$\n$g' muellkalender+gemeinde+glashuetten+glashuetten.ics > muellkalender+gemeinde+glashuetten+glashuetten.ics_1

cat vorspann.ics.templ muellkalender+gemeinde+glashuetten+glashuetten.ics_1 > muellkalender+gemeinde+glashuetten+glashuetten.ics
echo "END:VCALENDAR" >> muellkalender+gemeinde+glashuetten+glashuetten.ics

rm muellkalender+gemeinde+glashuetten+glashuetten.ics_1

#nur Oberems-Relevante filtern
cat muellkalender+gemeinde+glashuetten+gesamt.ics | sed 's/^\s*$/_umbruch1_/g' | sed ':a;N;$!ba;s$\r\n$_umbruch_$g' | sed ':a;N;$!ba;s$\n$_umbruch_$g' | sed 's$_umbruch1_$\n$g' \
> muellkalender+gemeinde+glashuetten+oberems.ics

sed -i '/Oberems)\|Alle Ortsteile/!d' muellkalender+gemeinde+glashuetten+oberems.ics
sed  's$_umbruch_$\n$g' muellkalender+gemeinde+glashuetten+oberems.ics > muellkalender+gemeinde+glashuetten+oberems.ics_1

cat vorspann.ics.templ muellkalender+gemeinde+glashuetten+oberems.ics_1 > muellkalender+gemeinde+glashuetten+oberems.ics
echo "END:VCALENDAR" >> muellkalender+gemeinde+glashuetten+oberems.ics

rm muellkalender+gemeinde+glashuetten+oberems.ics_1

#nur Schloßborn-Relevante filtern
cat muellkalender+gemeinde+glashuetten+gesamt.ics | sed 's/^\s*$/_umbruch1_/g' | sed ':a;N;$!ba;s$\r\n$_umbruch_$g' | sed ':a;N;$!ba;s$\n$_umbruch_$g' | sed 's$_umbruch1_$\n$g' \
> muellkalender+gemeinde+glashuetten+schlossborn.ics

sed -i '/Schloßborn)\|Alle Ortsteile/!d' muellkalender+gemeinde+glashuetten+schlossborn.ics
sed  's$_umbruch_$\n$g' muellkalender+gemeinde+glashuetten+schlossborn.ics > muellkalender+gemeinde+glashuetten+schlossborn.ics_1

cat vorspann.ics.templ muellkalender+gemeinde+glashuetten+schlossborn.ics_1 > muellkalender+gemeinde+glashuetten+schlossborn.ics
echo "END:VCALENDAR" >> muellkalender+gemeinde+glashuetten+schlossborn.ics

rm muellkalender+gemeinde+glashuetten+schlossborn.ics_1


#ical parser anmerkungen

#richtige zeilenendungen
unix2dos muellkalender+gemeinde+glashuetten+gesamt.ics
unix2dos muellkalender+gemeinde+glashuetten+glashuetten.ics
unix2dos muellkalender+gemeinde+glashuetten+oberems.ics
unix2dos muellkalender+gemeinde+glashuetten+schlossborn.ics

#keine leerzeilen
sed -i '/^$/d' muellkalender+gemeinde+glashuetten+gesamt.ics
sed -i '/^$/d' muellkalender+gemeinde+glashuetten+glashuetten.ics
sed -i '/^$/d' muellkalender+gemeinde+glashuetten+oberems.ics
sed -i '/^$/d' muellkalender+gemeinde+glashuetten+schlossborn.ics







