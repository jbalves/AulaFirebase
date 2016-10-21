package barros.jeferson.aulafirebase;

/**
 * Created by jbalves on 10/20/16.
 */

public class Book {
    private String capa;
    private String titulo;
    private String autor;
    private Integer paginas;
    private Integer ano;
    private String categoria;



    public Book() {
    }

    public Book(String capa, String titulo, String autor, Integer paginas, Integer ano, String categoria) {
        this.capa = capa;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.ano = ano;
        this.categoria = categoria;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
