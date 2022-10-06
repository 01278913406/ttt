/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class lv extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"sekundes", "sekundēm"},
            MINUTES  /**/ = {"minūtes", "minūtēm", "minūtes"},
            HOURS    /**/ = {"stundas", "stundām"},
            DAYS     /**/ = {"dienas", "dienām"},
            WEEKS    /**/ = {"nedēļas", "nedēļām"},
            MONTHS   /**/ = {"mēneša", "mēnešiem"},
            YEARS    /**/ = {"gada", "gadiem"};

    private static final lv INSTANCE = new lv();

    public static lv getInstance() {
        return INSTANCE;
    }

    private lv() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}