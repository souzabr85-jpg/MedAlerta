ALTER DATABASE medalerta
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

use medalerta;
create table Usuario (
	idUsuario int auto_increment not null,
	nome varchar(100) not null,
    telefone varchar(20) not null,
    email varchar(100) not null,
    enderecoRua varchar(100),
    enderecoNumero int,
    enderecoComplemento varchar(50),
    enderecoBairro varchar(50),
    enderecoCEP varchar(10),
    enderecoCidade varchar(50),
    enderecoEstado varchar(02),
    primary key (idUsuario)
);

create table Medicamento (
	idMedicamento int auto_increment not null,
    nomeComercial varchar(100) not null,
    nomeGenerico varchar(100),
    quantidade enum('unidade', 'ml'),
    formaUso varchar(100),
    observacao varchar(200),
    primary key (idMedicamento)
);

create table UsuarioMedicamento (
    idUsuarioMedicamento int auto_increment not null,
	idUsuario int not null,
	idMedicamento int not null,
    horarioUso time not null,
    frequenciaUso varchar(50),
    dosagem varchar(50) not null,
    dataHorarioAlerta datetime not null,
    statusAlerta enum('emitido', 'não emitido') not null,
    dataHorarioConsumo datetime,
    confirmacaoConsumo enum('sim', 'não') not null,
    primary key (idUsuarioMedicamento),
    foreign key (idUsuario) references Usuario (idUsuario),
    foreign key (idMedicamento) references Medicamento (idMedicamento)
);

create table HorarioMedicamento (
	idHorarioMedicamento int auto_increment not null,
	idUsuarioMedicamento int not null,
	horario time not null,
	frequenciaValor int not null,
	frequenciaUnidade enum('horas', 'dias', 'semanas', 'meses') not null,
	primary key (idHorarioMedicamento),
	foreign key (idUsuarioMedicamento) references UsuarioMedicamento(idUsuarioMedicamento)
);
