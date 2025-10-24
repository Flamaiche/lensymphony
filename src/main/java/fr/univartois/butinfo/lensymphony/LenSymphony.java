package fr.univartois.butinfo.lensymphony;

import fr.univartois.butinfo.lensymphony.musicxml.MusicXMLSaxParser;
import fr.univartois.butinfo.lensymphony.notes.AbstractNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NoteFactory;
import fr.univartois.butinfo.lensymphony.notes.soundSynthesis.Instrument;
import fr.univartois.butinfo.lensymphony.synthesizer.MixedMusicSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.MusicSynthesizer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * LenSymphony - Interactive CLI using Picocli.
 *
 * If a required option is missing, the program will prompt the user for input.
 */
@Command(name = "lensymphony",
        mixinStandardHelpOptions = true,
        version = "LenSymphony 1.0",
        description = "Synthesizes and optionally plays a MusicXML file.")
public final class LenSymphony implements Callable<Integer> {

    @Option(names = {"-i", "--input"}, description = "Input MusicXML file.")
    private File inputFile;

    private static final String PATH_FOLDER_MUSIC = "examples/";
    private static final String EXTENSION_MUSIC_FILE = ".xml";

    @Option(names = {"-o", "--output"}, description = "Output file for synthesized sound (optional).")
    private File outputFile;

    @Option(names = {"-p", "--play"}, description = "Play the music in real time.")
    private Boolean play;

    /**
     * Decision if play is let empty
     */
    private boolean defaultPlay = true;

    @Option(names = {"-v", "--voice"}, description = "Voice-to-instrument mapping (format: id:instrument).", split = ",")
    private List<String> voiceMappings;

    private static final AbstractNoteFactory noteFactory = NoteFactory.getINSTANCE();
    private final Map<String, Instrument> voiceInstruments = new HashMap<>();

    @Override
    public Integer call() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Ask for missing input file
        System.out.print("Enter path to input MusicXML file: ");
        inputFile = new File(PATH_FOLDER_MUSIC + scanner.nextLine() + EXTENSION_MUSIC_FILE);

        if (!inputFile.exists()) {
            System.err.println("File does not exist: " + inputFile.getAbsolutePath());
            return 1;
        }

        System.out.print("Enter path to output file (or leave empty to skip): ");
        String out = scanner.nextLine();
        outputFile = out.isEmpty() ? null : new File(out);

        // Ask for play option
        System.out.print("Play in real time? (y/n): ");
        play = scanner.nextLine().trim().equalsIgnoreCase("y");


        // Ask for voice mappings if not provided
        voiceMappings = new ArrayList<>();
        System.out.println("Enter voice mappings (format: id:INSTRUMENT), one per line. Empty line to finish:");
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            voiceMappings.add(line);
        }

        // Parse voice mappings
        for (String mapping : voiceMappings) {
            String[] parts = mapping.split(":");
            if (parts.length == 2) {
                voiceInstruments.put(parts[0], Instrument.getInstrumentByName(parts[1]));
            } else {
                System.err.println("⚠️  Invalid voice mapping: " + mapping);
            }
        }

        // Parse MusicXML
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MusicXMLSaxParser handler = new MusicXMLSaxParser(noteFactory);
        saxParser.parse(inputFile, handler);

        // Build staffs
        List<Staff> staffs = new ArrayList<>();
        for (Map.Entry<String, List<Note>> entry : handler.getParts().entrySet()) {
            String partId = entry.getKey();
            List<Note> notes = entry.getValue();

            Instrument instrument = voiceInstruments.getOrDefault(
                    partId,
                    Instrument.getInstrumentByName(handler.getInstrumentName(partId.split("\\.")[0]))
            );

            Staff staff = new Staff(instrument);
            for (Note note : notes) {
                if (note != null) staff.add(note);
            }
            staffs.add(staff);
        }

        // Create score and synthesizer
        Score score = new Score(staffs);
        MusicSynthesizer mixedSynth = new MixedMusicSynthesizer(score, handler.getTempo());

        // Synthesize and optionally play/save
        mixedSynth.synthesize();
        if (play) mixedSynth.play();

        if (outputFile != null) {
            System.out.println("Saving output to " + outputFile.getAbsolutePath());
            // TODO: implement file export
        }

        System.out.println("Finished!");
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new LenSymphony()).execute(args);
        System.exit(exitCode);
    }
}
