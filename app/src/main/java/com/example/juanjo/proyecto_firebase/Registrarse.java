package com.example.juanjo.proyecto_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juanjo.proyecto_firebase.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Registrarse extends AppCompatActivity {

    EditText nombre, apellidos, correo, direccion, contraseña;
    Button registrarse;

     FirebaseAuth mAuth;

    DatabaseReference bbdd;

    static ArrayList<String> listado = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();

        nombre = (EditText) findViewById(R.id.editNombreR);
        apellidos = (EditText) findViewById(R.id.editApellidosR);
        correo = (EditText) findViewById(R.id.editCorreoR);
        direccion = (EditText) findViewById(R.id.editDireccion);
        contraseña = (EditText) findViewById(R.id.editContraR);

        registrarse = (Button) findViewById(R.id.buttonRegistrarseR);

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){

                    Usuario usuario = datasnapshot.getValue(Usuario.class);

                    String nombreUsu = (usuario.getCorreo());

                    listado.add(nombreUsu);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarUsuario();
            }
        });
    }


    private void RegistrarUsuario(){

        mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Registrarse.this, "El registro ha sido exitoso",
                                    Toast.LENGTH_SHORT).show();

                            RegistrarUsuario();

                        }else{

                            Toast.makeText(Registrarse.this, "EL registro ha fallado " + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    private void Registrarbbdd(){


        final String Correo = correo.getText().toString();
        final String Direccion = direccion.getText().toString();

        String Nombre = nombre.getText().toString();
        String Apellidos = apellidos.getText().toString();

        String Contraseña = contraseña.getText().toString();

        if(!TextUtils.isEmpty(Nombre)){


            if(!TextUtils.isEmpty(Apellidos)){

                if(!TextUtils.isEmpty(Correo)) {


                    if(!TextUtils.isEmpty(Direccion)) {


                        if (!TextUtils.isEmpty(Contraseña)) {


                            Usuario usu = new Usuario(Nombre, Apellidos, Correo, Direccion);


                            for (int x = 0; x < listado.size(); x++) {
                                if (listado.get(x) != usu.getCorreo()) {

                                    String clave = usu.getCorreo();

                                    bbdd.child(clave).setValue(usu);

                                    Toast.makeText(Registrarse.this, "Usuario añadido", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Registrarse.this, "Este usuario ya existe", Toast.LENGTH_LONG).show();

                                }
                            }


                        }else{
                            Toast.makeText(Registrarse.this, "Te falta introducir una Contraseña", Toast.LENGTH_LONG).show();

                        }
                    }else{
                        Toast.makeText(Registrarse.this, "Te falta introducir una Direccion", Toast.LENGTH_LONG).show();

                    }

                }else{
                    Toast.makeText(Registrarse.this, "Te falta introducir un Correo", Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(Registrarse.this, "Te falta introducir un Apellido", Toast.LENGTH_LONG).show();

            }

        }else {
            Toast.makeText(Registrarse.this, "Te falta introducir un nombre", Toast.LENGTH_LONG).show();

        }
    }
}
