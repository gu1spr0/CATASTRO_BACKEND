package com.gis.catastro.exception;

import java.util.Map;
import java.util.TreeMap;

public class Message {
    private final static Map<MessageDescription, CTTOExceptionResponse> vMessageTypes = new TreeMap<MessageDescription, CTTOExceptionResponse>(){{
        // General 0-50
        put(MessageDescription.validationNullOrEmpty, new CTTOExceptionResponse("1","El valor del campo [%s] no puede ser vacío o nulo.", "detalle -_-"));
        put(MessageDescription.stateNullOrEmpty, new CTTOExceptionResponse("2","El estado consultado no puede ser vacío o nulo.", "detalle -_-"));
        put(MessageDescription.stateNotValid, new CTTOExceptionResponse("3","El estado consultado %s no es permitido.", "detalle -_-"));
        put(MessageDescription.tokenNullOrEmpty, new CTTOExceptionResponse("4", "El tipo de token no puede ser nulo", "detalle -_-"));
        put(MessageDescription.objectNull, new CTTOExceptionResponse("5", "%s no puede ser nulo", "detalle -_-"));
        // Entitys 101-150
        put(MessageDescription.repeated, new CTTOExceptionResponse("101","Existe un registro activo con el %s: %s", "detalle -_-"));
        put(MessageDescription.notExists, new CTTOExceptionResponse("102","No se encontró %s con el %s: %s", "detalle -_-"));
        put(MessageDescription.nameDuplicated, new CTTOExceptionResponse("103","El [%s] con nombre [%s] ya esta registrado", "detalle -_-"));
        put(MessageDescription.codeDuplicated, new CTTOExceptionResponse("104","El [%s] con código [%s] ya esta registrado", "detalle -_-"));
        put(MessageDescription.accionNotValid, new CTTOExceptionResponse("105","La acción [%s] no es valida.", "detalle -_-"));
        put(MessageDescription.TipoNoValido, new CTTOExceptionResponse("106","La tipo [%s] no es valido.", "detalle -_-"));
        put(MessageDescription.entityNotValid, new CTTOExceptionResponse("107", "El objeto %s no puede ser nulo", "detalle -_-"));
        put(MessageDescription.missingApprovedData, new CTTOExceptionResponse("108","Existen observaciones en la solicitud, no es posible modificar la bandeja a %s", "detalle -_-"));
        // Carga Masiva 151-200
        put(MessageDescription.CSVNoValido, new CTTOExceptionResponse("151","El CSV no es valido.", "detalle -_-"));
        put(MessageDescription.DatosNoValidos, new CTTOExceptionResponse("152","Existen datos que no son validos, Datos: [%s] .", "detalle -_-"));
        put(MessageDescription.DatosDuplicados, new CTTOExceptionResponse("153","Existen datos duplicados en el CSV, Datos: [%s] .", "detalle -_-"));
        put(MessageDescription.DatosExistentes, new CTTOExceptionResponse("154","Existen datos ya registrados en el CSV, Datos: [%s] .", "detalle -_-"));
        put(MessageDescription.FechaNoValido, new CTTOExceptionResponse("155","El formato de fechas no es válido. Usar dd-mm-yyyy.", "detalle -_-"));
        put(MessageDescription.TamanioNoValido, new CTTOExceptionResponse("156","Las dimensiones de la imagen deben ser [%d]px X [%d]px", "detalle -_-"));
        put(MessageDescription.SaldoNegativo, new CTTOExceptionResponse("157","Los saldos RC-IVA no pueden ser valores negativos", "detalle -_-"));
        put(MessageDescription.ImporteDescuentoNegativo, new CTTOExceptionResponse("158","Los importes de descuento no pueden ser valores negativos", "detalle -_-"));
        put(MessageDescription.DiasNegativo, new CTTOExceptionResponse("159","El total de días no puede ser un valor negativos", "detalle -_-"));

        // contraseñas  251-300
        put(MessageDescription.ContraseniaIncorrecta, new CTTOExceptionResponse("251","Contraseña incorrecta.", "detalle -_-"));
        // Usuarios 301-350
        put(MessageDescription.UsernameNoEncontrado, new CTTOExceptionResponse("301","El username [%s] no se encuentra registrado.", "detalle -_-"));
        put(MessageDescription.UserWithoutRoles, new CTTOExceptionResponse("302","Error en el Login: usuario no tiene rols asignados", "detalle -_-"));
        put(MessageDescription.UserWithoutResources, new CTTOExceptionResponse("304","Error en el Login: usuario no tiene menúes asignados", "detalle -_-"));
        put(MessageDescription.UserWithoutPermissions, new CTTOExceptionResponse("305","Error en el Login: usuario no tiene permisos asignados", "detalle -_-"));
        put(MessageDescription.UsernameDuplicado, new CTTOExceptionResponse("306","El [%s] con username [%s] ya esta registrado", "detalle -_-"));
        put(MessageDescription.UserAccessDenied, new CTTOExceptionResponse("307","Accesos denegado al token.", "detalle -_-"));
        // Encuestas 351 - 400
        put(MessageDescription.PreguntaNoValida, new CTTOExceptionResponse("351","No se puede agregar posibles respuestas a las preguntas simples.", "detalle -_-"));
        put(MessageDescription.TipoNoValido, new CTTOExceptionResponse("352","El tipo banner [%s] solo permite un banner activo.", "detalle -_-"));
        put(MessageDescription.LugarInvalido, new CTTOExceptionResponse("354","El lugar no puede ser nulo.", "detalle -_-"));
        put(MessageDescription.Base64NotValid, new CTTOExceptionResponse("353","Archivo base 64 no válido.", "detalle -_-"));
        put(MessageDescription.Base64Null, new CTTOExceptionResponse("354","Error al analizar CSV, linea: [%s].", "detalle -_-"));
        put(MessageDescription.Base64Int, new CTTOExceptionResponse("355","El total de días solo admite números enteros", "detalle -_-"));
        put(MessageDescription.Base64DatosError, new CTTOExceptionResponse("356","Los datos en el csv son incorrectos", "detalle -_-"));

    }};

    public static CTTOBadRequestException GetBadRequest(MessageDescription vMessageDescription, String value)
    {
        CTTOExceptionResponse vMessageDetail = vMessageTypes.get(vMessageDescription);
        String vNewMessage = vMessageDetail.getMessage();
        vNewMessage = String.format(vNewMessage, value);
        return new CTTOBadRequestException(vMessageDetail.getCode(), vNewMessage);
    }

    public static CTTOBadRequestException GetBadRequest(MessageDescription tipo)
    {
        CTTOExceptionResponse vMessageDetail = vMessageTypes.get(tipo);
        String vNewMessage = vMessageDetail.getMessage();
        return new CTTOBadRequestException(vMessageDetail.getCode(), vNewMessage);
    }

    public static CTTOBadRequestException GetBadRequest(MessageDescription pMessageDescription, Object[] pArgs)
    {
        CTTOExceptionResponse vMessageDetail = vMessageTypes.get(pMessageDescription);
        String vNewMessage = vMessageDetail.getMessage();
        vNewMessage = String.format(vNewMessage, pArgs);
        return new CTTOBadRequestException(vMessageDetail.getCode(), vNewMessage);
    }
    public static CTTONotFoundException GetNotFound(MessageDescription pMessageDescription, Object[] pArgs)
    {
        CTTOExceptionResponse vMessageDetail = vMessageTypes.get(pMessageDescription);
        String vNewMessage = vMessageDetail.getMessage();
        vNewMessage = String.format(vNewMessage, pArgs);
        return new CTTONotFoundException(vMessageDetail.getCode(), vNewMessage);
    }

    public static CTTONotFoundException GetNotFound(MessageDescription pMessageDescription, String pArgs)
    {
        CTTOExceptionResponse messageDetail = vMessageTypes.get(pMessageDescription);
        String newMessage = messageDetail.getMessage();
        newMessage = String.format(newMessage, pArgs);
        return new CTTONotFoundException(messageDetail.getCode(), newMessage);
    }
}
