package com.example.juanjo.proyecto_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrarse extends AppCompatActivity {

    EditText nombre, apellidos, correo, direccion, contraseña;
    Button registrarse;

     FirebaseAuth mAuth;

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
                        }else{

                            Toast.makeText(Registrarse.this, "EL registro ha fallado " + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
