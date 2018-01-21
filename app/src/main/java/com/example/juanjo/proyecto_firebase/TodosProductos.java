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

        final String clave = userAuth.getCurrentUser().getUid();


        bbddP = FirebaseDatabase.getInstance().getReference("quicktrade-3a1b6");


        cargarProductos();

    }
    public void cargarProductos(){
    Query q =  bbddP.orderByChild("categoria").equalTo("Tecnologia");

        q.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayAdapter<String> adaptador2;

            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {



                Producto producto = datasnapshot.getValue(Producto.class);

                String nombreProd = producto.getNombre();

                listadoTecno.add(nombreProd);
            }


            Toast.makeText(TodosProductos.this, "He obtenido producto", Toast.LENGTH_SHORT).show();

            adaptador2 = new ArrayAdapter<String>(TodosProductos.this, android.R.layout.simple_list_item_activated_1, listadoTecno);
            listaTecno.setAdapter(adaptador2);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}
}
