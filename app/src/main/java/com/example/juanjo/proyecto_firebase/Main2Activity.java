package com.example.juanjo.proyecto_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juanjo.proyecto_firebase.Model.Producto;
import com.example.juanjo.proyecto_firebase.Model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Spinner categorias;

    String categoria, ClaveModif, claveEliminar;

    Integer contador = 0;

    EditText textNombre, textDescripcion, textPrecio;
    ListView  listadoP;

    Button botonAñadir, botonModificar, botonEliminar, botonUsuario, botonCerrarSesion, botonTodosProductos, botonRecargar;

    DatabaseReference bbddP, bbddModificarP;

    TextView tituloProd;

    FirebaseAuth userAuth;

    ArrayList<String> listado2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        categorias = (Spinner) findViewById(R.id.spinnerCategoria);

        textNombre = (EditText) findViewById(R.id.editNombreP);
        textDescripcion = (EditText) findViewById(R.id.editDescripcion);
        textPrecio = (EditText) findViewById(R.id.editPrecio);


        listadoP = (ListView) findViewById(R.id.listaP);

        botonAñadir = (Button) findViewById(R.id.buttonAñadirP);
        botonModificar = (Button) findViewById(R.id.buttonModificarP);
        botonEliminar = (Button) findViewById(R.id.buttonEliminarP);
        botonUsuario = (Button) findViewById(R.id.buttonUsuarios);
        botonCerrarSesion = (Button) findViewById(R.id.buttonCerrarSesionP);
        botonTodosProductos = (Button) findViewById(R.id.buttonTodosProd);

        tituloProd = (TextView) findViewById(R.id.textViewProductos);

        userAuth = FirebaseAuth.getInstance();

        final String clave = userAuth.getCurrentUser().getUid();

        bbddP = FirebaseDatabase.getInstance().getReference("Productos");



        recargar();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categoria_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorias.setAdapter(adapter);


        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                categoria = (String) adapterView.getItemAtPosition(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Nombre = textNombre.getText().toString();
                String Descripcion = textDescripcion.getText().toString();
                String Precio = textPrecio.getText().toString();

                String claveP = bbddP.push().getKey();

                if (!TextUtils.isEmpty(Nombre)) {


                    if (!TextUtils.isEmpty(Descripcion)) {

                        if (!TextUtils.isEmpty(Precio)) {




                                Producto produ = new Producto(Nombre, Descripcion, categoria, clave,  Precio);


                                bbddP.child(claveP).setValue(produ);

                                Toast.makeText(Main2Activity.this, "Producto añadido", Toast.LENGTH_LONG).show();

                                recargar();



                        } else {
                            Toast.makeText(Main2Activity.this, "Te falta introducir un Precio", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(Main2Activity.this, "Te falta introducir un Descripcion", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(Main2Activity.this, "Te falta introducir un nombre", Toast.LENGTH_LONG).show();

                }


            }
        });

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                final String Producto = textNombre.getText().toString();

                final String Descripcion = textDescripcion.getText().toString();
                final String Precio = textPrecio.getText().toString();


                if(!TextUtils.isEmpty(Producto)){


                    Query q = bbddP.orderByChild("nombre").equalTo(Producto);

                    if (!TextUtils.isEmpty(Descripcion) || !TextUtils.isEmpty(Precio)) {

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshot) {


                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                                ClaveModif = datasnapshot.getKey();

                                bbddModificarP = FirebaseDatabase.getInstance().getReference("Productos").child(ClaveModif);



                                    if (!TextUtils.isEmpty(Descripcion)) {
                                        bbddModificarP.child("descripcion").setValue(textDescripcion.getText().toString());
                                        Toast.makeText(Main2Activity.this, "Se ha modificado la descripcion del producto", Toast.LENGTH_LONG).show();


                                    }

                                    if (!TextUtils.isEmpty(Precio)) {
                                        bbddModificarP.child("precio").setValue(Precio);
                                        Toast.makeText(Main2Activity.this, "Se ha modificado el precio del producto", Toast.LENGTH_LONG).show();


                                    }

                                    if (!TextUtils.isEmpty(categoria)) {
                                        bbddModificarP.child("categoria").setValue(categoria);
                                        Toast.makeText(Main2Activity.this, "Se ha modificado la categoria del producto", Toast.LENGTH_LONG).show();

                                    }

                            }

                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    } else {
                        Toast.makeText(Main2Activity.this, "Introduce lo qu quieres modificar", Toast.LENGTH_SHORT).show();
                    }





                }
                else{
                    Toast.makeText(Main2Activity.this, "Debes de introducir un Nombre", Toast.LENGTH_LONG).show();
                }

            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                final String Producto = textNombre.getText().toString();




                if(!TextUtils.isEmpty(Producto)){


                    Query q = bbddP.orderByChild("nombre").equalTo(Producto);


                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {



                                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                                    claveEliminar = datasnapshot.getKey();


                                    DatabaseReference ref = bbddP.child(claveEliminar);

                                    ref.removeValue();

                                    Toast.makeText(Main2Activity.this, "Se ha eliminado el producto: "+ Producto , Toast.LENGTH_SHORT).show();

                                    recargar();




                                }

                            }



                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                }
                else{
                    Toast.makeText(Main2Activity.this, "Debes de introducir un Nombre", Toast.LENGTH_LONG).show();
                }
            }
        });

        botonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity);
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



        botonTodosProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activity = new Intent(getApplicationContext(), TodosProductos.class);
                startActivity(activity);
            }
        });


    }

    public void recargar(){
        bbddP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayAdapter<String> adaptador2;


                listado2.clear();

                contador=0;

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Producto producto = datasnapshot.getValue(Producto.class);

                    String nombreProd = producto.getNombre();


                        listado2.add(nombreProd);

                            contador++;

                }

                if(listado2.isEmpty()){
                    tituloProd.setText("No tiene productos");
                }else{
                    tituloProd.setText("Tiene " + contador+" productos");
                }


                adaptador2 = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_activated_1, listado2);
                listadoP.setAdapter(adaptador2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
