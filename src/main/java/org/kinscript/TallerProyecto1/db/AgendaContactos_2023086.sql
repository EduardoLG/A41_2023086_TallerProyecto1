drop database if exists AgendaContactos_2023086;
create database AgendaContactos_2023086;
use AgendaContactos_2023086;


create table Usuario (
    idUsuario int auto_increment primary key,
    usuario varchar(50),
    password varchar(50) 
);

insert into Usuario (usuario, password) values ('Eduardo', 'hola123');
insert into Usuario (usuario, password) values ('Pedro', 'pass2');
insert into Usuario (usuario, password) values ('Luis', 'pass3');

create table Contactos (
    idContacto integer auto_increment,
    nombre varchar(200),
    numeroTelefono varchar(16),
    email varchar(64),
    idUsuario int not null, 
    constraint pk_contactos primary key (idContacto),
    constraint fk_usuario foreign key (idUsuario)
        references Usuario(idUsuario)
      
);
