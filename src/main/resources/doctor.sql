create table DOCTOR
(
    ID             BIGINT identity
        constraint DOCTOR_PK
            primary key,
    NAME           CHARACTER(50) not null,
    SURNAME        CHARACTER(50) not null,
    PATRONYMIC     CHARACTER(50) not null,
    SPECIALIZATION CHARACTER(50) not null
);


