package user.meistertisch.danissmpplugin.essentials.lang;

public enum Languages {
    GERMAN("language_de", "de"),
    ENGLISH("language_en", "en");

    private final String fileName;
    private final String shortage;

    Languages(String fileName, String shortage) {
        this.fileName = fileName;
        this.shortage = shortage;
    }

    public String getFileName() {
        return fileName;
    }

    public String getShortage() {
        return shortage;
    }

    public static Languages getLanguage(String language) {
        for (Languages lang : Languages.values()) {
            if (lang.name().equalsIgnoreCase(language)) {
                return lang;
            }
        }
        return null;
    }
}
