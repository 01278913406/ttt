/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.chicha.ttt.extractor.timeago.patterns;

import org.chicha.ttt.extractor.timeago.PatternsHolder;

public class pt extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"segundo", "segundos"},
            MINUTES  /**/ = {"minuto", "minutos"},
            HOURS    /**/ = {"hora", "horas"},
            DAYS     /**/ = {"dia", "dias"},
            WEEKS    /**/ = {"semana", "semanas"},
            MONTHS   /**/ = {"meses", "mês"},
            YEARS    /**/ = {"ano", "anos"};

    private static final pt INSTANCE = new pt();

    public static pt getInstance() {
        return INSTANCE;
    }

    private pt() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}