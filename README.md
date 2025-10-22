# LenSymphony - A simple music synthesizer library developed in Lens

This project provides a *Java* library for representing and synthesizing
musical pieces.
It allows users to define musical notes, silences, and staffs, and to
synthesize the sound of a complete musical piece using virtual instruments.

## Overview

This library is composed of the following classes:

```plantuml
hide empty members

' -------------------- '
' Note representations '
' -------------------- '

enum NoteValue {
    + {static} WHOLE
    + {static} HALF
    + {static} QUARTER
    + {static} EIGHTH
    + {static} SIXTEENTH
    + {static} THIRTY_SECOND
    + {static} SIXTY_FOURTH
    + {static} ONE_HUNDRED_TWENTY_EIGHTH
    + {static} TWO_HUNDRED_FIFTY_SIXTH
    - fractionOfWhole
    - type

    ~ NoteValue(fractionOfWhole: double, type: String)
    + duration(tempo: int): int
    + {static} fromString(type: String): NoteValue
}

enum PitchClass {
    + {static} C
    + {static} C_SHARP_D_FLAT
    + {static} D
    + {static} D_SHARP_E_FLAT
    + {static} E
    + {static} F
    + {static} F_SHARP_G_FLAT
    + {static} G
    + {static} G_SHARP_A_FLAT
    + {static} A
    + {static} A_SHARP_B_FLAT
    + {static} B

    + {static} fromName(name: String): PitchClass
}

class NotePitch {
    - {static} NB_OCTAVES: int
    - {static} NOTE_PITCHES: Map<PitchClass, NotePitch[]>
    - pitchClass: PitchClass
    - octave: int
    - frequency: double

    - NotePitch(pitchClass: PitchClass, octave: int, frequency: double)
    + {static} of(pitchClass: PitchClass, octave: int): NotePitch
    + {static} of(pitchClass: PitchClass, octave: int, alteration: int): NotePitch
    + alter(alteration: int): NotePitch
    + flat(): NotePitch
    + sharp(): NotePitch
    + frequency(): double
}

interface Note {
    + {abstract} getFrequency(): double
    + {abstract} getDuration(tempo: int): int
}

class Rest implements Note {
    -noteValue: NoteValue
    +Rest(noteValue: NoteValue)
    +getFrequency(): double
    +getDuration(tempo: int): int
}

class MusicalNote implements Note {
    - pitch: NotePitch
    - noteValue: NoteValue
    + MusicalNote(pitch: NotePitch, noteValue: NoteValue)
    + getFrequency(): double
    + getDuration(tempo: int): int

}


abstract class DecoratorNoteSynthesizer implements NoteSynthesizer {
    * base: NoteSynthesizer
    + DecoratorNoteSynthesizer(base: NoteSynthesizer)
    + synthesize(note: Note, tempo: int, volume: double): double[]
    * abstract applyEffect(samples : double [] ,note: Note,tempo : int , volume : double) : double []  
}

class HarmonicsDecorator extends DecoratorNoteSynthesizer {
    - nHarmonics: int
    + HarmonicsDecorator(base: NoteSynthesizer, harmonics: int)
    * applyEffect(samples: double[], note: Note, tempo: int,volume : double): double[]
}

class AdsrDecorator extends DecoratorNoteSynthesizer{
     - a : double
     - d : double
     - s : double
     - r : double
     +AdsrDecorator(base: NoteSynthesizer,a:double,d:double,s:double,r:double)
     * applyEffect(samples: double[], note: Note, tempo: int,volume : double): double[]

}

class VibratoDecorator extends DecoratorNoteSynthesizer {
    - depth: double
    - speed: double
    + VibratoDecorator(base: NoteSynthesizer, depth: double, speed: double)
    + VibratoDecorator(base: NoteSynthesizer)
    * applyEffect(samples: double[], note: Note, tempo: int, volume: double): double[]
    + getDepth(): double
    + getSpeed(): double
}

abstract class DecoratorNote implements Note {
    * note: Note
    * DecoratorNote(note: Note)
}

class DottedNotes extends DecoratorNote {
    + DottedNotes(note: Note)
    + getFrequency(): double
    + getDuration(tempo: int): int
}

class FermataOn extends DecoratorNote {
    + FermataOn(note: Note)
    + getFrequency(): double
    + getDuration(tempo: int): int
}



class TiedNotes implements Note {
    - listTiedNotes: List<Note>
    + TiedNotes(tiedNotes: List<Note>)
    + getFrequency(): double
    + getDuration(tempo: int): int
}

interface AbstractNoteFactory {
    + {abstract} createRest(value: NoteValue): Note
    + {abstract} createNote(pitch: NotePitch, value: NoteValue): Note
    + {abstract} createDottedNote(note: Note): Note
    + {abstract} createFermataOn(note: Note): Note
    + {abstract} createTiedNotes(notes: Note[]): Note
    + {abstract} createTiedNotes(notes: List<Note>): Note
}

NotePitch o-- PitchClass
AbstractNoteFactory --> Note : << creates >>

' ------- '
' Parsing '
' ------- '

class MusicXMLSaxParser {
    + MusicXMLSaxParser(noteFactory: AbstractNoteFactory)
    + getTempo(): int
    + getParts(): Map<String,List<Note>>
    + getNotes(partId: String): List<Note>
}

MusicXMLSaxParser --> AbstractNoteFactory : << uses >>

' --------------- '
' Sound synthesis '
' --------------- '

interface NoteSynthesizer {
    + {static} SAMPLE_RATE: int
    + {abstract} synthesize(note: Note, tempo: int, volume: double): double[]
}

class PureTone implements NoteSynthesizer {
+ static getInstance(): PureTone
+ synthesize(note: Note, tempo: int, volume: double): double[]
}

class Harmonic implements NoteSynthesizer {
    - static instance : Harmonic
    - harmonics : NoteSynthesizer
    - static octave : int
    --
    + Harmonic(octave : int)
    + Harmonic()
    + static getInstance() : Harmonic
    + setOctave(octave : int) : void
    + synthesize(note : Note, tempo : int, volume : double) : double[]
}
    
enum Instrument {
 + PURE_TONE
 + getSynthesizer(): NoteSynthesizer
}

class Staff implements Iterable<Note> {
    + Staff(notes: List<Note>)
    + iterator(): Iterator<Note>
    + getInstrument(): Instrument
}

class Score implements Iterable<Staff> {
    - staffs: List<Staff>
    + Score(staffs: List<Staff>)
    + iterator(): Iterator<Staff>
}

Score o-- "*" Staff

interface MusicSynthesizer {
    + {abstract} synthesize(): void
    + {abstract} getSamples(): double[]
    + {abstract} getAudioData(): byte[]
    + {abstract} play(): void
    + {abstract} save(filename: String): void
}

class MixedMusicSynthesizer implements MusicSynthesizer {
    - score: Score
    - tempo: int
    - samples: double[]
    + MixedMusicSynthesizer(score: Score, tempo: int)
    + synthesize(): void
    + getSamples(): double[]
    + getAudioData(): byte[]
    + play(): void
    + save(filename: String): void
}

class SimpleMusicSynthesizer implements MusicSynthesizer {
    - {static} DEFAULT_VOLUME: double
    - notes: Iterable<Note>
    - synthesizer: NoteSynthesizer
    - tempo: int
    - samples: double[]

    + SimpleMusicSynthesizer(tempo: int, notes: Iterable<Note>, synthetizer: NoteSynthesizer)
    + synthesize(): void
    + getSamples(): double[]
}

SimpleMusicSynthesizer o-- "*" Note
SimpleMusicSynthesizer o-- "1" NoteSynthesizer

' ------------ '
' Main classes '
' ------------ '

class Example {
    - {static} noteFactory: AbstractNoteFactory
    - {static} noteSynthesizer: NoteSynthesizer
    + {static} main(args: String[]): void
}

class LenSymphony {
    - {static} noteFactory: AbstractNoteFactory
    - {static} noteSynthesizer: NoteSynthesizer
    + {static} main(args: String[]): void
}


Example --> AbstractNoteFactory : << uses >>
Example --> MusicXMLSaxParser : << uses >>
Example --> NoteSynthesizer : << uses >>
LenSymphony --> AbstractNoteFactory : << uses >>
LenSymphony --> MusicXMLSaxParser : << uses >>
LenSymphony --> NoteSynthesizer : << uses >>
AbstractNoteFactory --> MusicalNote : << creates >>

MixedMusicSynthesizer o-- "*" Score
LenSymphony --> MixedMusicSynthesizer : << uses >>

```

## Feature list

| Features                                               | Design Pattern(s) (?) | Author(s)       |
|--------------------------------------------------------|-----------------------|-----------------|
| Representation of a note's pitch (name + octave)       |                       |                 |
| Representation of a note/silence value                 |                       |                 |
| Representation of a musical note                       | Composite             | Jabir Danoun    |
| Representation of a silence                            | Composite             | Matheo Popieul  |
| Representation of a point on a note                    | Decorator             | Matheo Popieul  |
| Representation of a tie between notes                  | Composite             | Jabir Danoun    |
| Representation of a staff                              | Iterator              | Hugo Richard    |
| Traversal of notes/silences in a staff                 |                       |                 |
| Representation of a musical piece                      | Iterator              | Malik Babahamou |
| Representation of a fermata on a note                  | Decorator             | Jabir Danoun    |
| Creation of musical elements (notes, silences)         |                       |                 |
| Generation of the "pure" sound for a note              | Strategy              | Hugo Richard    |
| Addition of harmonics to the sound of a note           | Decorator             | Matheo Popieul  |
| Application of an ADSR envelope to the sound of a note | Decorator             | Matheo Popieul  |
| Application of a vibrato to the sound of a note        | Decorator             | Jabir Danoun    |
| Addition of random noise to the sound of a note        |                       |                 |
| Synthesis of the bass drum sound                       |                       |                 |
| Synthesis of the snare drum sound                      |                       |                 |
| Synthesis of the cymbal sound                          |                       |                 |
| Synthesis of the triangle sound                        |                       |                 |
| Synthesis of the timpani sound                         |                       |                 |
| Synthesis of the xylophone sound                       |                       |                 |
| Definition of virtual instruments                      |                       |                 |
| Synthesis of the ensemble piece sound                  |                       |                 |
| Command line management                                |                       |                 |

## Team

This project has been developed by:

- Babahamou Malik
- Danoun Jabir
- Popieul Matheo
- Richard Hugo
