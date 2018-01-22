package com.example.juanjo.proyecto_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juanjo.proyecto_firebase.Model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodosProductos extends AppCompatActivity {

    ListView listaTecno, listaCoches, listaHogar;

    static ArrayList<String> listadoTecno = new ArrayList<String>();
    static ArrayList<String> listadoCoches = new ArrayList<String>();
    static ArrayList<String> listadoHogar = new ArrayList<String>();

    ArrayAdapter<String> adaptadorProductosHogar;
    ArrayAdapter<String> adaptadorProductosCoche;
    ArrayAdapter<String> adaptadorProductosTec;

    DatabaseReference bbddP;

    FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_productos);

        listaTecno = (ListView) findViewById(R.id.listViewTecno);
        listaCoches = (ListView) findViewById(R.id.listViewCoches);
        listaHogar = (ListView) findViewById(R.id.listViewHogar);

        userAuth = FirebaseAuth.getInstance();


        bbddP = FirebaseDatabase.getInstance().getReference("Productos");


        cargarProductosTecnologia();
        cargarProductosCoches();
        cargarProductosHogar();

    }

    public void cargarProductosTecnologia() {
        Query q = bbddP.orderByChild("categoria").equalTo("Tecnologia");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados


                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoTec = prod.getNombre();

                    listadoTecno.add(nombreProductoTec);
                    adaptadorProductosTec = new ArrayAdapter<String>(TodosProductos.this, android.R.layout.simple_list_item_1, listadoTecno);
                    listaTecno.setAdapter(adaptadorProductosTec);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void cargarProductosCoches(){
        Query q =  bbddP.orderByChild("categoria").equalTo("Coches");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados



                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoCoche = prod.getNombre();

                    listadoCoches.add(nombreProductoCoche);
                    adaptadorProductosCoche = new ArrayAdapter<String>(TodosProductos.this, android.R.layout.simple_list_item_1, listadoCoches);
                    listaCoches.setAdapter(adaptadorProductosCoche);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void cargarProductosHogar(){
        Query q =  bbddP.orderByChild("categoria").equalTo("Hogar");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados



                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoHogar = prod.getNombre();

                    listadoHogar.add(nombreProductoHogar);
                    adaptadorProductosHogar = new ArrayAdapter<String>(TodosProductos.this, android.R.layout.simple_list_item_1, listadoHogar);
                    listaHogar.setAdapter(adaptadorProductosHogar);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

