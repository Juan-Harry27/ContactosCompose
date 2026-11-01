
package com.practica.contactoscompose;

import android.util.Patterns;

public class ValidadorJava {

    public static boolean validarNombre(String nombre) {
        return nombre != null &&
                nombre.trim().matches("[\\p{L} ]+");
    }

    public static boolean validarCorreo(String correo) {
        return correo != null &&
                Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches();
    }

    public static boolean validarTelefono(String telefono) {
        return telefono == null ||
                telefono.trim().isEmpty() ||
                telefono.trim().matches("[0-9]+");
    }
}
