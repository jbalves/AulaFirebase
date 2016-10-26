package barros.jeferson.aulafirebase;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.CircularArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;

import java.util.ArrayList;

/**
 * Created by Jeferson Barros on 10/10/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Book> lista;


    public MyAdapter (Context context, ArrayList<Book> lista) {
        this.lista = lista;
        this.context = context;
    }


    //#2 monta o layout da lista
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_list, null);
        return new ViewHolder(view);
    }

    //#3 recupera uma posição da lista no layout
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Recupera a referência do meu livro
        Book book = lista.get(position);

        //Seta os valores do livro para o layout dentro do holder
        holder
                .setTitulo(book.getTitulo())
                .setAno(book.getAno())
                .setAutor(book.getAutor())
                .setPagina(book.getPaginas())
                .setCapa(book.getCapa());
    }

    //#4 conta a quantidade de elementos existente na lista
    @Override
    public int getItemCount() {
        //tamanho da lista
        return lista.size();
    }



    //#1 método a ser implementado
    //mapeia os elementos de layout
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tituloView;
        private TextView autorView;
        private TextView paginaView;
        private TextView anoView;
        private ImageView capaView;

        public ViewHolder(View itemView) {
            super(itemView);


            //Recuperei as referências do layout
            tituloView = (TextView) itemView.findViewById(R.id.txtTitulo);
            autorView = (TextView) itemView.findViewById(R.id.txtAutor);
            paginaView = (TextView) itemView.findViewById(R.id.txtPaginas);
            anoView = (TextView) itemView.findViewById(R.id.txtAno);
            capaView = (ImageView) itemView.findViewById(R.id.imgCapa);

        }

        public ViewHolder setTitulo(String titulo) {
            if (tituloView == null) return this;
            tituloView.setText(titulo);
            return this;
        }

        public ViewHolder setAutor(String autor) {
            if (autorView == null) return this;
            autorView.setText(autor);
            return this;
        }

        public ViewHolder setPagina(int pagina) {
            if (paginaView == null) return this;
            paginaView.setText(pagina+"");
            return this;
        }

        public ViewHolder setAno(int ano) {
            if (paginaView == null) return this;
            anoView.setText(ano+"");
            return this;
        }

        public ViewHolder setCapa(String capa){
            //if (capaView == null) return this;
            //processar a imagem
            Ocean
                    .glide(context)
                    .load(R.mipmap.ic_launcher)
                    .build(GlideRequest.BITMAP)
                    .resize(200,200)
                    .into(capaView);
            return this;
        }


    }

}
