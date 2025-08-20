create database AgendaContactos_2023086;
use AgendaContactos_2023086;

create table Contactos(
	idContacto integer auto_increment,
    nombre varchar(200),
    numeroTelefono varchar(16),
    email varchar(64),
    constraint pk_contactos primary key (idContacto)
);

insert into Contactos(nombre, numeroTelefono, email) values ('Eduardo','12345678','eduardo@gmail.com');
select * from Contactos;