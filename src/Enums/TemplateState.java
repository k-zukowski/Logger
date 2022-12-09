package Enums;

import java.util.ArrayList;

public enum TemplateState {
    ///TODO Add custom template
    DETAILED(new ArrayList<>() {
        {
            add(LogTemplateParameters.DATE);
            add(LogTemplateParameters.TIME);
            add(LogTemplateParameters.THREADNR);
            add(LogTemplateParameters.LOGTYPE);
            add(LogTemplateParameters.IP);
            add(LogTemplateParameters.MESSAGE);
        }
    }
    )
    ,SPARSE(
            new ArrayList<>() {
                {
                    add(LogTemplateParameters.DATE);
                    add(LogTemplateParameters.TIME);
                    add(LogTemplateParameters.LOGTYPE);
                    add(LogTemplateParameters.MESSAGE);
                }
            }
    );

    private final ArrayList<LogTemplateParameters> templates;

    TemplateState(ArrayList<LogTemplateParameters> template) {
        this.templates = template;
    }

    public ArrayList<LogTemplateParameters> getTemplate() {
        return templates;
    }
}
