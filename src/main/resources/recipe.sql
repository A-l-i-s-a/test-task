create table RECIPE
(
    ID            BIGINT identity
        constraint TABLE_NAME_PK
            primary key,
    DESCRIPTION   VARCHAR(200)  not null,
    PATIENT_ID    BIGINT        not null
        constraint RECIPE_PATIENT_ID_FK
            references PATIENT,
    DOCTOR_ID     BIGINT        not null
        constraint RECIPE_DOCTOR_ID_FK
            references DOCTOR,
    DATE_CREATION DATE          not null,
    "validity "   DATE          not null,
    PRIORITY      CHARACTER(20) not null
);


