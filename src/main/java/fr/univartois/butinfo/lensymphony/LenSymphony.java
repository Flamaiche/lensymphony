/**
 * LenSymphony - A simple music synthesizer library developed in Lens, France.
 * Copyright (c) 2025 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fr.univartois.butinfo.lensymphony;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import fr.univartois.butinfo.lensymphony.musicxml.MusicXMLSaxParser;
import fr.univartois.butinfo.lensymphony.notes.AbstractNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NoteFactory;
import fr.univartois.butinfo.lensymphony.notes.soundSynthesis.Instrument;
import fr.univartois.butinfo.lensymphony.synthesizer.MixedMusicSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.MusicSynthesizer;

/**
 * The LenSymphony class provides a simple application to synthesize and play music from a
 * MusicXML file.
 * This file must be provided as a command line argument to the application.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class LenSymphony {

    /**
     * The note factory used to create notes.
     */
    private static final AbstractNoteFactory noteFactory = NoteFactory.getINSTANCE();

    /**
     * Disables instantiation.
     */
    private LenSymphony() {
        throw new AssertionError("No LenSymphony instances for you!");
    }

    /**
     * Indicates whether the instrument should be selected manually or automatically.
     *
     * <p>
     * If set to {@code true}, instruments are taken from the {@link #instruments} array
     * in the order of the parsed MusicXML parts.
     * If set to {@code false}, each instrument is determined automatically from
     * the {@code <instrument-name>} tag found in the MusicXML file.
     * </p>
     */
    private static final boolean CHOICE_INSTRUMENT_MANUALLY = true;

    /**
     * The list of instruments to use when {@link #CHOICE_INSTRUMENT_MANUALLY} is {@code true}.
     *
     * <p>
     * Each element of this array corresponds to a part in the parsed MusicXML file.
     * If there are more parts than instruments in this array, remaining parts
     * will use {@link Instrument#PURE_TONE} as a fallback.
     * </p>
     */
    private static final Instrument[] instruments = {
            Instrument.TENOR_SAXOPHONE,
            Instrument.PIANO,
            Instrument.TRIANGLE,
            Instrument.PIANO,
            Instrument.PIANO,
            Instrument.PIANO
    };


    /**
     * The main method of the application.
     *
     * @param args The command line arguments, which must contain exactly the path to the
     *        MusicXML file to play.
     *
     * @throws Exception If any error occurs.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("MusicXML file is required as single argument");
        }

        // Create SAX parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        // Parsing files MusicXML
        MusicXMLSaxParser handler = new MusicXMLSaxParser(noteFactory);
        saxParser.parse(new File(args[0]), handler);

        // get and create staffs
        List<Staff> staffs = new ArrayList<>();
        int instrumentNumber = 0;
        for (Map.Entry<String, List<Note>> entry : handler.getParts().entrySet()) {
            String partId = entry.getKey(); // e.g., "P1.1" or "P1.0"
            List<Note> notes = entry.getValue();

            Instrument instrument = Instrument.PURE_TONE;

            if (CHOICE_INSTRUMENT_MANUALLY) {
                try {
                    instrument = instruments[instrumentNumber++];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Warning: Instrument array index out of bounds!");
                }
                System.out.println("Instrument (track " + instrumentNumber + ") : " + instrument.name());
            } else {
                // Retrieve the <instrument-name> from MusicXML
                String instrumentName = handler.getInstrumentName(partId.split("\\.")[0]); // remove staff number
                if (instrumentName != null) {
                    instrument = Instrument.getInstrumentByName(instrumentName); // Convert String to enum
                }
            }

            Staff staff = new Staff(instrument);
            for (Note note : notes) {
                if (note == null) continue;
                staff.add(note);
            }
            staffs.add(staff);
        }

        // creating score containing all staffs
        Score score = new Score(staffs);

        // creating musicSynthesizer for all voice
        MusicSynthesizer mixedSynth = new MixedMusicSynthesizer(score, handler.getTempo());

        // synth and read
        mixedSynth.synthesize();
        mixedSynth.play();
    }

}
