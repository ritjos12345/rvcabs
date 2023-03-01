package com.rvcabs.microservices.constants;

public enum Status {
    ACTIVE("Active", "Y"),
    DELETED("In Active", "N"),
    SUSPENDED("Suspended", "S"),
    RESUME("Resume", "Y"),
    PENDING("Pending","P");


    private String description;
    private String id;

    private Status(String description, String id) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    public static String getDescriptionById(String id)
    {
        for (final Status type : Status.values()) {
            if (type.getId().equalsIgnoreCase(id)) {
                return type.getDescription();
            }
        }

        return Status.ACTIVE.getDescription();
    }
}
