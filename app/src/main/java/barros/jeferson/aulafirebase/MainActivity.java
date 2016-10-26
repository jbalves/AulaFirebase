package barros.jeferson.aulafirebase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "livro" ;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("livro");

    private EditText tituloEdit, autorEdit, paginasEdit, anoEdit;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tituloEdit = (EditText) findViewById(R.id.tituloEdit);
        autorEdit = (EditText) findViewById(R.id.autorEdit);
        paginasEdit = (EditText) findViewById(R.id.paginasEdit);
        anoEdit = (EditText) findViewById(R.id.anoEdit);
        spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_neviar) {
            salvarLivro();
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarLivro() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        Book book = new Book();

        book.setTitulo(tituloEdit.getText().toString());
        book.setAutor(autorEdit.getText().toString());
        book.setPaginas(Integer.valueOf(paginasEdit.getText().toString()));
        book.setAno(Integer.valueOf(anoEdit.getText().toString()));
        book.setCategoria((String) spinner.getSelectedItem());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("livros");
        reference.push().setValue(book).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    limparEdit();
                } else {
                    progressDialog.dismiss();
                }
            }
        });

        progressDialog.setMessage("Eviando livro...");
        progressDialog.show();
    }

    private void limparEdit(){
        tituloEdit.setText("");
        autorEdit.setText("");
        paginasEdit.setText("");
        anoEdit.setText("");
    }
}
