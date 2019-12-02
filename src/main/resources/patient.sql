create table PATIENT
(
    ID         BIGINT identity
        constraint PATIENT_PK
            primary key,
    NAME       CHARACTER(50) not null,
    SURNAME    CHARACTER(50) not null,
    PATRONYMIC CHARACTER(50) not null,
    PHONE      CHARACTER(10) not null
);


