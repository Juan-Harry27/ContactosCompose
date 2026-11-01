
package com.practica.contactoscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                FormularioContactos()
            }
        }
    }
}

@Composable
fun FormularioContactos() {

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf(false) }
    var errorCorreo by remember { mutableStateOf(false) }
    var errorTelefono by remember { mutableStateOf(false) }
    var errorTipo by remember { mutableStateOf(false) }

    var mensaje by remember { mutableStateOf("") }

    val contactos = remember { mutableStateListOf<Contacto>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(
                text = "Registro de Contactos",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    errorNombre = false
                },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = errorNombre,
                supportingText = {
                    if (errorNombre) {
                        Text("Ingrese un nombre válido, solo letras")
                    }
                }
            )
        }

        item {
            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    errorCorreo = false
                },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = errorCorreo,
                supportingText = {
                    if (errorCorreo) {
                        Text("Ingrese un correo electrónico válido")
                    }
                }
            )
        }

        item {
            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    telefono = it
                    errorTelefono = false
                },
                label = { Text("Teléfono (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                isError = errorTelefono,
                supportingText = {
                    if (errorTelefono) {
                        Text("El teléfono solo debe contener números")
                    }
                }
            )
        }

        item {
            OutlinedTextField(
                value = tipo,
                onValueChange = {
                    tipo = it
                    errorTipo = false
                },
                label = { Text("Tipo de contacto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = errorTipo,
                supportingText = {
                    if (errorTipo) {
                        Text("Ingrese el tipo de contacto")
                    }
                }
            )
        }

        item {
            Button(
                onClick = {

                    errorNombre =
                        !ValidadorJava.validarNombre(nombre)

                    errorCorreo =
                        !ValidadorJava.validarCorreo(correo)

                    errorTelefono =
                        !ValidadorJava.validarTelefono(telefono)

                    errorTipo = tipo.isBlank()

                    if (!errorNombre &&
                        !errorCorreo &&
                        !errorTelefono &&
                        !errorTipo
                    ) {

                        val nuevoContacto = Contacto(
                            nombre = nombre.trim(),
                            correo = correo.trim(),
                            telefono = telefono.trim()
                                .ifEmpty { null },
                            tipo = tipo.trim()
                        )

                        contactos.add(nuevoContacto)

                        nombre = ""
                        correo = ""
                        telefono = ""
                        tipo = ""

                        mensaje = "Contacto agregado correctamente"

                    } else {
                        mensaje = "Revise los campos del formulario"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar contacto")
            }
        }

        if (mensaje.isNotEmpty()) {
            item {
                Text(
                    text = mensaje,
                    color = if (mensaje == "Contacto agregado correctamente")
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                )
            }
        }

        item {
            Text(
                text = "Contactos agregados",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        if (contactos.isEmpty()) {
            item {
                Text("Todavía no hay contactos registrados")
            }
        }

        items(contactos) { contacto ->

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Text(
                        text = contacto.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Correo: ${contacto.correo}")

                    Text(
                        "Teléfono: ${contacto.telefono ?: "No registrado"}"
                    )

                    Text("Tipo: ${contacto.tipo}")
                }
            }
        }
    }
}
