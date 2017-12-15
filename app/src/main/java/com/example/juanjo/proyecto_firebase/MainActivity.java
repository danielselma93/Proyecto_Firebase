package com.example.juanjo.proyecto_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juanjo.proyecto_firebase.Model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText textNombre, textApellidos, textCorreo, textDireccion, textUsuario;

    Button botonAñadir;

    DatabaseReference bbdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNombre = (EditText) findViewById(R.id.editNombre);
        textApellidos = (EditText) findViewById(R.id.editApellidos);
        textCorreo = (EditText) findViewById(R.id.editCorreo);
        textDireccion = (EditText) findViewById(R.id.editDireccion);
        textUsuario = (EditText) findViewById(R.id.editUsuario);

        botonAñadir = (Button) findViewById(R.id.buttonAñadir);

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Nombre = textNombre.getText().toString();
                String Apellidos = textApellidos.getText().toString();
                String Correo = textCorreo.getText().toString();
                String Direccion = textDireccion.getText().toString();
                String Usuario = textUsuario.getText().toString();

                if(!TextUtils.isEmpty(Nombre)){


                    if(!TextUtils.isEmpty(Apellidos)){

                        if(!TextUtils.isEmpty(Correo)) {


                            if(!TextUtils.isEmpty(Direccion)) {

                                Usuario usu = new Usuario(Nombre, Apellidos, Correo, Direccion, Usuario);

                                String clave = usu.getUsuario();

                                bbdd.child(clave).setValue(usu);

                                Toast.makeText(MainActivity.this, "Disco añadido", Toast.LENGTH_LONG).show();


                            }else{
                                Toast.makeText(MainActivity.this, "Te falta introducir una Direccion", Toast.LENGTH_LONG).show();

                            }

                            }else{
                            Toast.makeText(MainActivity.this, "Te falta introducir un Correo", Toast.LENGTH_LONG).show();

                        }

                    }else {
                        Toast.makeText(MainActivity.this, "Te falta introducir un Apellido", Toast.LENGTH_LONG).show();

                    }

                    }else {
                    Toast.makeText(MainActivity.this, "Te falta introducir un nombre", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
