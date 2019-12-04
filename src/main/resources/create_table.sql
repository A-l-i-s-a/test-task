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

insert into PATIENT (id, name, patronymic, surname, phone) values (1, 'Leon', 'Yurocjkin', 'Arrighetti', '5586343698');
insert into PATIENT (id, name, patronymic, surname, phone) values (2, 'Artemis', 'Ossenna', 'Coulman', '3085888564');
insert into PATIENT (id, name, patronymic, surname, phone) values (3, 'Kris', 'Gatley', 'Thomasen', '4206628002');
insert into PATIENT (id, name, patronymic, surname, phone) values (4, 'Wolfy', 'Mallinson', 'Tossell', '1242919336');
insert into PATIENT (id, name, patronymic, surname, phone) values (5, 'Rhett', 'Tregonna', 'Fardy', '8255312207');
insert into PATIENT (id, name, patronymic, surname, phone) values (6, 'Johnny', 'Coslitt', 'Sauniere', '4024683017');
insert into PATIENT (id, name, patronymic, surname, phone) values (7, 'Ronalda', 'Esel', 'Querrard', '6586192412');
insert into PATIENT (id, name, patronymic, surname, phone) values (8, 'Megen', 'Boyett', 'Antoniak', '9868367279');
insert into PATIENT (id, name, patronymic, surname, phone) values (9, 'Ashely', 'Copper', 'Blenkinsop', '6003287181');
insert into PATIENT (id, name, patronymic, surname, phone) values (10, 'Allis', 'Dunford', 'Polin', '9513100029');

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

insert into DOCTOR (id, name, patronymic, surname, specialization) values (1, 'Zacherie', 'Housbie', 'MacCahee', 'SURGEON');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (2, 'Slade', 'Pantin', 'Barukh', 'PSYCHOTHERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (3, 'Ermengarde', 'Garham', 'Genty', 'THERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (4, 'Ettore', 'Batchley', 'Gutch', 'NEUROLOGIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (5, 'Vassily', 'Preist', 'Rose', 'PSYCHOTHERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (6, 'Aluin', 'Fantonetti', 'Jagson', 'DENTIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (7, 'Galen', 'Bosman', 'Eldridge', 'THERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (8, 'Georgy', 'Colbridge', 'Nussen', 'SURGEON');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (9, 'Ardith', 'Chace', 'Medina', 'SURGEON');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (10, 'Zilvia', 'Evins', 'Bellenie', 'DENTIST');

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

insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (1, 'dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac est lacinia nisi', 8, 5, '2020-08-19', 'NORMAL');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (2, 'cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum', 3, 3, '2020-09-08', 'NORMAL');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (3, 'nullam sit amet turpis elementum ligula vehicula consequat morbi a ipsum', 6, 5, '2020-07-15', 'STATIM');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (4, 'justo lacinia eget tincidunt eget tempus vel pede morbi porttitor lorem id ligula suspendisse ornare', 8, 4, '2019-12-09', 'NORMAL');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (5, 'nisl nunc rhoncus dui vel sem sed sagittis nam congue risus semper porta volutpat quam pede lobortis ligula sit', 1, 5, '2019-12-22', 'STATIM');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (6, 'nulla elit ac nulla sed vel enim sit amet nunc viverra dapibus', 1, 4, '2020-01-30', 'STATIM');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (7, 'consectetuer eget rutrum at lorem integer tincidunt ante vel ipsum praesent blandit lacinia erat vestibulum', 1, 1, '2020-04-10', 'CITO');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (8, 'odio cras mi pede malesuada in imperdiet et commodo vulputate justo in blandit ultrices', 9, 6, '2020-06-26', 'CITO');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (9, 'vivamus vestibulum sagittis sapien cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus etiam vel', 5, 6, '2020-03-23', 'STATIM');
insert into RECIPE (id, description, patient_id, doctor_id, date_creation, priority) values (10, 'est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac est lacinia nisi venenatis', 3, 5, '2020-02-11', 'NORMAL');


