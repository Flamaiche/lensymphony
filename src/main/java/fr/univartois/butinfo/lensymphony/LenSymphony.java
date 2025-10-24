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
 * LenSymphony provides a command-line interface (CLI) for synthesizing and optionally
 * playing MusicXML files. It supports both command-line arguments and interactive
 * prompts for missing information.
 *
 * <p>Options:
 * <ul>
 *   <li>-i / --input : Path to input MusicXML file.</li>
 *   <li>-o / --output : Flag to save synthesized output to a file.</li>
 *   <li>-p / --play : Flag to play the music in real time.</li>
 *   <li>-v / --voice : Comma-separated list of instruments for each track.</li>
 * </ul>
 *
 * <p>If an option is not provided as an argument, the program will prompt the user
 * to enter the value interactively.
 *
 * <p>Example usage:
 * <pre>
 * java -jar LenSymphony.jar -i song.xml -o -p -v PIANO,VIOLIN
 * </pre>
 */
@Command(name = "lensymphony",
        mixinStandardHelpOptions = true,
        version = "LenSymphony 1.0",
        description = "Synthesizes and optionally plays a MusicXML file.")
public final class LenSymphony implements Callable<Integer> {

    /** Scanner used to read interactive input from the console. */
    private final Scanner scanner = new Scanner(System.in);

    /** Input MusicXML file. */
    @Option(names = {"-i", "--input"}, description = "Input MusicXML file.")
    private File inputFile;

    /** Default file name if none is provided interactively. */
    private final String defaultFile = "take-my-breath";

    /** Path to the folder containing music examples. */
    private static final String PATH_FOLDER_MUSIC = "examples/";

    /** Extension of MusicXML files. */
    private static final String EXTENSION_MUSIC_FILE = ".xml";

    /** Output file flag. */
    @Option(names = {"-o", "--output"}, description = "Output file for synthesized sound (optional).")
    private Boolean output;

    /** Default value for the output flag. */
    private final boolean defaultOutput = false;

    /** File object for the output. */
    private File outputFile;

    /** Default path for output files. */
    private static final String PATH_OUTPUT = PATH_FOLDER_MUSIC + "output/";

    /** Flag to play music in real time. */
    @Option(names = {"-p", "--play"}, description = "Play the music in real time.")
    private Boolean play;

    /** Default play value if not provided interactively. */
    private final boolean defaultPlay = true;

    /** List of instruments to associate with each track. */
    @Option(names = {"-v", "--voice"}, description = "Voice-to-track (format: instrument_name).", split = ",")
    private List<Instrument> voiceList;

    /** Singleton instance of the note factory. */
    private static final AbstractNoteFactory noteFactory = NoteFactory.getINSTANCE();

    /**
     * Main callable method invoked by Picocli.
     *
     * <p>This method checks for missing arguments and prompts the user interactively
     * if needed, parses the MusicXML file, constructs staffs with instruments, and
     * synthesizes and optionally plays or saves the music.
     *
     * @return exit code 0 if successful, 1 if the input file does not exist.
     * @throws Exception if parsing or synthesis fails.
     */
    @Override
    public Integer call() throws Exception {

        String input = "";
        if (inputFile == null) {
            // Ask for input file
            System.out.print("Enter name to input (in : examples/ [ur-input] .xml) file: ");
            input = scanner.nextLine();
            if (input.isEmpty()) input = defaultFile;
            inputFile = new File(PATH_FOLDER_MUSIC + input + EXTENSION_MUSIC_FILE);

            if (!inputFile.exists()) {
                System.err.println("File does not exist: " + inputFile.getAbsolutePath());
                return 1;
            }
        }

        if (output == null) {
            // Ask for output file
            System.out.print("Output file ? (y/n): ");
            String out = scanner.nextLine();
            if (out.isEmpty()) output = defaultOutput;
            else output = out.trim().equalsIgnoreCase("y");
            if (output) outputFile = new File(PATH_OUTPUT + input);
        }

        if (play == null) {
            // Ask for play option
            System.out.print("Play in real time? (y/n): ");
            String playIn = scanner.nextLine();
            if (playIn.isEmpty()) play = defaultPlay;
            else play = playIn.trim().equalsIgnoreCase("y");
        }

        // Parse MusicXML file
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MusicXMLSaxParser handler = new MusicXMLSaxParser(noteFactory);
        saxParser.parse(inputFile, handler);

        if (voiceList == null) {
            // Ask for voice mappings if not provided
            voiceList = new ArrayList<>();
            System.out.println("Enter voice mappings (format: INSTRUMENT), one per line. Empty line to finish:");
            int trackNumber = 1;
            for (Map.Entry<String, List<Note>> entry : handler.getParts().entrySet()) {
                String partId = entry.getKey();
                System.out.print("Track (" + trackNumber++ + ") - ");
                String instrumentNameFromXML = handler.getInstrumentName(partId.split("\\.")[0]);
                Instrument instrument = getInstrumentFromUser(instrumentNameFromXML);
                voiceList.add(instrument);
            }
        }

        // Build staffs for each track
        List<Staff> staffs = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, List<Note>> entry : handler.getParts().entrySet()) {
            List<Note> notes = entry.getValue();

            Instrument instrument;
            try {
                instrument = voiceList.get(i++);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Warning : voiceList size is smaller than the number of tracks");
                instrument = Instrument.PURE_TONE;
            }

            Staff staff = new Staff(instrument);
            for (Note note : notes) {
                if (note != null) staff.add(note);
            }
            staffs.add(staff);
        }

        // Create score and synthesizer
        Score score = new Score(staffs);
        MusicSynthesizer mixedSynth = new MixedMusicSynthesizer(score, handler.getTempo());

        // Synthesize and optionally play or save
        mixedSynth.synthesize();
        if (play) mixedSynth.play();

        if (outputFile != null) {
            System.out.println("Saving output to " + outputFile.getAbsolutePath());
            // TODO: implement file export
        }

        System.out.println("Finished!");
        return 0;
    }

    /**
     * Prompts the user to enter an instrument for a track.
     *
     * @param instrumentNameFromXML the default instrument name from the XML file
     * @return the Instrument selected by the user, or the default if left empty
     */
    private Instrument getInstrumentFromUser(String instrumentNameFromXML) {
        System.out.print("instrument (or empty to get it automatically): ");
        String line = scanner.nextLine();
        if (line.isEmpty()) return Instrument.getInstrumentByName(instrumentNameFromXML);
        else return Instrument.getInstrumentByName(line);
    }

    /**
     * Main entry point for the CLI application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new LenSymphony()).execute(args);
        System.exit(exitCode);
    }
}
