/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class hi extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"सेकंड"},
            MINUTES  /**/ = {"मिनट"},
            HOURS    /**/ = {"घंटा", "घंटे"},
            DAYS     /**/ = {"दिन"},
            WEEKS    /**/ = {"सप्ताह", "हफ़्ते"},
            MONTHS   /**/ = {"महीना", "महीने"},
            YEARS    /**/ = {"वर्ष"};

    private static final hi INSTANCE = new hi();

    public static hi getInstance() {
        return INSTANCE;
    }

    private hi() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}