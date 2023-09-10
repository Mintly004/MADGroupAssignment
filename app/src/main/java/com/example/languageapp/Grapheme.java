package com.example.languageapp;

public class Grapheme {
    private String kanjiCharacter;
    private int kanjiStroke;
    private String radicalCharacter;
    private int radicalStroke;
    private int radicalOrder;

    public Grapheme(){

    }
    public Grapheme(String kanjiCharacter, int kanjiStroke, String radicalCharacter, int radicalStroke, int radicalOrder) {
        this.kanjiCharacter = kanjiCharacter;
        this.kanjiStroke = kanjiStroke;
        this.radicalCharacter = radicalCharacter;
        this.radicalStroke = radicalStroke;
        this.radicalOrder = radicalOrder;
    }

    public String getKanjiCharacter() {
        return kanjiCharacter;
    }

    public int getKanjiStroke() {
        return kanjiStroke;
    }

    public String getRadicalCharacter() {
        return radicalCharacter;
    }

    public int getRadicalStroke() {
        return radicalStroke;
    }

    public int getRadicalOrder() {
        return radicalOrder;
    }
}
