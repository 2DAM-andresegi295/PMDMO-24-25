package iesmm.pmdm.pmdm_t5_sqlite.dao;

import android.database.Cursor;

public interface DAO {
    public Cursor getAllData();
    public boolean insert(String name, String surname, String marks);
    public boolean update(String id, String name, String surname, String marks);
    public int delete(String id);
    public Cursor gatMarks(String marks);//Devuelve los recursos que tengan esas marcas
    public boolean borrarTodo(); //Eliminar todos los registros
}
