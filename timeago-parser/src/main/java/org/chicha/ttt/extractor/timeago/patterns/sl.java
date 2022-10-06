/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class sl extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"sekundama", "sekundami", "sekundo"},
            MINUTES  /**/ = {"minutama", "minutami", "minuto"},
            HOURS    /**/ = {"urama", "urami", "uro"},
            DAYS     /**/ = {"dnem", "dnevi", "dnevoma"},
            WEEKS    /**/ = {"tedni", "tednom", "tednoma"},
            MONTHS   /**/ = {"mesecem", "mesecema", "meseci"},
            YEARS    /**/ = {"leti", "letom", "letoma"};

    private static final sl INSTANCE = new sl();

    public static sl getInstance() {
        return INSTANCE;
    }

    private sl() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}