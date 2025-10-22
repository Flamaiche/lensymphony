package fr.univartois.butinfo.lensymphony.musicxml;

import fr.univartois.butinfo.lensymphony.musicxml.MusicXMLSaxParser;
import fr.univartois.butinfo.lensymphony.notes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestMusicXMLSaxParser {

    private MusicXMLSaxParser parser;
    private AbstractNoteFactory factory;

    @BeforeEach
    void setup() {
        // Implémentation minimale pour les tests
        factory = new AbstractNoteFactory() {
            @Override
            public Note createNote(NotePitch pitch, NoteValue value) {
                return new Note() {
                    @Override
                    public double getFrequency() {
                        return pitch.frequency();
                    }

                    @Override
                    public int getDuration(int tempo) {
                        return value.duration(tempo);
                    }
                };
            }

            @Override
            public Note createRest(NoteValue value) {
                return createNote(NotePitch.of(PitchClass.C, 4), value);
            }

            @Override
            public Note createDottedNote(Note note) {
                return note;
            }

            @Override
            public Note createFermataOn(Note note) {
                return note;
            }

            @Override
            public Note createTiedNotes(Note... notes) {
                return notes[0];
            }

            @Override
            public Note createTiedNotes(List<Note> notes) {
                return notes.get(0);
            }
        };

        parser = new MusicXMLSaxParser(factory);
    }

    // Implémentation simple de Attributes
    private static class SimpleAttributes implements Attributes {
        private final Map<String, String> values = new HashMap<>();

        void setValue(String key, String value) {
            values.put(key, value);
        }

        @Override
        public String getValue(String qName) {
            return values.get(qName);
        }

        // Les autres méthodes de l'interface ne sont pas utilisées pour ces tests
        @Override public int getLength() { return 0; }
        @Override public String getURI(int index) { return null; }
        @Override public String getLocalName(int index) { return null; }
        @Override public String getQName(int index) { return null; }
        @Override public String getType(int index) { return null; }
        @Override public String getValue(int index) { return null; }
        @Override public int getIndex(String uri, String localName) { return 0; }
        @Override public int getIndex(String qName) { return 0; }
        @Override public String getType(String uri, String localName) { return null; }
        @Override public String getType(String qName) { return null; }
        @Override public String getValue(String s, String s1) {return "";}
    }

    @Test
    void testTempoReading() {
        SimpleAttributes attr = new SimpleAttributes();
        attr.setValue("tempo", "120");

        parser.startElement("", "", "sound", attr);

        assertEquals(120, parser.getTempo(), "Tempo should be read correctly from <sound>");
    }

    @Test
    void testInstrumentNameMapping() {
        SimpleAttributes attr = new SimpleAttributes();
        attr.setValue("id", "P1-I1");

        parser.startElement("", "", "score-instrument", attr);
        parser.characters("Violin".toCharArray(), 0, 6);
        parser.endElement("", "", "instrument-name");

        assertEquals("Violin", parser.getInstrumentName("P1"), "Instrument name should map correctly to part ID");
    }

}
