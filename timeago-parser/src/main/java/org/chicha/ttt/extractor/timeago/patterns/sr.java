/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class sr extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"секунде", "секунди"},
            MINUTES  /**/ = {"минута"},
            HOURS    /**/ = {"сат", "сата", "сати"},
            DAYS     /**/ = {"Пре 1 дан", "Пре 2 дана", "Пре 3 дана", "Пре 4 дана", "Пре 5 дана", "Пре 6 дана"},
            WEEKS    /**/ = {"недеље", "недељу"},
            MONTHS   /**/ = {"месец", "месеца", "месеци"},
            YEARS    /**/ = {"година", "године", "годину"};

    private static final sr INSTANCE = new sr();

    public static sr getInstance() {
        return INSTANCE;
    }

    private sr() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}