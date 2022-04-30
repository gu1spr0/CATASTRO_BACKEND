package com.gis.catastro.exception;

public enum MessageDescription {
    // General 0-50
    stateNullOrEmpty,
    stateNotValid,
    validationNullOrEmpty,
    tokenNullOrEmpty,
    objectNull,

    // Entitys 101-150
    repeated,
    notExists,
    EntityDuplicated,
    nameDuplicated,
    codeDuplicated,
    accionNotValid,
    typeNotValid,
    entityNotValid,
    sameState,
    missingApprovedData,

    // Carga Masiva 151-200
    CSVNoValido,
    FechaNoValido,
    DatosNoValidos,
    DatosDuplicados,
    DatosExistentes,
    TamanioNoValido,
    SaldoNegativo,
    ImporteDescuentoNegativo,
    DiasNegativo,

    // contraseñas  251-300
    ContraseniaIncorrecta,

    // Usuarios 301-350
    UsernameNoEncontrado,
    UsernameDuplicado,
    UserWithoutRoles,
    UserWithoutResources,
    UserWithoutPermissions,
    UserAccessDenied,


    // Encuestas 351 - 400
    PreguntaNoValida,
    TipoNoValido,
    LugarInvalido,
    Base64NotValid,
    Base64Null,
    Base64Int,
    Base64DatosError
}
