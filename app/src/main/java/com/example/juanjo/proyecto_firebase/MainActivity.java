package com.example.juanjo.proyecto_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juanjo.proyecto_firebase.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText textNombre, textApellidos, textCorreo, textDireccion;
    ListView lista;

    Button botonAñadir, botonModificar, botonEliminar, botonProducto;

    DatabaseReference bbdd;

    ArrayList<String> listado = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNombre = (EditText) findViewById(R.id.editNombre);
        textApellidos = (EditText) findViewById(R.id.editApellidos);
        textCorreo = (EditText) findViewById(R.id.editCorreo);
        textDireccion = (EditText) findViewById(R.id.editDireccion);

        lista = (ListView) findViewById(R.id.list);

        botonAñadir = (Button) findViewById(R.id.buttonAñadir);
        botonModificar = (Button) findViewById(R.id.buttonModificar);
        botonEliminar = (Button) findViewById(R.id.buttonEliminar);
        botonProducto = (Button) findViewById(R.id.buttonProducto);

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){

                    Usuario usuario = datasnapshot.getValue(Usuario.class);

                    String nombreUsu = (usuario.getNombre() + usuario.getApellidos());

                    listado.add(nombreUsu);
                }

                adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_activated_1, listado);
                lista.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Nombre = textNombre.getText().toString();
                String Apellidos = textApellidos.getText().toString();
                String Correo = textCorreo.getText().toString();
                String Direccion = textDireccion.getText().toString();

                if(!TextUtils.isEmpty(Nombre)){


                    if(!TextUtils.isEmpty(Apellidos)){

                        if(!TextUtils.isEmpty(Correo)) {


                            if(!TextUtils.isEmpty(Direccion)) {





                                    Usuario usu = new Usuario(Nombre, Apellidos, Correo, Direccion);



                                for(int x=0;x<listado.size();x++) {
                                    if (listado.get(x)!=(usu.getNombre() + usu.getApellidos())){

                                        String clave = (usu.getNombre() + usu.getApellidos());

                                        bbdd.child(clave).setValue(usu);

                                        Toast.makeText(MainActivity.this, "Usuario añadido", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Este usuario ya existe", Toast.LENGTH_LONG).show();

                                    }
                                }


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

        botonModificar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                final String Usuario = (textNombre.getText().toString() + textApellidos.getText().toString());

                final String Correo = textCorreo.getText().toString();
                final String Direccion = textDireccion.getText().toString();

                String Nombre = textNombre.getText().toString();
                String Apellidos = textApellidos.getText().toString();

                Toast.makeText(MainActivity.this, Usuario, Toast.LENGTH_LONG).show();

                if(!TextUtils.isEmpty(Nombre)){

                    if(!TextUtils.isEmpty(Apellidos)) {




                        Query q = bbdd.orderByChild("Usuarios").equalTo(Nombre+Apellidos);

                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {



                                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {


                                    String clave = datasnapshot.getKey();

                                    if (!TextUtils.isEmpty(Correo)) {
                                        if (!TextUtils.isEmpty(Direccion)) {


                                        if(clave==Usuario) {
                                            bbdd.child(clave).child("correo").setValue(textCorreo.getText().toString());

                                            bbdd.child(Usuario).child("direccion").setValue(Direccion);

                                            Toast.makeText(MainActivity.this, "La direccion del  " + Usuario + " se ha modificado con éxito", Toast.LENGTH_LONG).show();

                                        }
                                    }



                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }else {
                        Toast.makeText(MainActivity.this, "Debes de introducir un apellido", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Debes de introducir un Nombre", Toast.LENGTH_LONG).show();
                }
            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Usuario = (textNombre.getText().toString() + textApellidos.getText().toString());



                String Nombre = textNombre.getText().toString();
                String Apellidos = textApellidos.getText().toString();

                Toast.makeText(MainActivity.this, Usuario, Toast.LENGTH_LONG).show();

                if(!TextUtils.isEmpty(Nombre)){

                    if(!TextUtils.isEmpty(Apellidos)) {

                        Toast.makeText(MainActivity.this, "Estoy dentro", Toast.LENGTH_LONG).show();



                        Query q = bbdd.orderByChild("Usuarios").equalTo(Nombre+Apellidos);

                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Toast.makeText(MainActivity.this, "dentro de datasnapsot 1", Toast.LENGTH_LONG).show();


                                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                                    String clave = datasnapshot.getKey();


                                        if(clave==Usuario) {

                                            DatabaseReference ref = bbdd.child(clave);

                                            Toast.makeText(MainActivity.this, "dentro de remove ", Toast.LENGTH_LONG).show();

                                            ref.removeValue();
                                        }




                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(MainActivity.this, "Se ha borrado el usuario introducido", Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(MainActivity.this, "Debes de introducir un apellido", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Debes de introducir un Nombre", Toast.LENGTH_LONG).show();
                }

            }
        });

        botonProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent activity2 = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(activity2);



            }
        });
    }
}
