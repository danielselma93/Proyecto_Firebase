package com.example.juanjo.proyecto_firebase;

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
import android.widget.Toast;

import com.example.juanjo.proyecto_firebase.Model.Producto;
import com.example.juanjo.proyecto_firebase.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Spinner categorias;

    String categoria;

    EditText textNombre, textDescripcion, textPrecio, textUsuario;
    ListView listadoU, listadoP;

    Button botonAñadir, botonModificar, botonEliminar, botonUsuario;

    DatabaseReference bbddP;
    DatabaseReference bbddU;


    ArrayList<String> listado1 = new ArrayList<String>();
    ArrayList<String> listado2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        categorias = (Spinner) findViewById(R.id.spinnerCategoria);

        textNombre = (EditText) findViewById(R.id.editNombreP);
        textDescripcion = (EditText) findViewById(R.id.editDescripcion);
        textPrecio = (EditText) findViewById(R.id.editPrecio);
        textUsuario = (EditText) findViewById(R.id.editUsuario);

        listadoU = (ListView) findViewById(R.id.listP);

        listadoP = (ListView) findViewById(R.id.listaP);

        botonAñadir = (Button) findViewById(R.id.buttonAñadirP);
        botonModificar = (Button) findViewById(R.id.buttonModificarP);
        botonEliminar = (Button) findViewById(R.id.buttonEliminarP);
        botonUsuario = (Button) findViewById(R.id.buttonUsuarios);

        bbddP = FirebaseDatabase.getInstance().getReference("productos");
        bbddU = FirebaseDatabase.getInstance().getReference("Usuarios");

        bbddP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayAdapter<String> adaptador2;

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {



                    Producto producto = datasnapshot.getValue(Producto.class);

                    String nombreProd = (producto.getNombre());

                    listado2.add(nombreProd);
                }



                adaptador2 = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_activated_1, listado2);
                listadoP.setAdapter(adaptador2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bbddU.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador1;

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Usuario usuario = datasnapshot.getValue(Usuario.class);

                    String nombreUsu = (usuario.getNombre() + usuario.getApellidos());

                    listado1.add(nombreUsu);
                }
                adaptador1 = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_activated_1, listado1);
                listadoU.setAdapter(adaptador1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
                String Usuario = textUsuario.getText().toString();

                if (!TextUtils.isEmpty(Nombre)) {


                    if (!TextUtils.isEmpty(Descripcion)) {

                        if (!TextUtils.isEmpty(Precio)) {


                            if (!TextUtils.isEmpty(Usuario)) {


                                Producto produ = new Producto(Nombre, Descripcion, categoria, Usuario);


                                for (int x = 0; x < listado1.size(); x++) {
                                    if (listado1.get(x) == Usuario) {

                                        String clave = produ.getNombre();

                                        bbddP.child(clave).setValue(produ);

                                        Toast.makeText(Main2Activity.this, "Cancion añadida", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Main2Activity.this, "Este usuario no existe", Toast.LENGTH_LONG).show();

                                    }
                                }


                            } else {
                                Toast.makeText(Main2Activity.this, "Te falta introducir una Usuario", Toast.LENGTH_LONG).show();

                            }

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
    }
}
