package pl.pawel.linkshell.simpleapp.loader.context.enums.impl;

import pl.pawel.linkshell.simpleapp.loader.context.enums.ContextType;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public enum ArchiveContextType implements ContextType {

    JAR("*.jar"),
    ZIP("*.zip"),       //UNUSED
    WAR("*.war"),       //UNUSED
    EAR("*.ear"),       //UNUSED
    EJB("*.ejb");       //UNUSED

    private String nameType;

    ArchiveContextType(String nameType) {
        this.nameType = nameType;
    }

    @Override
    public String getName() {
        return nameType;
    }
}
