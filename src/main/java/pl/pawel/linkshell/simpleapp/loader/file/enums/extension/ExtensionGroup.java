package pl.pawel.linkshell.simpleapp.loader.file.enums.extension;

import pl.pawel.linkshell.simpleapp.loader.file.enums.extension.impl.*;

/**
 * Created by pawellinkshell on 25.02.2018.
 */
public enum ExtensionGroup {
    COMPILED(CompiledType.values()),
    ARCHIVE(ArchiveType.values()),
    RESOURCE(ResourceType.values()),
    SCRIPT(ScriptType.values()),
    TEXT_MEDIA(TextMediaType.values()),
    IMAGE_MEDIA(ImageMediaType.values()),
    AUDIO_MEDIA(AudioMediaType.values()),
    VIDEO_MEDIA(VideoMediaType.values()),
    OTHER(OtherType.values());

    private Extensionable[] fileTypes;

    ExtensionGroup(Extensionable[] values) {
        this.fileTypes = values;
    }

    public Extensionable[] getFileTypes() {
        return fileTypes;
    }
}