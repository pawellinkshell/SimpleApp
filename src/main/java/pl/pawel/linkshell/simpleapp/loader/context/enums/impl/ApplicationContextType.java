package pl.pawel.linkshell.simpleapp.loader.context.enums.impl;

import pl.pawel.linkshell.simpleapp.loader.context.enums.ContextType;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public enum ApplicationContextType implements ContextType{

    NORMAL("");

    private String nameType;

    ApplicationContextType(String nameType) {
        this.nameType = nameType;
    }

    @Override
    public String getName() {
        return nameType;
    }
}
