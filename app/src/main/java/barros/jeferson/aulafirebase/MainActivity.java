package barros.jeferson.aulafirebase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;
import com.oceanbrasil.libocean.control.glide.ImageDelegate;

import java.io.File;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ImageDelegate.BytesListener {
    private static final String TAG = "livro" ;
    private static final int REQUEST_INTENT_CAMERA = 10;
    private static final int REQUEST_PERMISSION = 13;
    private static String[] PERMISSIONS_READ_WRITE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("livro");

    private EditText tituloEdit, autorEdit, paginasEdit, anoEdit;
    private Spinner spinner;
    private ImageView imagemLivro;

    private byte[] bytesDaImagem;

    private File caminhoDaImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tituloEdit = (EditText) findViewById(R.id.tituloEdit);
        autorEdit = (EditText) findViewById(R.id.autorEdit);
        paginasEdit = (EditText) findViewById(R.id.paginasEdit);
        anoEdit = (EditText) findViewById(R.id.anoEdit);
        spinner = (Spinner) findViewById(R.id.spinner);
        imagemLivro = (ImageView) findViewById(R.id.imagemLivro);

        imagemLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamera();
            }
        });

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
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://aula-firebase.appspot.com").child("livrosImagens").child(caminhoDaImagem.getName());
        storageRef.putBytes(bytesDaImagem);
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        Book book = new Book();
//
//        book.setTitulo(tituloEdit.getText().toString());
//        book.setAutor(autorEdit.getText().toString());
//        book.setPaginas(Integer.valueOf(paginasEdit.getText().toString()));
//        book.setAno(Integer.valueOf(anoEdit.getText().toString()));
//        book.setCategoria((String) spinner.getSelectedItem());
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("livros");
//        reference.push().setValue(book).addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    progressDialog.dismiss();
//                    limparEdit();
//                } else {
//                    progressDialog.dismiss();
//                }
//            }
//        });
//
//        progressDialog.setMessage("Eviando livro...");
//        progressDialog.show();


    }

    private void limparEdit(){
        tituloEdit.setText("");
        autorEdit.setText("");
        paginasEdit.setText("");
        anoEdit.setText("");
    }


    private void abrirCamera(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            verificaChamarPermissao();
        } else {
            // tenho permissao, chama a intent de camera
            intentAbrirCamera();
        }
    }

    private void intentAbrirCamera() {
        String nomeFoto = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString()+"firebase.jpg";
        caminhoDaImagem = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),nomeFoto);
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoDaImagem));
        startActivityForResult(it,REQUEST_INTENT_CAMERA);
    }

    private void verificaChamarPermissao() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // exibir o motivo de esta precisando da permissao
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_READ_WRITE, REQUEST_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_READ_WRITE, REQUEST_PERMISSION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION){
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // tem
                Log.d("Ale","tem permissao");
                intentAbrirCamera();
            } else {
                // nao tem a permissao
                Log.d("Ale","nao tem permissao");
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_INTENT_CAMERA && resultCode == RESULT_OK){
            if (caminhoDaImagem != null && caminhoDaImagem.exists()){
                Ocean.glide(this)
                        .load(Uri.fromFile(caminhoDaImagem))
                        .build(GlideRequest.BYTES)
                        .addDelegateImageBytes(this)
                        .toBytes(300, 300);
            }else{
                Log.e("Ale","FILE null");
            }
        }else{
            Log.d("Ale","nao usou a camera");
        }
    }

    @Override
    public void createdImageBytes(byte[] bytes) {
        bytesDaImagem = bytes;
        Bitmap bitmap = Ocean.byteToBitmap(bytes);
        imagemLivro.setImageBitmap(bitmap);
    }
}
