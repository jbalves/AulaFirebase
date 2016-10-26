package barros.jeferson.aulafirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ArrayList<Book> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recuperarDadosFirebase();
    }

    /**
     * Método para recuperar os dados do firebase
     */
    private void recuperarDadosFirebase(){
        FirebaseDatabase.getInstance().getReference().child("livros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Log.d("Jeferson",snapshot.getValue().toString());
                    Book book =  snapshot.getValue(Book.class);
                    //Log.d("Jeferson",book.getAutor());
                    mArrayList.add(book);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Log.d("Jeferson","Tamanho da lista " + mArrayList.size());

    }
}