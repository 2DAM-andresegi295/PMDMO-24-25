package iesmm.pmdm.pmdm_t4_agenda;

public class Contacto {
    private String nombre;
    private String telefono;
    private String email;
    private int image;
    public Contacto(String nombre, String telefono, String email, int image) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
