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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText textNombre, textApellidos, textDireccion;
    ListView lista;

    TextView titulo;

    FirebaseUser mAuth;
    FirebaseAuth userAuth;

    Button  botonModificar, botonProducto, botonCerrarSesion;

    DatabaseReference bbdd, usuariobbdd;

    static ArrayList<String> listado = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNombre = (EditText) findViewById(R.id.editNombre);
        textApellidos = (EditText) findViewById(R.id.editApellidos);
        textDireccion = (EditText) findViewById(R.id.editDireccion);

        titulo = (TextView) findViewById(R.id.textViewTitulo);

        lista = (ListView) findViewById(R.id.list);

        botonModificar = (Button) findViewById(R.id.buttonModificar);
        botonProducto = (Button) findViewById(R.id.buttonProducto);
        botonCerrarSesion = (Button) findViewById(R.id.buttonCerrarSesionU);

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

        userAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();

        if (mAuth != null) {
            titulo.setText("Bienvenido usuario con correo " + mAuth.getEmail());
        } else {
            //si no esta Logueado, llevale a que inicie sesión
            startActivity(new Intent(this, Login.class));
            finish();
        }

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




        botonModificar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                final String Usuario = (textNombre.getText().toString() + textApellidos.getText().toString());

                final String Direccion = textDireccion.getText().toString();

                String Nombre = textNombre.getText().toString();
                String Apellidos = textApellidos.getText().toString();

                Toast.makeText(MainActivity.this, Usuario, Toast.LENGTH_LONG).show();

                if(!TextUtils.isEmpty(Nombre)){

                    if(!TextUtils.isEmpty(Apellidos)) {

                            if (!TextUtils.isEmpty(Direccion)) {


                                //Editar usuario
                                usuariobbdd = FirebaseDatabase.getInstance().getReference("Usuarios").child(Usuario);

                               // usuariobbdd = FirebaseDatabase.getInstance().getReference("productos").child(Nombre);

                                //Direccion
                                usuariobbdd.child("direccion").setValue(Direccion);



                            }else{
                                Toast.makeText(MainActivity.this,"Introduce una direccion",Toast.LENGTH_SHORT).show();

                            }








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

        botonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userAuth.signOut();

                Intent activity = new Intent(getApplicationContext(), Login.class);
                startActivity(activity);

                finish();
            }
        });
    }
}
