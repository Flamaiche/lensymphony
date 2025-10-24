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
 * This class provides a command-line interface for synthesizing and optionally
 * playing MusicXML files. If a required option is not provided via command-line
 * arguments, the program will prompt the user to enter the value interactively.
 *
 * Options:
 *  -i, --input   : Input MusicXML file.
 *  -o, --output  : Output file for synthesized sound (optional).
 *  -p, --play    : Play the music in real time.
 *  -v, --voice   : Voice-to-track mapping (format: instrument_name).
 *
 * Example usage:
 * java -jar LenSymphony.jar -i song.xml -o -p -v PIANO,VIOLIN
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
    private Boolean output;

    private final boolean defaultOutput = false;

    private File outputFile;

    private static final String PATH_OUTPUT = PATH_FOLDER_MUSIC + "output/";

    @Option(names = {"-p", "--play"}, description = "Play the music in real time.")
    private Boolean play;

    private final boolean defaultPlay = true;

    @Option(names = {"-v", "--voice"}, description = "Voice-to-track (format: instrument_name).", split = ",")
    private List<Instrument> voiceList;

    private static final AbstractNoteFactory noteFactory = NoteFactory.getINSTANCE();

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

        // Parse MusicXML
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

        // Build staffs
        List<Staff> staffs = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, List<Note>> entry : handler.getParts().entrySet()) {
            List<Note> notes = entry.getValue();

            Instrument instrument;
            try {
                instrument = voiceList.get(i++);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Warning : voiceList size is inf to tracklist number");
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
