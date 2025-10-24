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

    private final Scanner scanner = new Scanner(System.in);

    @Option(names = {"-i", "--input"}, description = "Input MusicXML file.")
    private File inputFile;

    private final String defaultFile = "take-my-breath";

    private static final String PATH_FOLDER_MUSIC = "examples/";
    private static final String EXTENSION_MUSIC_FILE = ".xml";

    @Option(names = {"-o", "--output"}, description = "Output file for synthesized sound (optional).")
    private boolean output;

    private final boolean defaultOutput = false;

    private File outputFile;

    private static final String PATH_OUTPUT = PATH_FOLDER_MUSIC + "output/";

    @Option(names = {"-p", "--play"}, description = "Play the music in real time.")
    private Boolean play;

    /**
     * Decision if play is let empty
     */
    private final boolean defaultPlay = true;

    @Option(names = {"-v", "--voice"}, description = "Voice-to-instrument mapping (format: id:instrument).", split = ",")
    private List<Instrument> voiceMappings;

    private static final AbstractNoteFactory noteFactory = NoteFactory.getINSTANCE();
    private final Map<String, Instrument> voiceInstruments = new HashMap<>();

    @Override
    public Integer call() throws Exception {

        // Ask for input file
        System.out.print("Enter name to input (in : examples/ [ur-input] .xml) file: ");
        String input = scanner.nextLine();
        if (input.isEmpty()) input = defaultFile;
        inputFile = new File(PATH_FOLDER_MUSIC + input + EXTENSION_MUSIC_FILE);

        if (!inputFile.exists()) {
            System.err.println("File does not exist: " + inputFile.getAbsolutePath());
            return 1;
        }

        // Ask for output file
        System.out.print("Output file ? (y/n): ");
        String out = scanner.nextLine();
        if (out.isEmpty()) output = defaultOutput;
        else output = out.trim().equalsIgnoreCase("y");
        if (output) outputFile = new File(PATH_OUTPUT + input);


        // Ask for play option
        System.out.print("Play in real time? (y/n): ");
        String playIn = scanner.nextLine();
        if (playIn.isEmpty()) play = defaultPlay;
        else play = playIn.trim().equalsIgnoreCase("y");

        // Ask for voice mappings if not provided
        voiceMappings = new ArrayList<Instrument>();
        System.out.println("Enter voice mappings (format: INSTRUMENT), one per line. Empty line to finish:");

        // Parse MusicXML
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MusicXMLSaxParser handler = new MusicXMLSaxParser(noteFactory);
        saxParser.parse(inputFile, handler);

        // Build staffs
        int trackNumber = 1;
        List<Staff> staffs = new ArrayList<>();
        for (Map.Entry<String, List<Note>> entry : handler.getParts().entrySet()) {
            String partId = entry.getKey();
            List<Note> notes = entry.getValue();

            System.out.print("Track (" + trackNumber++ + ") - ");
            String instrumentNameFromXML = handler.getInstrumentName(partId.split("\\.")[0]);
            Instrument instrument = getInstrumentFromUser(instrumentNameFromXML);

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

    private Instrument getInstrumentFromUser(String instrumentNameFromXML) {
        Instrument instrument;

        System.out.print("instrument (or empty to get it automatically): ");
        String line = scanner.nextLine();
        if (line.isEmpty()) instrument = Instrument.getInstrumentByName(instrumentNameFromXML);
        else instrument = Instrument.getInstrumentByName(line);

        return instrument;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new LenSymphony()).execute(args);
        System.exit(exitCode);
    }
}
